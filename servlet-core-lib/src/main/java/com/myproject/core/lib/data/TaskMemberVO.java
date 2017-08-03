package com.myproject.core.lib.data;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TaskMemberVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String bankName;
	private String taskName;
	private String strStatus;
	private ArrayList<ListMemberVO> listMemberVO;
	
	
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


	public String getStrStatus() {
		return strStatus;
	}


	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}


	public ArrayList<ListMemberVO> getListMemberVO() {
		return listMemberVO;
	}


	public void setListMemberVO(ArrayList<ListMemberVO> listMemberVO) {
		this.listMemberVO = listMemberVO;
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
