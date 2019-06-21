package cn.hisdar.alt.view.settings.mark;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JLabel;

public class AddItemButton extends JButton {

	public AddItemButton(String text) {
		super(text);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Dimension size = getSize();
		g.setColor(new Color(0, 0, 255));
		g.drawRect(0, 0, size.width - 1, size.height - 1);
	}
	
	
}
