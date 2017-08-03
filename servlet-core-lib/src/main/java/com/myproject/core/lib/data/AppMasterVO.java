package com.myproject.core.lib.data;

import java.io.Serializable;

public class AppMasterVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appId;
	private String bankId;
	private String appName;
	private String appDetail;
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppDetail() {
		return appDetail;
	}
	public void setAppDetail(String appDetail) {
		this.appDetail = appDetail;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
}
