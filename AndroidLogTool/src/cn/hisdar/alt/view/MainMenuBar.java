package cn.hisdar.alt.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MainMenuBar extends JMenuBar {

	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenu settingsMenu;
	
	private JMenuItem exitMenuItem;
	private JMenuItem aboutMenuItem;
	private JMenuItem findMenuItem;
	private JMenuItem findNextMenuItem;
	private JMenuItem findPreviousMenuItem;
	private JMenuItem settingsMenuItem;
	
	private JMenuItem logViewMenuItem;
	private JMenuItem debugViewMenuItem;
	private JMenuItem fileViewItem;
	private JMenuItem fileViewEditerItem;
	
	public MainMenuBar() {
		fileMenu = new JMenu("文件");
		helpMenu = new JMenu("帮助");
		editMenu = new JMenu("编辑");
		viewMenu = new JMenu("视图");
		settingsMenu = new JMenu("设置");
		
		exitMenuItem = new JMenuItem("退出");
		aboutMenuItem = new JMenuItem("关于");
		findMenuItem = new JMenuItem("查找");
		findNextMenuItem = new JMenuItem("查找下一个");
		findPreviousMenuItem = new JMenuItem("查找上一个");
		settingsMenuItem = new JMenuItem("设置");
	
		logViewMenuItem = new JMenuItem("日志视图");
		debugViewMenuItem = new JMenuItem("调试视图");
		fileViewItem = new JMenuItem("文件管理器视图");
		fileViewEditerItem = new JMenuItem("文件编辑器视图");
		
		fileMenu.add(exitMenuItem);
		editMenu.add(findMenuItem);
		editMenu.add(findPreviousMenuItem);
		editMenu.add(findNextMenuItem);
		
		viewMenu.add(logViewMenuItem);
		viewMenu.add(debugViewMenuItem);
		viewMenu.add(fileViewItem);
		viewMenu.add(fileViewEditerItem);
		
		settingsMenu.add(settingsMenuItem);
		helpMenu.add(aboutMenuItem);
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		viewMenu.setMnemonic(KeyEvent.VK_V);
		settingsMenu.setMnemonic(KeyEvent.VK_S);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		add(fileMenu);
		add(editMenu);
		add(viewMenu);
		add(settingsMenu);
		add(helpMenu);
	}
}
