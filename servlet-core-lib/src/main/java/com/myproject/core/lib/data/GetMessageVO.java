package com.myproject.core.lib.data;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

public class GetMessageVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String messageRc;
	private SubmitDataVO otherMessage;
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
	public SubmitDataVO getOtherMessage() {
		return otherMessage;
	}
	public void setOtherMessage(SubmitDataVO otherMessage) {
		this.otherMessage = otherMessage;
	}

}
