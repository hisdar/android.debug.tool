package cn.hisdar.alt.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cn.hisdar.alt.Global;
import cn.hisdar.alt.androiddevice.AndroidDeviceManager;
import cn.hisdar.alt.view.log.LogPanel;
import cn.hisdar.alt.view.phonefile.PhoneFileView;
import cn.hisdar.lib.ui.HSplitPane;
import cn.hisdar.lib.ui.HTabbedPane;
import cn.hisdar.lib.ui.TitlePanel;

public class MainFrame extends JFrame {

	private LogPanel logPanel;
	private MainMenuBar mainMenuBar;
	private ControlPanel controlPanel;
	private SettingsDialog settingsDialog;
	private HSplitPane mainSplitPane;
	
	private PhoneFileView phonefileView;
	private DebugView debugView;
	private FileEditerView fileEditerView;
	private LogView logView;
	
	private JPanel      centerPanel;
	private HSplitPane  centerSplitPane;
	private JPanel      centerBottomPane;
	
	/********************************************************************
	 *         *                                                        *
	 *         *                                                        *
	 *         *                                                        *
	 *         **********************************************************
	 *         *                                                        *
	 *         *                                                        *
	 ********************************************************************/
	
	public MainFrame() {
		
		mainMenuBar = new MainMenuBar();
		setJMenuBar(mainMenuBar);
		
		// left : fileView, center
		mainSplitPane = new HSplitPane(HSplitPane.VERTICAL_SPLIT);
		
		//
		initLeftView();
		
		centerPanel     = new JPanel();
		
		mainSplitPane.setRightComponent(centerPanel);
		
		// center : top : fileEditerView, bottom : logView
		centerPanel.setLayout(new BorderLayout());
		centerSplitPane  = new HSplitPane(HSplitPane.HORIZONTAL_SPLIT);
		fileEditerView   = new FileEditerView();
		centerBottomPane = new JPanel();
		
		centerSplitPane.setTopComponent(fileEditerView);
		centerSplitPane.setBottomComponent(centerBottomPane);
		centerSplitPane.setDividerLocation(0);
		centerPanel.add(centerSplitPane, BorderLayout.CENTER);
		
		logPanel = new LogPanel();
		centerBottomPane.setLayout(new BorderLayout());
		centerBottomPane.add(logPanel, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(mainSplitPane, BorderLayout.CENTER);
		
		controlPanel = new ControlPanel();
		add(controlPanel, BorderLayout.NORTH);
		controlPanel.addLogFiterListener(logPanel);
		
		settingsDialog = new SettingsDialog(this);
		Global.setSettingsDialog(settingsDialog);
	}
	
	private void initLeftView() {
		JPanel      leftViewPanel      = new JPanel();
		TitlePanel  leftViewTitlePanel = new TitlePanel("资源视图");
		HTabbedPane leftTabbedPanel    = new HTabbedPane();
		
		leftViewPanel.setLayout(new BorderLayout());
		leftViewPanel.add(leftViewTitlePanel, BorderLayout.NORTH);
		leftViewPanel.add(leftTabbedPanel, BorderLayout.CENTER);
		
		phonefileView = new PhoneFileView();
		leftTabbedPanel.add(phonefileView, "手机文件系统");
		
		AndroidDeviceManager.getInstance().addAndroidDeviceListener(phonefileView);
		
		mainSplitPane.setLeftComponent(leftViewPanel);
	}
}
