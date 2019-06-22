package cn.hisdar.alt.view.log;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.Border;

import cn.hisdar.alt.view.filter.LogFilterListener;
import cn.hisdar.alt.view.filter.LogFilterPanel;
import cn.hisdar.lib.ui.HLinearPanel;
import cn.hisdar.lib.ui.TitlePanel;

public class LogControlView extends JPanel {

	private HLinearPanel mainLinearPanel;
	private JPanel searchPanel;
	private LogFilterPanel logFilterPanel;
	private JPanel markPanel;
	
	private TitlePanel searchTitle;
	private TitlePanel markTitle;
	
	public LogControlView() {
		setLayout(new BorderLayout());
		
		searchTitle = new TitlePanel("ËÑË÷");
		markTitle   = new TitlePanel("±ê¼Ç");
		
		mainLinearPanel = new HLinearPanel();
		searchPanel = new JPanel();
		logFilterPanel = new LogFilterPanel();
		searchPanel.setLayout(new BorderLayout());
		searchPanel.add(searchTitle, BorderLayout.NORTH);
		searchPanel.add(logFilterPanel, BorderLayout.CENTER);

		markPanel = new JPanel();
		markPanel.setLayout(new BorderLayout());
		markPanel.add(markTitle, BorderLayout.NORTH);
		
		mainLinearPanel.add(searchPanel);
		mainLinearPanel.add(markPanel);
		
		add(mainLinearPanel, BorderLayout.NORTH);
	}
	
	public LogFilterPanel getLogFilterPanel() {
		return logFilterPanel;
	}
}
