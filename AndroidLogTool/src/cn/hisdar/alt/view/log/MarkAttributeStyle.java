package cn.hisdar.alt.view.log;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.Style;

public class MarkAttributeStyle {
	
	private MarkAttribute attribute;
	private MutableAttributeSet style;
	
	public MarkAttributeStyle() {
	}

	public MarkAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(MarkAttribute attribute) {
		this.attribute = attribute;
	}

	public MutableAttributeSet getStyle() {
		return style;
	}

	public void setStyle(MutableAttributeSet style) {
		this.style = style;
	}

	
}
