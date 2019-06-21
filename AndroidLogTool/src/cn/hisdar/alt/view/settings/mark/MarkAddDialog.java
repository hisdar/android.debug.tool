package cn.hisdar.alt.view.settings.mark;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import cn.hisdar.lib.ui.HLinearPanel;
import cn.hisdar.lib.ui.UIAdapter;

public class MarkAddDialog extends JDialog {

	private JPanel actionPanel;
	private HLinearPanel editPanel;
	
	private JPanel namePanel;
	private JLabel nameLabel;
	private JTextField nameField;
	
	private JPanel foregroundColorPanel;
	private JLabel foregroundColorNameLabel;
	private JLabel foregroundColorSelectLabel;
	
	private JPanel backgroundColorPanel;
	private JLabel backgroundColorNameLabel;
	private JLabel backgroundColorSelectLable;
	
	
	public MarkAddDialog(JDialog parent) {
		super(parent);
		setLayout(new BorderLayout());
		setTitle("添加标记");
		setSize(600, 300);
		setLocation(UIAdapter.getCenterLocation(null, this));
		
		editPanel = new HLinearPanel();
		Dimension nameLabelSize = new Dimension(100, 20);
		
		namePanel = new JPanel(new BorderLayout());
		nameLabel = new JLabel("名字");
		nameField = new JTextField();
		namePanel.add(nameLabel, BorderLayout.WEST);
		namePanel.add(nameField, BorderLayout.CENTER);
		nameLabel.setPreferredSize(nameLabelSize);
		
		foregroundColorPanel = new JPanel(new BorderLayout());
		foregroundColorNameLabel = new JLabel("文字颜色");
		foregroundColorSelectLabel = new JLabel("");
		foregroundColorSelectLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		foregroundColorPanel.add(foregroundColorNameLabel, BorderLayout.WEST);
		foregroundColorPanel.add(foregroundColorSelectLabel, BorderLayout.CENTER);
		foregroundColorNameLabel.setPreferredSize(nameLabelSize);
		
		backgroundColorPanel = new JPanel(new BorderLayout());
		backgroundColorNameLabel = new JLabel("背景颜色");
		backgroundColorSelectLable = new JLabel("");
		backgroundColorPanel.add(backgroundColorNameLabel, BorderLayout.WEST);
		backgroundColorPanel.add(backgroundColorSelectLable, BorderLayout.CENTER);
		backgroundColorNameLabel.setPreferredSize(nameLabelSize);
		
		editPanel.add(namePanel);
		editPanel.add(foregroundColorPanel);
		editPanel.add(backgroundColorPanel);
		
		add(editPanel, BorderLayout.CENTER);
	}
	
	
}
