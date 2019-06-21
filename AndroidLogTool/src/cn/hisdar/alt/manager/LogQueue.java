package cn.hisdar.alt.manager;

public class LogQueue {

	private int logQueueSize = 10000;
	private String[] logQueue;
	
	private int queueHead;
	private int queueTail;
	private int queueLength;
	private int queuePointer;
	
	public LogQueue() {
		queueHead = 0;
		queueTail = 0;
		queuePointer = 0;
		logQueue = new String[logQueueSize];
	}
	
	public int getQueueLength() {
		return queueLength;
	}
	
	public void push(String log) {
		logQueue[queueTail] = log;
		queueTail = (queueTail + 1) % logQueueSize;
		queueLength = queueLength + 1;
		if (queueLength >= logQueueSize) {
			queueLength = logQueueSize;
			queueHead = (queueHead + 1) % logQueueSize;
		}
		
		synchronized (LogQueue.class) {
			if (queueTail == queuePointer) {
				queuePointer = (queuePointer + 1) % logQueueSize;
			}
		}
	}
	
	public String getLog(int index) {
		
		if (index >= queueLength) {
			return null;
		}
		
		int localIndex = (queueHead + index) % logQueueSize;
		return logQueue[localIndex];
	}
	
	public boolean haveNextLog() {

		synchronized (LogQueue.class) {
			
			if (queuePointer == queueTail) {
				return false;
			}
			
			if (((queuePointer + 1) % logQueueSize) != queueTail) {
				return true;
			}
			
			//if ((queuePointer + queueHead) % logQueueSize < queueLength) {
			//	return true;
			//}
		}
		
		return false;
	}
	
	public String getNextLog() {
		synchronized (LogQueue.class) {
			String logString = logQueue[queuePointer];
			queuePointer = (queuePointer + 1) % logQueueSize;
			return logString;
		}
	}
	
	public void resetLogPointer() {
		synchronized (LogQueue.class) {
			queuePointer = queueHead;
		}
	}
}
