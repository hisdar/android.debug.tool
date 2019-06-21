package cn.hisdar.alt.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.net.SocketTimeoutException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cn.hisdar.alt.view.settings.mark.MarkSettingsPanel;
import cn.hisdar.lib.ui.UIAdapter;

public class SettingsDialog extends JDialog {

	public final static int SETTINGS_MARK = 0x01;
	
	private JPanel itemPanel;
	private JPanel contextPanel;
	private MarkSettingsPanel markSettingsPanel;
	
	public SettingsDialog(JFrame parent) {
		super(parent);
		setModal(true);
		setTitle("…Ë÷√");
		setSize(800, 400);
		setLayout(new BorderLayout());
		
		itemPanel = new JPanel();
		contextPanel = new JPanel();
		
		itemPanel.setLayout(new FlowLayout());
		contextPanel.setLayout(new BorderLayout());
		
		markSettingsPanel = new MarkSettingsPanel(this);
		
		add(itemPanel, BorderLayout.WEST);
		add(contextPanel, BorderLayout.CENTER);
	}
	
	public void show(int view) {
		switch (view) {
		case SETTINGS_MARK:
			contextPanel.add(markSettingsPanel, BorderLayout.CENTER);
			setLocation(UIAdapter.getCenterLocation(getParent(), this));
			setVisible(true);
			break;

		default:
			break;
		}
	}
}
