package cn.hisdar.alt.view.log;

public class LogViewControlEvent {
	
	public static final int LOG_VIEW_CONTROL_EVENT_PAUSE = 0x8001;
	public static final int LOG_VIEW_CONTROL_EVENT_START = 0x8002;
	public static final int LOG_VIEW_CONTROL_EVENT_CLEAR = 0x8003;
	public static final int LOG_VIEW_CONTROL_EVENT_ENABLE_AUTO_SCROLL  = 0x8004;
	public static final int LOG_VIEW_CONTROL_EVENT_DISABLE_AUTO_SCROLL = 0x8005;
	
	public int eventCode;
	public int eventValue;
	
	public LogViewControlEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public LogViewControlEvent(int eventCode) {
		this.eventCode = eventCode;
	}
	
	public LogViewControlEvent(int eventCode, int eventValue) {
		this.eventCode = eventCode;
		this.eventValue = eventValue;
	}

}
