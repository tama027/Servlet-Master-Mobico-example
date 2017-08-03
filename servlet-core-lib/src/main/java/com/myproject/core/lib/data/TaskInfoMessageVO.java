package com.myproject.core.lib.data;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TaskInfoMessageVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String messageRc;
	private TaskInfoVO otherMessage;
	private int rc;
	
	public String getMessageRc() {
		return messageRc;
	}
	public void setMessageRc(String messageRc) {
		this.messageRc = messageRc;
	}

	public int getRc() {
		return rc;
	}
	public void setRc(int rc) {
		this.rc = rc;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public TaskInfoVO getOtherMessage() {
		return otherMessage;
	}
	public void setOtherMessage(TaskInfoVO otherMessage) {
		this.otherMessage = otherMessage;
	}


}
