package cn.hisdar.alt;

import cn.hisdar.alt.view.SettingsDialog;

public class Global {

	private static SettingsDialog settingsDialog = null;
	
	public static String logcatSavePath = "./user/save/logcat";
	
	public static void setSettingsDialog(SettingsDialog dlg) {
		settingsDialog = dlg;
	}
	
	public static SettingsDialog getSettingsDialog() {
		return settingsDialog;
	}
}
