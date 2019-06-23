package cn.hisdar.alt.view.log;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import cn.hisdar.alt.Global;
import cn.hisdar.alt.view.SettingsDialog;
import cn.hisdar.lib.configuration.ConfigItem;
import cn.hisdar.lib.configuration.HConfig;

public class LogViewPopupMenu implements ActionListener {
	
	private JPopupMenu           logAreaPopupMenu;
	private JMenu                addMarkMenu;
	private JMenu                delMarkMenu;
	private JMenuItem            setAutoMarkItem;
	private JMenuItem            editMarkMenuItem;
	private JMenuItem            pauseItem;
	private JMenuItem            startItem;
	private JMenuItem            copyItem;
	private JMenuItem            saveItem;
	private JMenuItem            clearItem;
	private JMenuItem            enableAutoMarkItem;
	private JMenuItem            disableAutoMarkItem;
	private JMenuItem            enableAutoScrollItem;
	private JMenuItem            disableAutoScrollItem;
	private JMenuItem            disableAtuoScrollWhenSelecteTextItem;
	
	private ArrayList<LogViewControlListener> logViewControlListeners;
	
	private boolean isDisableAutoWhenSelecteText = false;
	
	public LogViewPopupMenu() {
		logViewControlListeners = new ArrayList<>();
		initPopupMenu();
	}
	
	private void initPopupMenu() {
		logAreaPopupMenu = new JPopupMenu();
		addMarkMenu      = new JMenu("��ӱ��");
		delMarkMenu      = new JMenu("ɾ�����");
		pauseItem        = new JMenuItem("��ͣ");
		startItem        = new JMenuItem("��ʼ");
		copyItem         = new JMenuItem("����");
		saveItem         = new JMenuItem("����");
		clearItem        = new JMenuItem("����");
		enableAutoMarkItem  = new JMenuItem("�������");
		disableAutoMarkItem = new JMenuItem("�رձ��");
		setAutoMarkItem     = new JMenuItem("�Զ��������");
		editMarkMenuItem    = new JMenuItem("�༭��ǲ˵���");
		
		enableAutoScrollItem = new JMenuItem("������ֱ�������Զ�����");
		disableAutoScrollItem = new JMenuItem("�رմ�ֱ�������Զ�����");
		disableAtuoScrollWhenSelecteTextItem = new JMenuItem("ѡ�����ݺ�ֹͣ�Զ�����");
		
		logAreaPopupMenu.add(copyItem);
		logAreaPopupMenu.add(saveItem);
		logAreaPopupMenu.add(startItem);
		logAreaPopupMenu.add(pauseItem);
		logAreaPopupMenu.add(clearItem);
		
		logAreaPopupMenu.addSeparator();
		logAreaPopupMenu.add(addMarkMenu);
		logAreaPopupMenu.add(delMarkMenu);
		logAreaPopupMenu.add(enableAutoMarkItem);
		logAreaPopupMenu.add(disableAutoMarkItem);
		logAreaPopupMenu.add(setAutoMarkItem);
		logAreaPopupMenu.add(editMarkMenuItem);
		
		logAreaPopupMenu.addSeparator();
		logAreaPopupMenu.add(enableAutoScrollItem);
		logAreaPopupMenu.add(disableAutoScrollItem);
		logAreaPopupMenu.add(disableAtuoScrollWhenSelecteTextItem);
		
		loadMarkConfig();
		
		enableAutoScrollItem.setEnabled(false);
		disableAtuoScrollWhenSelecteTextItem.setSelected(isDisableAutoWhenSelecteText);
		
		editMarkMenuItem.addActionListener(this);
		enableAutoScrollItem.addActionListener(this);
		disableAutoScrollItem.addActionListener(this);
		disableAtuoScrollWhenSelecteTextItem.addActionListener(this);
	}
	
	private void loadMarkConfig() {
		
		HConfig markConfig = null;
		try {
			markConfig = HConfig.getInstance("./config/mark/user-def.xml");
		}catch (Exception e) {
			return;
		}
		
		if (markConfig == null) {
			return;
		}
		
		ArrayList<ConfigItem> markConfigs = markConfig.getConfigItemList();
		if (markConfigs == null || markConfigs.size() <= 0) {
			return;
		}
		
		for (int i = 0; i < markConfigs.size(); i++) {
			ConfigItem configItem = markConfigs.get(i);
			MarkAttribute attr = MarkAttribute.parseFromString(configItem.getValue());
			JMenuItem item = new JMenuItem(attr.getName());
			item.setBackground(attr.getBackground());
			item.setForeground(attr.getForeground());
			
			addMarkMenu.add(item);
			
			item = new JMenuItem(attr.getName());
			item.setBackground(attr.getBackground());
			item.setForeground(attr.getForeground());
			delMarkMenu.add(item);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enableAutoScrollItem) {
			enableAutoScrollItem.setEnabled(false);
			disableAutoScrollItem.setEnabled(true);
			
			disableAtuoScrollWhenSelecteTextItem.setEnabled(isDisableAutoWhenSelecteText);
			LogViewControlEvent event = new LogViewControlEvent(LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_ENABLE_AUTO_SCROLL);
			notifyControlEvent(event);
		} else if (e.getSource() == disableAutoScrollItem) {
			disableAutoScrollItem.setEnabled(false);
			enableAutoScrollItem.setEnabled(true);
			disableAtuoScrollWhenSelecteTextItem.setEnabled(false);
			LogViewControlEvent event = new LogViewControlEvent(LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_DISABLE_AUTO_SCROLL);
			notifyControlEvent(event);
		} else if (e.getSource() == disableAtuoScrollWhenSelecteTextItem) {
			isDisableAutoWhenSelecteText = disableAtuoScrollWhenSelecteTextItem.isSelected();
			if (disableAtuoScrollWhenSelecteTextItem.isSelected()) {
				disableAtuoScrollWhenSelecteTextItem.setSelected(false);
			} else {
				disableAtuoScrollWhenSelecteTextItem.setSelected(true);
			}
		} else if (e.getSource() == editMarkMenuItem) {
			Global.getSettingsDialog().show(SettingsDialog.SETTINGS_MARK);
		} else if (e.getSource() == clearItem) {
			LogViewControlEvent event = new LogViewControlEvent(LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_CLEAR);
			notifyControlEvent(event);
		} else if (e.getSource() == pauseItem) {
			LogViewControlEvent event = new LogViewControlEvent(LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_PAUSE);
			notifyControlEvent(event);
		} else if (e.getSource() == startItem) {
			LogViewControlEvent event = new LogViewControlEvent(LogViewControlEvent.LOG_VIEW_CONTROL_EVENT_START);
			notifyControlEvent(event);
		}
	}

	public JPopupMenu getLogAreaPopupMenu() {
		return logAreaPopupMenu;
	}

	public void setLogAreaPopupMenu(JPopupMenu logAreaPopupMenu) {
		this.logAreaPopupMenu = logAreaPopupMenu;
	}
	
	public void addControlListener(LogViewControlListener l) {
		
		for (int i = 0; i < logViewControlListeners.size(); i++) {
			if (logViewControlListeners.get(i) == l) {
				return;
			}
		}
		
		logViewControlListeners.add(l);
	}
	
	public void removeControlListener(LogViewControlListener l) {
		
		for (int i = 0; i < logViewControlListeners.size(); i++) {
			if (logViewControlListeners.get(i) == l) {
				logViewControlListeners.remove(i);
				return;
			}
		}
	}
	
	private void notifyControlEvent(LogViewControlEvent event) {
		for (int i = 0; i < logViewControlListeners.size(); i++) {
			logViewControlListeners.get(i).logViewControlEvent(event);
		}
	}
}
