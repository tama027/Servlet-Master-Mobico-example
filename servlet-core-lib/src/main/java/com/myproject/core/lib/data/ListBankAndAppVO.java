package com.myproject.core.lib.data;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import java.sql.Date;

public class ListBankAndAppVO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	private List<AppMasterVO> listAppVO;
	private List<BankMasterVO> listBankVO;
	
	public List<BankMasterVO> getListBankVO() {
		return listBankVO;
	}
	public void setListBankVO(List<BankMasterVO> listBankVO) {
		this.listBankVO = listBankVO;
	}
	public List<AppMasterVO> getListAppVO() {
		return listAppVO;
	}
	public void setListAppVO(List<AppMasterVO> listAppVO) {
		this.listAppVO = listAppVO;
	}
	

	
}
