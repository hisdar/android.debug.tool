package cn.hisdar.alt.view.filter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cn.hisdar.lib.log.HLog;
import cn.hisdar.lib.ui.HLinearPanel;

public class LogFilterPanel extends JPanel implements DocumentListener {

	private static final long serialVersionUID = 1L;

	private JLabel filterLabel;
	private JTextField filterTextField;
	private JPanel searchPanel;
	
	private JCheckBox matchWholeWordCheckBox;
	private JCheckBox matchCaseCheckBox;
	private JCheckBox loopLookupCheckBox;
	private JPanel serachOptionPanel;
	
	private ButtonGroup searchModeGroup;
	private JRadioButton searchNomalRadioBotton;
	private JRadioButton searchExpansionRadioBotton;
	private JRadioButton searchRegularExpressionRadioBotton;
	private JPanel searchModePanel;
	
	private JButton searchNextButton;
	private JButton searchPreviousButton;
	private JButton countButton;
	private JButton searchWholeDocumentButton;
	private JButton serachAllDocumentButton;
	private JPanel  searchActionPanel;
	
	private HLinearPanel linearPanel;
	
	private ArrayList<LogFilterListener> logFilterListeners;
	
	public LogFilterPanel() {
		setLayout(new BorderLayout());
		logFilterListeners = new ArrayList<>();
		
		initSearchPanel();
		initSearchOptionPanel();
		initSearchModePanel();
		
		linearPanel = new HLinearPanel();
		linearPanel.add(searchPanel);
		linearPanel.add(serachOptionPanel);
		linearPanel.add(searchModePanel);
		
		add(linearPanel, BorderLayout.CENTER);
		
		filterTextField.getDocument().addDocumentListener(this);
	}
	
	private void initSearchPanel() {
		filterLabel     = new JLabel("搜索内容 :");
		filterTextField = new JTextField();
		
		searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.add(filterLabel, BorderLayout.WEST);
		searchPanel.add(filterTextField, BorderLayout.CENTER);
	}
	
	private void initSearchOptionPanel() {
		
		matchWholeWordCheckBox = new JCheckBox("全词匹配");
		matchCaseCheckBox      = new JCheckBox("匹配大小写");
		loopLookupCheckBox     = new JCheckBox("循环查找");
		serachOptionPanel      = new JPanel();
		serachOptionPanel.setLayout(new GridLayout(3, 1, 0, 0));
		serachOptionPanel.add(matchWholeWordCheckBox);
		serachOptionPanel.add(matchCaseCheckBox);
		serachOptionPanel.add(loopLookupCheckBox);
	}
	
	private void initSearchModePanel() {
		
		searchNomalRadioBotton = new JRadioButton("普通搜索");
		searchExpansionRadioBotton = new JRadioButton("扩展搜索");
		searchRegularExpressionRadioBotton = new JRadioButton("正则表达式");
		
		searchModeGroup = new ButtonGroup();
		searchModeGroup.add(searchNomalRadioBotton);
		searchModeGroup.add(searchExpansionRadioBotton);
		searchModeGroup.add(searchRegularExpressionRadioBotton);
		
		searchModePanel = new JPanel();
		searchModePanel.setLayout(new GridLayout(3, 1 ,0 ,0));
		searchModePanel.add(searchNomalRadioBotton);
		searchModePanel.add(searchExpansionRadioBotton);
		searchModePanel.add(searchRegularExpressionRadioBotton);
	}
	
	public void addLogFiterListener(LogFilterListener l) {
		for (int i = 0; i < logFilterListeners.size(); i++) {
			if (logFilterListeners.get(i) == l) {
				return ;
			}
		}
		
		logFilterListeners.add(l);
	}
	
	private void notifyLogFilterChangeEvent(String filter) {
		HLog.il("notify log filter:" + filter);
		for (int i = 0; i < logFilterListeners.size(); i++) {
			logFilterListeners.get(i).logFilterChangeEvent(filter);
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		notifyLogFilterChangeEvent(filterTextField.getText());
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		notifyLogFilterChangeEvent(filterTextField.getText());
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		notifyLogFilterChangeEvent(filterTextField.getText());
		
	}
}
