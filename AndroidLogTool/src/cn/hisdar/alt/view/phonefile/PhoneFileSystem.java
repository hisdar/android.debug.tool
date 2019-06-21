package cn.hisdar.alt.view.phonefile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.hisdar.alt.androiddevice.AndroidDevice;
import cn.hisdar.lib.adapter.FileAdapter;
import cn.hisdar.lib.log.HLog;

public class PhoneFileSystem implements FileSystemInterface {

	private AndroidDevice androidDevice;
	
	public PhoneFileSystem(AndroidDevice androidDevice) {
		this.androidDevice = androidDevice;
	}
	
	@Override
	public int open(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<FileAttributes> listAllFiles(String path) {
		
		ArrayList<FileAttributes> childFiles = new ArrayList<>();
		
		String cmdPath = path;
		if (cmdPath.charAt(cmdPath.length() - 1) != '/') {
			cmdPath += "/";
		}
		
		String cmd = "ls -al " + cmdPath;
		StringBuffer result = androidDevice.adbShell(cmd);
		
		HLog.il("cmd exec finished");
		
		if (result == null) {
			HLog.el("result is null, cmd:" + cmd);
			return childFiles;
		}
		
		if (result.length() <= 0) {
			HLog.el("result buffer length is 0, cmd:" + cmd);
			return childFiles;
		}
		
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result.toString().getBytes());
		BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream));
		String lineString = "";
		
		while (lineString != null) {
			try {
				lineString = reader.readLine();
			} catch (IOException e) {
				HLog.el(e);
				return childFiles;
			}
			
			if (lineString == null) {
				return childFiles;
			}
			
			if (lineString.indexOf("Permission denied") >= 0) {
				continue;
			}
			
			if (lineString.indexOf("total") >= 0) {
				continue;
			}
			
			break;
		}
		
		HLog.il("start to parse every file ");
		
		while (lineString != null) {
			
			// parse one line
			FileAttributes fileAttr = parseFileAttributesFromString(lineString);
			if (fileAttr != null) {
				fileAttr.setFullPath(FileAdapter.pathCat(path, fileAttr.getName()));
				childFiles.add(fileAttr);
			}
			
			try {
				lineString = reader.readLine();
			} catch (IOException e) {
				HLog.el(e);
				return childFiles;
			}
		}
		
		HLog.il("child files count : " + childFiles.size());
//		if (childFiles.size() == 1) {
//			if (childFiles.get(0).getType() == FileAttributes.FILE_TYPE_LINK) {
//				return listAllFiles(childFiles.get(0).getLinkPath());
//			}
//		}
		
		return childFiles;
	}

	private FileAttributes parseFileAttributesFromString(String attr) {
		
		String fileAttrStrings[] = attr.split(" ");
		ArrayList<String> fileAttrArrayList = new ArrayList<>();
		for (int i = 0; i < fileAttrStrings.length; i++) {
			if (fileAttrStrings[i].trim().length() <= 0) {
				continue;
			}
			
			fileAttrArrayList.add(fileAttrStrings[i]);
		}
		
		FileAttributes fileAttributes = new FileAttributes();
		fileAttributes.setName(fileAttrArrayList.get(7));
		fileAttributes.setLastAccessTime(fileAttrArrayList.get(5) + " " + fileAttrArrayList.get(6));
		fileAttributes.setSize(Long.parseLong(fileAttrArrayList.get(4)));
		fileAttributes.setGroup(fileAttrArrayList.get(3));
		fileAttributes.setUser(fileAttrArrayList.get(2));
		fileAttributes.setPermission(fileAttrArrayList.get(0));
		
		if (fileAttributes.getType() == FileAttributes.FILE_TYPE_LINK) {
			fileAttributes.setLinkPath(fileAttrArrayList.get(9));
		}
		
		return fileAttributes;
	}
}
