package cn.hisdar.alt;

import javax.swing.JFrame;
import javax.swing.UIManager;

import cn.hisdar.alt.view.MainFrame;
import cn.hisdar.lib.log.HLog;
import cn.hisdar.lib.ui.UIAdapter;

public class Main {

	public static void main(String[] args) {
		
		HLog.enableCmdLog();
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//windows·ç¸ñ
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MainFrame mainFrame = new MainFrame();
		mainFrame.setTitle("AndroidLogTool");
		mainFrame.setSize(1080, 720);
		mainFrame.setLocation(UIAdapter.getCenterLocation(null, mainFrame));
		mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}
}
