package com.myproject.core.lib.data;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

public class TaskInfoVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private String bankLogo;
	private String bankName;
	private String appName;
	private String taskName;
	private String taskDesc;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	private String strStatus;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getBankLogo() {
		return bankLogo;
	}

	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}


}
