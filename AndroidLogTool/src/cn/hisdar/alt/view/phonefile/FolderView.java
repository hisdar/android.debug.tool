package cn.hisdar.alt.view.phonefile;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.nio.file.FileSystem;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.hisdar.lib.log.HLog;
import cn.hisdar.lib.ui.HLinearPanel;

public class FolderView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FileViewEventHandler fileViewEventHandler;
	private ArrayList<FileView> fileViews;
	private HLinearPanel folderMainPanel;
	private HLinearPanel folderTitlePanel;
	
	private JLabel iconLabel;
	private JLabel fileNameLabel;
	private JLabel lastAccessTimeLabel;
	private JLabel fileTypeLabel;
	private JLabel fileSizeLabel;
	
	private FileSystemInterface fileSystem;
	
	
	public FolderView(FileSystemInterface fileSytem) {
		this.fileSystem = fileSytem;
		fileViews = new ArrayList<>();
		fileViewEventHandler = new FileViewEventHandler();
		folderMainPanel = new HLinearPanel(HLinearPanel.VERTICAL);
		folderTitlePanel = new HLinearPanel(HLinearPanel.HORIZONTAL);
		initFolderTitlePanel();
		
		setLayout(new BorderLayout());
		add(folderMainPanel, BorderLayout.CENTER);
		add(folderTitlePanel, BorderLayout.NORTH);
	}
	
	private void initFolderTitlePanel() {
		iconLabel = new JLabel("");
		fileNameLabel = new JLabel("名称");
		lastAccessTimeLabel = new JLabel("修改日期");
		fileTypeLabel = new JLabel("类型");
		fileSizeLabel = new JLabel("大小");
		
		folderTitlePanel.add(iconLabel);
		folderTitlePanel.add(fileNameLabel);
		folderTitlePanel.add(lastAccessTimeLabel);
		folderTitlePanel.add(fileTypeLabel);
		folderTitlePanel.add(fileSizeLabel);
		
		folderTitlePanel.setDividingLineSize(FileView.dividingLineSize);
	}
	
	public void clear() {
		folderMainPanel.removeAllChilds();
		for (int i = fileViews.size(); i > 0; i--) {
			fileViews.remove(i - 1);
		}
		
		repaint();
	}
	
	private boolean addAttributesToFolderView(FileAttributes fileAttr) {
		
		if (fileAttr.getName().equals(".")) {
			return false;
		}
		
		if (fileAttr.getName().equals("..")) {
			return false;
		} 
		
		FileView fileView = new FileView(fileAttr);
		addFileView(fileView);
		
		return true;
	}
	
	public void addFiles(ArrayList<FileAttributes> files) {
		
		if (files != null) {
			for (int i = 0; i < files.size(); i++) {
				addAttributesToFolderView(files.get(i));
			}
		}
	}
	
	public void addFileView(FileView fileView) {
		fileViews.add(fileView);
		folderMainPanel.add(fileView);
		
		fileView.addFileViewListener(fileViewEventHandler);
		updateFileViewSize();
	}
	
	public void update(FileAttributes childFiles[]) {
		
		folderMainPanel.removeAll();
		for (int i = fileViews.size(); i > 0; i--) {
			fileViews.remove(i - 1);
		}
		
		for (int i = 0; i < childFiles.length; i++) {
			FileView fileView = new FileView(childFiles[i]);
			folderMainPanel.add(fileView);
		}
	}
	
	private int getMaxLabelSize(JLabel label, int currentMaxSize) {
		int currentWidth = FileView.getLabelStringWidth(label);
		if (currentWidth > currentMaxSize) {
			return currentWidth;
		}
		
		return currentMaxSize;
	}
	
	private Dimension getLabelSizeByWidth(JLabel label, int width) {
		return new Dimension(width + FileView.dividingLineSize, (int)label.getSize().getHeight());
	}
	
	private void updateFileViewSize() {
		// file max file name item
		int maxfileNameStringWidth = FileView.getLabelStringWidth(fileNameLabel);
		int maxLastAccessTimeWidth = FileView.getLabelStringWidth(lastAccessTimeLabel);
		int maxFileTypeStringWidth = FileView.getLabelStringWidth(fileTypeLabel);
		int maxFileSizeStringWidth = FileView.getLabelStringWidth(fileSizeLabel);
		for (int i = 0; i < fileViews.size(); i++) {
			FileView fileView = fileViews.get(i);
			
			maxfileNameStringWidth = getMaxLabelSize(fileView.getFileNameLabel(), maxfileNameStringWidth);
			maxLastAccessTimeWidth = getMaxLabelSize(fileView.getFileLastModifyTimeLabel(), maxLastAccessTimeWidth);
			maxFileTypeStringWidth = getMaxLabelSize(fileView.getFileTypeLabel(), maxFileTypeStringWidth);
			maxFileSizeStringWidth = getMaxLabelSize(fileView.getFileSizeLabel(), maxFileSizeStringWidth);
		}
		
		// set size to every fileView
		Dimension newFileNameSize = getLabelSizeByWidth(fileNameLabel, maxfileNameStringWidth);
		Dimension newLastAccessTimeSize = getLabelSizeByWidth(lastAccessTimeLabel, maxLastAccessTimeWidth);
		Dimension newFileTypeSize = getLabelSizeByWidth(fileTypeLabel, maxFileTypeStringWidth);
		Dimension newFileSizeSize = getLabelSizeByWidth(fileSizeLabel, maxFileSizeStringWidth);
		
		iconLabel.setPreferredSize(new Dimension(16, 16));
		fileNameLabel.setPreferredSize(newFileNameSize);
		lastAccessTimeLabel.setPreferredSize(newLastAccessTimeSize);
		fileTypeLabel.setPreferredSize(newFileTypeSize);
		fileSizeLabel.setPreferredSize(newFileSizeSize);
		for (int i = 0; i < fileViews.size(); i++) {
			fileViews.get(i).getFileNameLabel().setPreferredSize(newFileNameSize);
			fileViews.get(i).getFileLastModifyTimeLabel().setPreferredSize(newLastAccessTimeSize);
			fileViews.get(i).getFileTypeLabel().setPreferredSize(newFileTypeSize);
			fileViews.get(i).getFileSizeLabel().setPreferredSize(newFileSizeSize);
			fileViews.get(i).invalidate();
		}
		
		folderMainPanel.invalidate();
		folderTitlePanel.invalidate();
	}
	
	public void setFileSystem(FileSystemInterface fileSystem) {
		this.fileSystem = fileSystem;
	}
	
	private class FileViewEventHandler implements FileViewListener {

		@Override
		public void fileViewEvent(FileView fileView, MouseEvent e, int event) {
			switch (event) {
			case FileView.FILE_VIEW_EVENT_MOUSE_IN:
				
				for (int i = 0; i < fileViews.size(); i++) {
					FileView currentFileView = fileViews.get(i);
					if (currentFileView.isMouseIn() && currentFileView != fileView) {
						currentFileView.setMouseIn(false);
					}
				}
				
				if (!fileView.isMouseIn()) {
					fileView.setMouseIn(true);
				}
				
				repaint();
				break;
				
			case FileView.FILE_VIEW_EVENT_MOUSE_PRESSED:
				break;
				
			case FileView.FILE_VIEW_EVENT_MOUSE_RELEASE:
				handleMouseReleasedEvent(fileView, e);
				break;
				
			case FileView.FILE_VIEW_EVENT_MOUSE_CLICKED:
				handleMouseClickedEvent(fileView, e);
				break;

			default:
				break;
			}
		}
		
		private void handleMouseReleasedEvent(FileView fileView, MouseEvent e) {
			
		}
		
		private void handleMouseClickedEvent(FileView fileView, MouseEvent e) {
			if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
				if (fileSystem == null) {
					return;
				}
				
				String selectedPath = fileView.getFileAttributes().getFullPath();
				ArrayList<FileAttributes> childFiles = fileSystem.listAllFiles(selectedPath);
				if (childFiles.size() <= 0) {
					return;
				}

				// remove all files
				clear();
				addFiles(childFiles);
			}
		}
		
	}
	
	
}