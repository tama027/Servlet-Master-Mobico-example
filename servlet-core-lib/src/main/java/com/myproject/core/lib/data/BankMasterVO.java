package com.myproject.core.lib.data;

import java.io.Serializable;

public class BankMasterVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String bankName;
	private String bankDesc;
	private String bankLogo;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankDesc() {
		return bankDesc;
	}
	public void setBankDesc(String bankDesc) {
		this.bankDesc = bankDesc;
	}
	public String getBankLogo() {
		return bankLogo;
	}
	public void setBankLogo(String bankLogo) {
		this.bankLogo = bankLogo;
	}
	
}
