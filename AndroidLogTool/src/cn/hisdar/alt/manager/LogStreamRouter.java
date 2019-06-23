package cn.hisdar.alt.manager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.hisdar.alt.Global;
import cn.hisdar.alt.androiddevice.AndroidDevice;
import cn.hisdar.alt.view.log.LogView;
import cn.hisdar.lib.adapter.FileAdapter;
import cn.hisdar.lib.log.HLog;

public class LogStreamRouter {

	private LogView logView;
	private String logFilter;
	private LogQueue logQueue;
	private String logFileSavePath;
	private AndroidDevice androidDevice;
	private LogOutputThread outputThread;
	private LogInputStreamThread inputThread;

	private boolean isNeedToClearOutputView;
	
	private FileOutputStream fileOutputStream;
	
	public LogStreamRouter(AndroidDevice device, LogView view) {
		logView = view;
		androidDevice = device;
		isNeedToClearOutputView = false;
		
		logQueue = new LogQueue();
		logFilter = "";
		
		outputThread = new LogOutputThread();
		inputThread = new LogInputStreamThread();
	}
	
	public void start() {
		if (fileOutputStream != null) {
			try {
				fileOutputStream.close();
				fileOutputStream = null;
			} catch (IOException e) {}
		}
		
		FileAdapter.initFolder(Global.logcatSavePath);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss-SSS");
		String dateString = dateFormat.format(new Date());
		String savePath = FileAdapter.pathCat(Global.logcatSavePath, dateString);
		savePath += ".log";
		try {
			fileOutputStream = new FileOutputStream(savePath);
		} catch (FileNotFoundException e) {
			HLog.el("open file fail:" + savePath);
			HLog.el(e);
			fileOutputStream = null;
		}
		
		outputThread.start();
		inputThread.start();
	}
	
	public void setLogFileSavePath(String path) {
		logFileSavePath = path;
	}
	
	public void saveToFile(String lineString) {
		if (fileOutputStream == null) {
			return;
		}
		
		try {
			fileOutputStream.write(lineString.getBytes());
		} catch (IOException e) {
			HLog.el("logcat file write fail");
			HLog.el(e);
			fileOutputStream = null;
		}
	}
	
	public void exit() {
		inputThread.stopThread(false);
		outputThread.stopThread(false);
		
		try {
			fileOutputStream.close();
		} catch (IOException e) {}
		
		fileOutputStream = null;
	}
	
	public void setLogFilter(String filter) {
		this.logFilter = filter;
		isNeedToClearOutputView = true;
		
		HLog.il("set filter to:" + filter);
	}
	
	private class LogInputStreamThread extends Thread {
		
		private boolean isExit;
		private boolean isThreadRunning;
		
		public LogInputStreamThread() {
			isExit = false;
			isThreadRunning = false;
		}
		
		public void stopThread(boolean sync) {
			isExit = true;
			
			if (sync) {
				while (isThreadRunning) {
					try {
						sleep(10);
					} catch (InterruptedException e) {}
				}
			}
		}
		
		@Override
		public void run() {
			isThreadRunning = true;
			
			String lineString;
			BufferedReader logReader = null;
			while (!isExit) {
				
				if (logReader == null) {
					InputStream inputStream = androidDevice.getLogcatInputStream();
					InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
					logReader = new BufferedReader(inputStreamReader);
				}
				
				try {
					lineString = logReader.readLine();
				} catch (IOException e) {
					logReader = null;
					continue;
				}
				
				if (lineString == null) {
					HLog.il("read a null data, reset inputstream");
					logReader = null;
					continue;
				}
				
				saveToFile(lineString + "\n");
				logQueue.push(lineString);
			}
			
			isThreadRunning = false;
		}
	}
	
	private class LogOutputThread extends Thread {
		
		private boolean isExit;
		private boolean isThreadRunning;
		
		public LogOutputThread() {
			isExit = false;
			isThreadRunning = false;
		}
		
		public void stopThread(boolean sync) {
			isExit = true;
			
			if (sync) {
				while (isThreadRunning) {
					try {
						sleep(10);
					} catch (InterruptedException e) {}
				}
			}
		}
		
		@Override
		public void run() {
			
			isThreadRunning = true;
			while (!isExit) {
				
				if (isNeedToClearOutputView) {
					isNeedToClearOutputView = false;
					logView.clear();
					logQueue.resetLogPointer();
					HLog.il("reset filter to:" + logFilter);
				}
				
				if (!logQueue.haveNextLog()) {
					try {
						sleep(10);
					} catch (InterruptedException e) {}
					continue;
				}
				
				String logString = logQueue.getNextLog();
				
				if (logFilter.length() > 0 && logString.indexOf(logFilter) < 0) {
					continue;
				}
				
				if (!logView.isPaused()) {
					logView.output(logString);
				} else {
					
				}
			}
			
			isThreadRunning = false;
		}
	}
}
