package cn.hisdar.alt.view.log;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.Border;

import cn.hisdar.lib.ui.HLinearPanel;
import cn.hisdar.lib.ui.TitlePanel;

public class LogControlView extends JPanel {

	private HLinearPanel mainLinearPanel;
	private JPanel searchPanel;
	private JPanel markPanel;
	
	private TitlePanel searchTitle;
	private TitlePanel markTitle;
	
	public LogControlView() {
		setLayout(new BorderLayout());
		
		searchTitle = new TitlePanel("����");
		markTitle   = new TitlePanel("���");
		
		mainLinearPanel = new HLinearPanel();
		searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.add(searchTitle, BorderLayout.NORTH);
		
		markPanel = new JPanel();
		markPanel.setLayout(new BorderLayout());
		markPanel.add(markTitle, BorderLayout.NORTH);
		
		mainLinearPanel.add(searchPanel);
		mainLinearPanel.add(markPanel);
		
		add(mainLinearPanel, BorderLayout.NORTH);
	}
	
	
}
