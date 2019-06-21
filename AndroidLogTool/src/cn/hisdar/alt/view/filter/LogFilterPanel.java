package cn.hisdar.alt.view.filter;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cn.hisdar.lib.log.HLog;

public class LogFilterPanel extends JPanel implements DocumentListener {

	private JTextField filterTextField;
	private JComboBox<String> filterTypeComboBox;
	
	private String[] filterTypes = {"Common", "Extend", "Regular expression"};
	
	private ArrayList<LogFilterListener> logFilterListeners;
	
	public LogFilterPanel() {
		setLayout(new BorderLayout());
		logFilterListeners = new ArrayList<>();
		
		filterTextField = new JTextField();
		filterTypeComboBox = new JComboBox<String>(filterTypes);
		//add(filterTypeComboBox, BorderLayout.WEST);
		add(filterTextField, BorderLayout.CENTER);
		
		filterTextField.getDocument().addDocumentListener(this);
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
