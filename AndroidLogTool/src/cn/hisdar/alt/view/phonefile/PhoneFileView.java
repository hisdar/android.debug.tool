package cn.hisdar.alt.view.phonefile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.hisdar.alt.androiddevice.AndroidDevice;
import cn.hisdar.alt.androiddevice.AndroidDeviceListener;
import cn.hisdar.lib.log.HLog;
import cn.hisdar.lib.ui.HLinearPanel;

public class PhoneFileView extends JPanel implements AndroidDeviceListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel controlPanel;
	private FolderView folderView;
	private AndroidDevice androidDevice;
	private PhoneFileSystem phoneFileSystem;
	private int androidDeviceState;
	
	public PhoneFileView() {
		controlPanel = new JPanel();
		folderView = new FolderView(null);
		
		setLayout(new BorderLayout());
		
		add(controlPanel, BorderLayout.NORTH);
		add(folderView, BorderLayout.CENTER);
	}
	
	@Override
	public void androidDeviceStateChangeEvent(AndroidDevice androidDevice, int state) {
		this.androidDevice = androidDevice;
		this.androidDeviceState  = state;
		if (state == AndroidDevice.ANDROID_DEVICE_STATE_CONNECT) {
			phoneFileSystem = new PhoneFileSystem(androidDevice);
			folderView.setFileSystem(phoneFileSystem);
			update("/");
		} else {
			folderView.clear();
		}
	}
	
	private boolean update(String path) {
		
		if (androidDeviceState != AndroidDevice.ANDROID_DEVICE_STATE_CONNECT) {
			return false;
		}
		
		ArrayList<FileAttributes> childFiles = phoneFileSystem.listAllFiles(path);
		folderView.addFiles(childFiles);
		
		invalidate();
		getParent().invalidate();
		
		return true;
	}
	
}
