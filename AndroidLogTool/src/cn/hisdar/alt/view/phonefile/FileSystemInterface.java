package cn.hisdar.alt.view.phonefile;

import java.util.ArrayList;

public interface FileSystemInterface {
	public int open(String path);
	public ArrayList<FileAttributes> listAllFiles(String path);
}
