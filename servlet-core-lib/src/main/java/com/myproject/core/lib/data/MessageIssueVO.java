package com.myproject.core.lib.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.myproject.parking.lib.utils.CommonUtil;

public class MessageIssueVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String issueNumber;
	private String userId;
	private String messageDesc;
	private Date msgCreatedOn;
	private Date updatedOn;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMessageDesc() {
		return messageDesc;
	}
	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public Date getMsgCreatedOn() {
		return msgCreatedOn;
	}
	public void setMsgCreatedOn(Date msgCreatedOn) {
		this.msgCreatedOn = msgCreatedOn;
	}
}
