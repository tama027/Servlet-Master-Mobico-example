package com.myproject.core.lib.data;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.util.Date;

public class SubmitDataVO extends LoginData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String issueNumber;
	private String bankId;
	private String appId;
	private String bankName;
	private String taskName;
	private String taskDesc;
	private String appName;
	private String imgPath;
	private int status;
	private String createdBy; //userId
	private Date createdOn;
	private Date updatedOn;
	private List<MessageIssueVO> listmessageIssueVO;

	
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getTaskDesc() {
		return taskDesc;
	}
	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<MessageIssueVO> getListmessageIssueVO() {
		if (listmessageIssueVO == null)
			listmessageIssueVO = new ArrayList<MessageIssueVO>();
		return listmessageIssueVO;
	}
	public void setListmessageIssueVO(List<MessageIssueVO> listmessageIssueVO) {
		this.listmessageIssueVO = listmessageIssueVO;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
}
