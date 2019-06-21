package cn.hisdar.alt.androiddevice;

import java.util.ArrayList;

import cn.hisdar.lib.log.HLog;

public class AndroidDeviceManager {

	private static AndroidDeviceManager mainDeviceManager = null;
	
	private AndroidDeviceWatcher androidDeviceWatcher;
	private ArrayList<AndroidDeviceListener> androidDeviceListeners;
	
	private AndroidDeviceManager() {
		androidDeviceListeners = new ArrayList<>();
		androidDeviceWatcher = new AndroidDeviceWatcher();
		androidDeviceWatcher.start();
	}
	
	public static AndroidDeviceManager getInstance() {
		if (mainDeviceManager == null) {
			synchronized (AndroidDeviceManager.class) {
				if (mainDeviceManager == null) {
					mainDeviceManager = new AndroidDeviceManager();
				}
			}
		}
		
		return mainDeviceManager;
	}
	
	public void addAndroidDeviceListener(AndroidDeviceListener l) {
		for (int i = 0; i < androidDeviceListeners.size(); i++) {
			if (androidDeviceListeners.get(i) == l) {
				return;
			}
		}
		
		androidDeviceListeners.add(l);
	}
	
	private void notifyAndroidDeviceStateChangeEvent(AndroidDevice androidDevice, int state) {
		for (int i = 0; i < androidDeviceListeners.size(); i++) {
			androidDeviceListeners.get(i).androidDeviceStateChangeEvent(androidDevice, state);
		}
	}
	
	public void refurbish() {
		androidDeviceWatcher.refurbish();
	}
	
	private class AndroidDeviceWatcher extends Thread {
		
		private boolean isExit;
		private ArrayList<AndroidDevice> androidDevices;
		
		public AndroidDeviceWatcher() {
			isExit = false;
			androidDevices = new ArrayList<>();
		}
		
		private boolean isDeviceExist(AndroidDevice androidDevice) {
			for (int j = 0; j < androidDevices.size(); j++) {
				if (androidDevices.get(j).getDeviceID().equals(androidDevice.getDeviceID())) {
					return true;
				}
			}
			
			return false;
		}
		
		private boolean isDeviceExist(AndroidDevice[] androidDevices, AndroidDevice androidDevice) {
			if (androidDevices == null) {
				return false;
			}
			
			for (int i = 0; i < androidDevices.length; i++) {
				if (androidDevice.getDeviceID().equals(androidDevices[i].getDeviceID())) {
					return true;
				}
			}
			
			return false;
		}
		
		private void removeAndroidDevice(AndroidDevice androidDevice) {
			for (int i = 0; i < androidDevices.size(); i++) {
				AndroidDevice curDevice = androidDevices.get(i);
				if (androidDevice.getDeviceID().equals(curDevice.getDeviceID())) {
					int state = AndroidDevice.ANDROID_DEVICE_STATE_DISCONNECT;
					notifyAndroidDeviceStateChangeEvent(curDevice, state);
					androidDevices.remove(i);
					
					HLog.il("device " + curDevice.getDeviceName() + " have removed");
					return;
				}
			}
		}
		
		public void refurbish() {
			for (int i = 0; i < androidDevices.size(); i++) {
				androidDevices.remove(i);
			}

			interrupt();
		}
		
		@Override
		public void run() {
			while (!isExit) {
				AndroidDevice[] deviceses = AndroidDeviceAdapter.getInstance().getAndroidDevices();
				if (deviceses == null || deviceses.length <= 0) {
					for (int i = 0; i < androidDevices.size(); i++) {
						removeAndroidDevice(androidDevices.get(i));
					}
					continue;
				}
				
				for (int i = 0; i < deviceses.length; i++) {
					if (!isDeviceExist(deviceses[i])) {
						// notify new android device connected
						int state = AndroidDevice.ANDROID_DEVICE_STATE_CONNECT;
						notifyAndroidDeviceStateChangeEvent(deviceses[i], state);
						androidDevices.add(deviceses[i]);
					}
				}
				
				for (int i = androidDevices.size(); i > 0 ; i--) {
					int index = i - 1;
					if (!isDeviceExist(deviceses, androidDevices.get(index))) {
						removeAndroidDevice(androidDevices.get(index));
					}
				}
				
				try {
					sleep(100);
				} catch (InterruptedException e) {}
			}
		}
	}
}
