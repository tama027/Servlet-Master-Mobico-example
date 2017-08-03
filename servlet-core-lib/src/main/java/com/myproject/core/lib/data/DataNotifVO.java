package com.myproject.core.lib.data;

import java.io.Serializable;

public class DataNotifVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private  NotifVO dataNotifVO;


	public NotifVO getDataNotifVO() {
		return dataNotifVO;
	}


	public void setDataNotifVO(NotifVO dataNotifVO) {
		this.dataNotifVO = dataNotifVO;
	}


}
