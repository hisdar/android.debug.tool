package cn.hisdar.alt.view.phonefile;

public class FileAttributes {
	
	public static final int FILE_TYPE_FOLDER = 0x8001;
	public static final int FILE_TYPE_FILE   = 0x8002;
	public static final int FILE_TYPE_LINK   = 0x8003;
	public static final int FILE_TYPE_UNKNOW = 0x8FFF;
	
	private String fullPath;
	private String name;
	private String permission;
	private String createTime;
	private String lastAccessTime;
	private String user;
	private String group;
	private String linkPath;
	private long   size;
	private int    type;
	
	public FileAttributes() {
		type = FILE_TYPE_UNKNOW;
	}
	
	private void parseTileType() {
		if (permission.charAt(0) == 'd') {
			type = FILE_TYPE_FOLDER;
		} else if (permission.charAt(0) == 'l') {
			type = FILE_TYPE_LINK;
		} else if (permission.charAt(0) == '-') {
			type = FILE_TYPE_FILE;
		} else {
			type = FILE_TYPE_UNKNOW;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public String getFullPath() {
		return fullPath;
	}
	
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	
	public void setLinkPath(String path) {
		linkPath = path;
	}
	
	public String getLinkPath() {
		return linkPath;
	}

	public int getType() {
		if (type == FILE_TYPE_UNKNOW) {
			parseTileType();
		}

		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}