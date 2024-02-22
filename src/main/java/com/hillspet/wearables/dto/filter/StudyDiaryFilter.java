package com.hillspet.wearables.dto.filter;
/**
 * @author rajesh
 *
 */
public class StudyDiaryFilter extends BaseFilter {
	
	private int taskId;
	private int eventId;
	
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	
	
}
