package cn.hisdar.alt.view.log;

import java.awt.Color;

import javax.swing.text.Style;

import cn.hisdar.lib.adapter.StringAdapter;
import cn.hisdar.lib.log.HLog;

public class MarkAttribute {

	private String separators = "+-=\\|/.,;:?<>() {}[]!@~#$%^&*`'";
	
	private String name;
	private String keyWord;
	private boolean matchCapitals;
	private boolean matchWholeWords;
	private Color foreground;
	private Color background;
	private int fontStyle;
	
	public MarkAttribute() {
	}

	public boolean isSeparator(char ch) {
		for (int i = 0; i < separators.length(); i++) {
			if (ch == separators.charAt(i)) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getStringMatchedIndex(String str, int startIndex) {
		if (startIndex >= str.length()) {
			//HLog.el("index out of bounds, startIndex:" + startIndex + ", length:" + str.length());
			return -1;
		}
		
		String strToCheck = str;
		String keyWordToCheck = keyWord;
		if (!matchCapitals) {
			strToCheck = str.toLowerCase();
			keyWordToCheck = keyWord.toLowerCase();
		}
		
		int index = strToCheck.indexOf(keyWordToCheck, startIndex);
		while (index >= 0) {
			
			if (matchWholeWords) {
				if (index > 0) {
					if (!isSeparator(strToCheck.charAt(index - 1))) {
						return -1;
					}
				}
				
				if (index + keyWordToCheck.length() < strToCheck.length()) {
					if (!isSeparator(strToCheck.charAt(index + keyWordToCheck.length()))) {
						return -1;
					}
				}
				
				return index;
			} else {
				return index;
			}
			
			//index = strToCheck.indexOf(keyWordToCheck, index + keyWordToCheck.length());
		}
		
		return -1;
	}
	
	public static MarkAttribute parseFromString(String str) {
		MarkAttribute markAttribute = new MarkAttribute();
		int index = str.indexOf(':');
		if (index < 0) {
			return null;
		}
		
		str = str.substring(index + 1, str.length());
		String[] configs = str.split(";");
		if (configs == null) {
			return null;
		}
		
		for (int i = 0; i < configs.length; i++) {
			parseStringItem(configs[i], markAttribute);
		}
		
		return markAttribute;
	}
	
	private static void parseStringItem(String str, MarkAttribute markAttribute) {
		
		String[] configItemStrings = str.split("=");
		if (configItemStrings.length != 2) {
			HLog.el("configItemStrings.length:" + configItemStrings.length);
			HLog.el("str:" + str);
			return;
		}
		
		String name = configItemStrings[0].trim();
		String value = configItemStrings[1].trim();
		
		HLog.il("parse:" + name + "=" + value);
		if (name.equals("name")) {
			markAttribute.setName(value);
			return;
		}
		
		if (name.equals("keyWord")) {
			markAttribute.setKeyWord(value);
			return;
		}
		
		if (name.equals("matchCapitals")) {
			if (value.equals("true")) {
				markAttribute.setMatchCapitals(true);
			} else {
				markAttribute.setMatchCapitals(false);
			}
			return;
		}
		
		if (name.equals("matchWholeWords")) {
			if (value.equals("true")) {
				markAttribute.setMatchWholeWords(true);
			} else {
				markAttribute.setMatchWholeWords(false);
			}
			return;
		}
		
		if (name.equals("foreground")) {
			Color color = parseColorFromString(value);
			if (color == null) {
				color = new Color(0, 0, 0);
			}
			markAttribute.setForeground(color);
			return;
		}
		
		if (name.equals("background")) {
			Color color = parseColorFromString(value);
			if (color == null) {
				color = new Color(0, 0, 0);
			}
			HLog.il(color.toString());
			markAttribute.setBackground(color);
			return;
		}
		
		if (name.equals("fontStyle")) {
			if (StringAdapter.isInteger(value)) {
				int fontStyle = Integer.parseInt(value);
				markAttribute.setFontStyle(fontStyle);
			}
			return;
		}
	}
	
	private static Color parseColorFromString(String str) {
		int startIndex = str.indexOf('[');
		if (startIndex < 0) {
			HLog.el("[ not found");
			return null;
		}
		
		int endIndex = str.indexOf(']');
		if (endIndex < 0) {
			HLog.el("] not found");
			return null;
		}
		
		str = str.substring(startIndex + 1, endIndex);
		String[] rgbStrings = str.split(",");
		if (rgbStrings.length != 3) {
			HLog.el("data length not enugth");
			return null;
		}
		
		try {
			int r = Integer.parseInt(rgbStrings[0].substring(2, rgbStrings[0].length()));
			int g = Integer.parseInt(rgbStrings[1].substring(2, rgbStrings[0].length()));
			int b = Integer.parseInt(rgbStrings[2].substring(2, rgbStrings[0].length()));
			
			return new Color(r, g, b);
		} catch (Exception e) {
			HLog.el("number exchange fail");
			HLog.el(e);
			return null;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public boolean isMatchCapitals() {
		return matchCapitals;
	}

	public void setMatchCapitals(boolean matchCapitals) {
		this.matchCapitals = matchCapitals;
	}

	public boolean isMatchWholeWords() {
		return matchWholeWords;
	}

	public void setMatchWholeWords(boolean matchWholeWords) {
		this.matchWholeWords = matchWholeWords;
	}

	public String getSeparators() {
		return separators;
	}

	public void setSeparators(String separators) {
		this.separators = separators;
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public int getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	
	public String toString() {
		String string = "MarkAttribute : " +
				"name="            + name + "; " +
				"keyWord="         + keyWord + "; " +
				"matchCapitals="   + matchCapitals + "; " +
				"matchWholeWords=" + matchWholeWords + "; " +
				"foreground="      + foreground + "; " +
				"background="      + background + "; " +
				"fontStyle="       + fontStyle;
		
		return string;
	}
}
