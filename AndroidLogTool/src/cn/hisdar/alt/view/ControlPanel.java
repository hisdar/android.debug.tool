package cn.hisdar.alt.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import cn.hisdar.alt.view.filter.LogFilterListener;
import cn.hisdar.alt.view.filter.LogFilterPanel;

public class ControlPanel extends JPanel {

	private JButton settingsButton;
	private JButton openWithEditerButton;
	private JButton startAndStopButton;
	private JButton clearLogButton;
	private JButton clearLogFileButton;
	private JButton openLogFolderButton;
	private JButton newLogFileButton;
	
	private JPanel controlButtonPanel;
	
	public ControlPanel() {
		
		controlButtonPanel = new JPanel();
		//controlButtonPanel.setLayout(new GridLayout(1, 7, 5, 5));
		
		ImageIcon fileIcon = new ImageIcon("img/file_icon.png");
		ImageIcon settingsIcon = new ImageIcon("img/settings.png");
		ImageIcon startIcon = new ImageIcon("img/start.png");
		ImageIcon stopIcon = new ImageIcon("img/stop.png");
		ImageIcon deleteUpIcon = new ImageIcon("img/delete_up.png");
		ImageIcon clearLogIcon = new ImageIcon("img/delete-log-icon.png");
		ImageIcon newLogFileIcon = new ImageIcon("img/new-log-file.png");
		ImageIcon openFolder = new ImageIcon("img/open-folder.png");
		
		settingsButton = new JButton(settingsIcon);
		openWithEditerButton = new JButton(fileIcon);
		startAndStopButton = new JButton(startIcon);
		clearLogButton = new JButton(deleteUpIcon);
		clearLogFileButton = new JButton(clearLogIcon);
		openLogFolderButton = new JButton(openFolder);
		newLogFileButton = new JButton(newLogFileIcon);
		
		settingsButton.setOpaque(false);
		openWithEditerButton.setOpaque(false);
		startAndStopButton.setOpaque(false);
		clearLogButton.setOpaque(false);
		clearLogFileButton.setOpaque(false);
		openLogFolderButton.setOpaque(false);
		newLogFileButton.setOpaque(false);
		
		Border buttonBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		
		settingsButton.setBorder(buttonBorder);
		openWithEditerButton.setBorder(buttonBorder);
		startAndStopButton.setBorder(buttonBorder);
		clearLogButton.setBorder(buttonBorder);
		clearLogFileButton.setBorder(buttonBorder);
		openLogFolderButton.setBorder(buttonBorder);
		newLogFileButton.setBorder(buttonBorder);
		
		controlButtonPanel.add(startAndStopButton);
		controlButtonPanel.add(newLogFileButton);
		controlButtonPanel.add(openWithEditerButton);
		controlButtonPanel.add(openLogFolderButton);
		controlButtonPanel.add(clearLogButton);
		controlButtonPanel.add(clearLogFileButton);
		controlButtonPanel.add(openLogFolderButton);
		controlButtonPanel.add(settingsButton);
		
		setBackground(new Color(0xF0, 0xF8, 0xFF));
		
		setLayout(new BorderLayout());
		add(controlButtonPanel, BorderLayout.WEST);
		
	}
	
}
