package cn.hisdar.alt.view.log;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import cn.hisdar.alt.androiddevice.AndroidDevice;
import cn.hisdar.alt.androiddevice.AndroidDeviceListener;
import cn.hisdar.alt.androiddevice.AndroidDeviceManager;
import cn.hisdar.alt.manager.LogStreamRouter;
import cn.hisdar.alt.view.filter.LogFilterListener;
import cn.hisdar.lib.configuration.ConfigItem;
import cn.hisdar.lib.configuration.HConfig;
import cn.hisdar.lib.log.HLog;
import cn.hisdar.lib.ui.HLinearPanel;
import cn.hisdar.lib.ui.HSplitPane;

public class LogPanel extends JPanel
implements AndroidDeviceListener, LogFilterListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LogView logView;
	private LogControlView logControlView;
	private HSplitPane logViewAndControl;
	
	private MarkAttribute searchAttr;
	private LogStreamRouter logStreamRouter = null;
	private String logFilter = "";
	
	public LogPanel() {
		logViewAndControl = new HSplitPane(HSplitPane.VERTICAL_SPLIT);
		
		logView = new LogView();
		logControlView = new LogControlView();
		logViewAndControl.setLeftComponent(logView);
		logViewAndControl.setRightComponent(logControlView);
		logViewAndControl.setDividerLocation(0.85);
		
		setLayout(new BorderLayout());
		add(logViewAndControl, BorderLayout.CENTER);
		AndroidDeviceManager.getInstance().addAndroidDeviceListener(this);
	}

	@Override
	public void androidDeviceStateChangeEvent(AndroidDevice androidDevice, int state) {
		if (state == AndroidDevice.ANDROID_DEVICE_STATE_CONNECT) {
			
			HLog.il("new device found:" + androidDevice.getDeviceID());
			if (androidDevice != null) {
				logView.clear();
				logStreamRouter = new LogStreamRouter(androidDevice, logView);
				logStreamRouter.start();
			}
		} else if (state == AndroidDevice.ANDROID_DEVICE_STATE_DISCONNECT) {
			logStreamRouter.exit();
			logStreamRouter = null;
		}
	}

	@Override
	public void logFilterChangeEvent(String filter) {
		
		if (filter.length() == 0) {
			logView.removeFilterAttribute(searchAttr);
			logStreamRouter.setLogFilter(filter);
			return;
		}
		
		if (logFilter.equals(filter)) {
			return;
		}
		
		logFilter = filter;
		
		MarkAttribute filterAttribute = new MarkAttribute();
		filterAttribute.setKeyWord(filter);
        filterAttribute.setMatchCapitals(false);
        filterAttribute.setName(filter + " attr");
        filterAttribute.setMatchWholeWords(false);
        filterAttribute.setBackground(new Color(50, 255, 50));
        filterAttribute.setForeground(Color.BLACK);
        
        logView.removeFilterAttribute(searchAttr);
        searchAttr = filterAttribute;
        logView.addFilterAttribute(filterAttribute);
        
        if (logStreamRouter != null) {
        	logStreamRouter.setLogFilter(filter);
        }
	}
}
