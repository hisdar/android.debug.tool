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
		fileMenu = new JMenu("�ļ�");
		helpMenu = new JMenu("����");
		editMenu = new JMenu("�༭");
		viewMenu = new JMenu("��ͼ");
		settingsMenu = new JMenu("����");
		
		exitMenuItem = new JMenuItem("�˳�");
		aboutMenuItem = new JMenuItem("����");
		findMenuItem = new JMenuItem("����");
		findNextMenuItem = new JMenuItem("������һ��");
		findPreviousMenuItem = new JMenuItem("������һ��");
		settingsMenuItem = new JMenuItem("����");
	
		logViewMenuItem = new JMenuItem("��־��ͼ");
		debugViewMenuItem = new JMenuItem("������ͼ");
		fileViewItem = new JMenuItem("�ļ���������ͼ");
		fileViewEditerItem = new JMenuItem("�ļ��༭����ͼ");
		
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
