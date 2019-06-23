package cn.hisdar.alt.view.log;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import cn.hisdar.alt.Global;
import cn.hisdar.alt.view.SettingsDialog;
import cn.hisdar.lib.configuration.ConfigItem;
import cn.hisdar.lib.configuration.HConfig;
import cn.hisdar.lib.log.HLog;

public class LogView extends JPanel
implements AdjustmentListener, LogViewControlListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private LogDocument          doc;
	private JTextPane            logArea;
	private JScrollBar           verticalBar;
	private StyleContext         styleContext;
	private JScrollPane          logScrollPanel;
	private LineNumberHeaderView lineNumberHeaderView;
	private LogViewPopupMenu     logViewPopupMenu;
	
	private ArrayList<MarkAttributeStyle> filters;
	private MouseEventAdapter mouseEventAdapter;
	
	private int lineCount = 0;
	private int maxLineCount = 10000;
	private boolean isAutoScroll = true;
	private boolean isSelectedText = false;
	private boolean isDisableAutoWhenSelecteText = false;
	
	private boolean isPaused = false;

	public LogView() {
		filters = new ArrayList<>();
		mouseEventAdapter = new MouseEventAdapter();
		logViewPopupMenu  = new LogViewPopupMenu();
		
		styleContext = new StyleContext();
        doc = new LogDocument(styleContext);
		logArea = new JTextPane(doc);
		logArea.setFont(new Font("Courier New", Font.PLAIN, 18));
		lineNumberHeaderView = new LineNumberHeaderView();
		
		JPanel logAreaPanel = new JPanel();
		logAreaPanel.setLayout(new BorderLayout());
		logAreaPanel.add(logArea, BorderLayout.CENTER);
		logScrollPanel = new JScrollPane(logAreaPanel);
		logScrollPanel.setRowHeaderView(lineNumberHeaderView);
		logScrollPanel.getVerticalScrollBar().setUnitIncrement(9);
		
		setLayout(new BorderLayout());
		add(logScrollPanel, BorderLayout.CENTER);
		
		verticalBar = logScrollPanel.getVerticalScrollBar();
		
		verticalBar.addAdjustmentListener(this);
		verticalBar.addMouseMotionListener(mouseEventAdapter);
		
		logArea.addMouseListener(mouseEventAdapter);
		//logArea.addMouseWheelListener(this);
		
		logViewPopupMenu.addControlListener(this);
	}
	
	public MarkAttributeStyle createFilterAttribute(MarkAttribute attr) {
		MarkAttributeStyle filterAttrStyle = new MarkAttributeStyle();

		//MutableAttributeSet style = new SimpleAttributeSet();
		Style style = styleContext.addStyle(attr.getName(), null);
		StyleConstants.setFontFamily(style, "monospaced");
        StyleConstants.setForeground(style, attr.getForeground());
        StyleConstants.setBackground(style, attr.getBackground());
        
        filterAttrStyle.setAttribute(attr);
        filterAttrStyle.setStyle(style);
        
        return filterAttrStyle;
	}
	
	public boolean isAttributExist(MarkAttribute attr) {
		for (int i = 0; i < filters.size(); i++) {
			MarkAttribute filterAttribute = filters.get(i).getAttribute();
			if (filterAttribute == attr) {
				return true;
			}
			
			if (filterAttribute.getKeyWord().equals(attr.getKeyWord())) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addFilterAttribute(MarkAttribute attr) {
		if (attr == null) {
			return;
		}
		
		if (isAttributExist(attr)) {
			removeFilterAttribute(attr);
		}

		filters.add(createFilterAttribute(attr));
	}
	
	public void removeFilterAttribute(MarkAttribute attr) {
		
		if (attr == null) {
			return;
		}
		
		for (int i = 0; i < filters.size(); i++) {
			MarkAttribute curAttr = filters.get(i).getAttribute();
			
			if (curAttr == attr) {
				filters.remove(i);
				return;
			}
			
			if (curAttr.getKeyWord().equals(attr.getKeyWord())) {
				filters.remove(i);
				return;	
			}
		}
	}
	
	public void output(String log) {
		
		if (log == null) {
			return;
		}
		
		while (getLineCount() > maxLineCount - 1) {
			int offset = logArea.getDocument().getDefaultRootElement().getElement(0).getEndOffset();
			try {
				logArea.setSelectionEnd(logArea.getSelectionStart());
				doc.remove(0, offset);
			} catch (BadLocationException e) {
				HLog.il("offset:" + offset);
				e.printStackTrace();
			}
		}
		
		lineCount++;
		int startIndex = doc.getLength();
		try {
			doc.insertString(startIndex, log + "\n", null);
			updateDocumentAttributes(startIndex, log.length());
		} catch (Exception e) {
			HLog.el("line count:" + lineCount +", startIndex:" + startIndex);
			HLog.el(log);
			return;
		}
	}
	
	public void clear() {
		lineCount = 0;
		logArea.setText("");
	}
	
	
	
	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	private void updateDocumentAttributes(int startIndex, int length) {
		int docLength = doc.getLength();
		if (startIndex + length >= docLength) {
			HLog.il("startIndex:" + startIndex + " length:" + docLength);
			return;
		}
		
		String documentString;
		try {
			documentString = doc.getText(startIndex, length);
		} catch (BadLocationException e) {
			HLog.il("startIndex:" + startIndex + " length:" + docLength);
			e.printStackTrace();
			return;
		}
		
		for (int i = 0; i < filters.size(); i++) {
			MarkAttribute attr = filters.get(i).getAttribute();
			MutableAttributeSet style = filters.get(i).getStyle();
			int index = attr.getStringMatchedIndex(documentString, 0);
			
			while (index >= 0) {
				int docIndex = startIndex + index;
				int keyWordLen = attr.getKeyWord().length();
				doc.setCharacterAttributes(docIndex, keyWordLen, style, true);
				index = attr.getStringMatchedIndex(documentString, index + keyWordLen);
			}
		}
	}
	
	public int getLineCount() {
		Element map = logArea.getDocument().getDefaultRootElement();
		return map.getElementCount();
	}
	
	private void handleLogAreaMouseEvent(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			logViewPopupMenu.getLogAreaPopupMenu().show(logArea, e.getX(), e.getY());
		} else if (e.getButton() == MouseEvent.BUTTON1) {
			if (logArea.getSelectionEnd() - logArea.getSelectionStart() > 0) {
				isSelectedText = true;
			}
		}
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if(e.getAdjustmentType() == AdjustmentEvent.TRACK) {
			if (e.getSource() == verticalBar) {
				int scrollBarMaxValue = verticalBar.getMaximum();
				int scrollBarExtValue = verticalBar.getModel().getExtent();
				
				if (isAutoScroll) {
					if (isDisableAutoWhenSelecteText) {
						return;
					}
					
					if (!isDisableAutoWhenSelecteText && isSelectedText) {
						return;
					}
					
					verticalBar.setValue(scrollBarMaxValue - scrollBarExtValue);
				}
			}
		} else {
			HLog.il("scroll bar event:" + e.getAdjustmentType());
		}
	}

	private class MouseEventAdapter extends MouseAdapter {
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if (e.getSource() == verticalBar) {
				int scrollBarValue = verticalBar.getValue();
				int scrollBarMaxValue = verticalBar.getMaximum();
				int scrollBarExtValue = verticalBar.getModel().getExtent();
				
				if (isAutoScroll) {
					isAutoScroll = false;
					return;
				}
	
				if (scrollBarValue + scrollBarExtValue == scrollBarMaxValue) {
					isAutoScroll = true;
				}			
			}
		}
	
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == logArea) {
				handleLogAreaMouseEvent(e);
			}
			
		}
	}

	@Override
	public void logViewControlEvent(LogViewControlEvent event) {
		switch (event.eventCode) {
		case LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_CLEAR:
			clear();
			break;
		case LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_PAUSE:
			break;
		case LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_START:
			break;
		case LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_ENABLE_AUTO_SCROLL:
			isAutoScroll = true;
			break;
		case LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_DISABLE_AUTO_SCROLL:
			isAutoScroll = false;
			break;
		default:
			break;
		}
	}
}
