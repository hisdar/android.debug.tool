package cn.hisdar.alt.androiddevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import cn.hisdar.lib.log.HLog;

public class CommandLine {

	public static boolean execCommand(String command) {
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			HLog.el(e);
			return false;
		}
		
		return true;
	}
	
	public static StringBuffer execCommandAndGetResult(String command) {
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		StringBuffer resuletData = new StringBuffer();
		
		try {
			process = runtime.exec(command);
			
			// ����2���̣߳��ֱ��ȡ�������������ʹ�����������
            CommandLineExecResultReadThread stdoutUtil
            	= new CommandLineExecResultReadThread(process.getInputStream(), resuletData);
            CommandLineExecResultReadThread erroroutUtil
            	= new CommandLineExecResultReadThread(process.getErrorStream(), null);
            stdoutUtil.start();
            erroroutUtil.start();

			process.waitFor();
		} catch (IOException | InterruptedException e) {
			HLog.el(e);
			return null;
		}
		
		return resuletData;
	}
	
}
