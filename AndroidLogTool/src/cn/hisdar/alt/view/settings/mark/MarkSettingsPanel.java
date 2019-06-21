package cn.hisdar.alt.view.settings.mark;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.hisdar.lib.ui.HLinearPanel;

public class MarkSettingsPanel extends JPanel implements ActionListener {

	private AddItemButton addItemButton;
	private HLinearPanel markListPanel;
	private MarkAddDialog markAddDialog;
	
	private MouseEventHandler mouseEventHandler;
	
	public MarkSettingsPanel(JDialog parent) {
		setLayout(new BorderLayout());

		markListPanel = new HLinearPanel();
		addItemButton = new AddItemButton("+");
		addItemButton.setForeground(Color.BLUE);
		Font labelFont = addItemButton.getFont();
		
		addItemButton.setFont(new Font(labelFont.getFamily(), Font.BOLD, labelFont.getSize()));
		addItemButton.setHorizontalAlignment(JLabel.CENTER);
		
		markListPanel.add(addItemButton);
		
		add(markListPanel, BorderLayout.CENTER);
		
		markAddDialog = new MarkAddDialog(parent);
		
		mouseEventHandler = new MouseEventHandler();
		addItemButton.addMouseListener(mouseEventHandler);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	private class MouseEventHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == addItemButton) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					markAddDialog.setVisible(true);
				}
			}
			super.mouseClicked(e);
		}
		
	}
}
