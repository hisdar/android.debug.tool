package cn.hisdar.alt.view.phonefile;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.hisdar.lib.adapter.FileAdapter;
import cn.hisdar.lib.log.HLog;
import cn.hisdar.lib.ui.HLinearPanel;

public class FileView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int dividingLineSize = 5;
	public static final int FILE_VIEW_EVENT_MOUSE_IN          = 0x8001;
	public static final int FILE_VIEW_EVENT_MOUSE_OUT         = 0x8002;
	public static final int FILE_VIEW_EVENT_MOUSE_PRESSED     = 0x8003;
	public static final int FILE_VIEW_EVENT_MOUSE_RELEASE     = 0x8004;
	public static final int FILE_VIEW_EVENT_MOUSE_CLICKED     = 0x8005;
	public static final int FILE_VIEW_EVENT_MOUSE_WHEEL_MOVED = 0x8006;
	public static final int FILE_VIEW_EVENT_MOUSE_DRAGGED     = 0x8007;
	public static final int FILE_VIEW_EVENT_MOUSE_MOVED       = 0x8008;
	
	private JLabel fileIconLabel;
	private JLabel fileNameLabel;
	private JLabel fileLastModifyTimeLabel;
	private JLabel fileTypeLabel;
	private JLabel fileSizeLabel;
	
	private HLinearPanel fileViewPanel;

	private FileAttributes fileAttributes;
	private MouseListenerAdapter mouseListenerAdapter;
	private ImageIcon folderIcon;
	
	private ArrayList<FileViewListener> fileViewListeners;
	
	private boolean isMouseIn;
	
	public FileView(FileAttributes fileAttributes) {
		this.fileAttributes = fileAttributes;
		
		isMouseIn = false;
		fileViewListeners = new ArrayList<>();
		mouseListenerAdapter = new MouseListenerAdapter(this);
		
		fileIconLabel = new JLabel();
		fileNameLabel = new JLabel(fileAttributes.getName());
		fileTypeLabel = new JLabel(fileAttributes.getType() + "");
		fileSizeLabel = new JLabel(fileAttributes.getSize() + "");
		fileLastModifyTimeLabel = new JLabel(fileAttributes.getLastAccessTime());
		
		fileViewPanel = new HLinearPanel(HLinearPanel.HORIZONTAL);
		fileViewPanel.add(fileIconLabel);
		fileViewPanel.add(fileNameLabel);
		fileViewPanel.add(fileLastModifyTimeLabel);
		fileViewPanel.add(fileTypeLabel);
		fileViewPanel.add(fileSizeLabel);
		
		setLayout(new BorderLayout());
		add(fileViewPanel, BorderLayout.CENTER);
		fileViewPanel.setDividingLineSize(dividingLineSize);
		
		fileIconLabel.addMouseListener(mouseListenerAdapter);
		fileNameLabel.addMouseListener(mouseListenerAdapter);
		fileTypeLabel.addMouseListener(mouseListenerAdapter);
		fileSizeLabel.addMouseListener(mouseListenerAdapter);
		fileLastModifyTimeLabel.addMouseListener(mouseListenerAdapter);
		
		setIcon();
		setOpaque(true);
	}
	
	private void setIcon() {
		
		if (fileAttributes.getType() == FileAttributes.FILE_TYPE_FOLDER) {
			if (!new File("./img/folder-icon.png").exists()) {
				return;
			}
			
			folderIcon = new ImageIcon("./img/folder-icon.png");
			fileIconLabel.setIcon(folderIcon);
			return;
		}
		
		fileIconLabel.setPreferredSize(new Dimension(22, 16));
	}
	
	public void addFileViewListener(FileViewListener listener) {
		for (int i = 0; i < fileViewListeners.size(); i++) {
			if (fileViewListeners.get(i) == listener) {
				return ;
			}
		}
		
		fileViewListeners.add(listener);
	}
	
	public void setMouseIn(boolean isMouseIn) {
		
		this.isMouseIn = isMouseIn;
		if (isMouseIn) {
			setLabelsBackground(true, new Color(0xCAE1FF));
		} else {
			setLabelsBackground(false, null);
		}
	}
	
	public void setSelected(boolean selected) {
	}
	
	public void setLabelsBackground(boolean opaque, Color color) {
	
		fileIconLabel.setOpaque(opaque);
		fileNameLabel.setOpaque(opaque);
		fileTypeLabel.setOpaque(opaque);
		fileSizeLabel.setOpaque(opaque);
		fileLastModifyTimeLabel.setOpaque(opaque);
		
		fileIconLabel.setBackground(color);
		fileNameLabel.setBackground(color);
		fileTypeLabel.setBackground(color);
		fileSizeLabel.setBackground(color);
		fileLastModifyTimeLabel.setBackground(color);
	}
	
	public static int getLabelStringWidth(JLabel label) {
		
		if (label == null) {
			HLog.il("label is null");
			return 0;
		}
		
		Graphics g = label.getGraphics();
		if (g == null) {
			HLog.il("label:" + label.getText() + " graphics is null");
			return 0;
		}
		
		FontMetrics fontMetrics = g.getFontMetrics(label.getFont());
		return fontMetrics.stringWidth(label.getText());
	}
	
	public FileAttributes getFileAttributes() {
		return fileAttributes;
	}

	public JLabel getFileIconLabel() {
		return fileIconLabel;
	}

	public void setFileIconLabel(JLabel fileIconLabel) {
		this.fileIconLabel = fileIconLabel;
	}

	public JLabel getFileNameLabel() {
		return fileNameLabel;
	}

	public void setFileNameLabel(JLabel fileNameLabel) {
		this.fileNameLabel = fileNameLabel;
	}

	public JLabel getFileLastModifyTimeLabel() {
		return fileLastModifyTimeLabel;
	}

	public void setFileLastModifyTimeLabel(JLabel fileLastModifyTimeLabel) {
		this.fileLastModifyTimeLabel = fileLastModifyTimeLabel;
	}

	public JLabel getFileTypeLabel() {
		return fileTypeLabel;
	}

	public void setFileTypeLabel(JLabel fileTypeLabel) {
		this.fileTypeLabel = fileTypeLabel;
	}

	public JLabel getFileSizeLabel() {
		return fileSizeLabel;
	}

	public void setFileSizeLabel(JLabel fileSizeLabel) {
		this.fileSizeLabel = fileSizeLabel;
	}

	public HLinearPanel getFileViewPanel() {
		return fileViewPanel;
	}

	public void setFileViewPanel(HLinearPanel fileViewPanel) {
		this.fileViewPanel = fileViewPanel;
	}
	
	public boolean isMouseIn() {
		return isMouseIn;
	}

	private class MouseListenerAdapter extends MouseAdapter {

		private FileView fileView;
		public MouseListenerAdapter(FileView fileView) {
			this.fileView = fileView;
		}
		
		private void notifyEvent(FileView fileView, MouseEvent e, int event) {
			for (int i = 0; i < fileViewListeners.size(); i++) {
				fileViewListeners.get(i).fileViewEvent(fileView, e, event);
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			notifyEvent(fileView, e, FILE_VIEW_EVENT_MOUSE_OUT);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			notifyEvent(fileView, e, FILE_VIEW_EVENT_MOUSE_IN);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			notifyEvent(fileView, e, FILE_VIEW_EVENT_MOUSE_CLICKED);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			notifyEvent(fileView, e, FILE_VIEW_EVENT_MOUSE_PRESSED);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			notifyEvent(fileView, e, FILE_VIEW_EVENT_MOUSE_RELEASE);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			notifyEvent(fileView, e, FILE_VIEW_EVENT_MOUSE_WHEEL_MOVED);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			notifyEvent(fileView, e, FILE_VIEW_EVENT_MOUSE_DRAGGED);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			notifyEvent(fileView, e, FILE_VIEW_EVENT_MOUSE_MOVED);
		}
	}
}