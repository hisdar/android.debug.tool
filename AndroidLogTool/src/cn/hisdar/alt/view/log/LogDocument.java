package cn.hisdar.alt.view.log;

import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

public class LogDocument extends DefaultStyledDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogDocument() {
		super();
	}
	
	public LogDocument(StyleContext styles) {
		super(styles);
	}
	
	public LogDocument(AbstractDocument.Content c, StyleContext styles) {
		super(c, styles);
	}
}
