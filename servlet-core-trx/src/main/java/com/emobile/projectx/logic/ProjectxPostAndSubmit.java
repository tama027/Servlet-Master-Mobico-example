package com.emobile.projectx.logic;

import com.myproject.parking.trx.logic.BaseQueryLogic;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.core.lib.data.LoginData;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.utils.CipherUtil;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class ProjectxPostAndSubmit implements BaseQueryLogic {
	private static final Logger LOG = LoggerFactory.getLogger(ProjectxPostAndSubmit.class);
	 
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response, String data, ObjectMapper objMapper,
			String pathInfo) {
					
		LOG.debug("Generate Password :"+pathInfo);		
		String passwordInput = "";
		try {				
			
			LoginData loginData = objMapper.readValue(data, LoginData.class);
			passwordInput = CipherUtil.passwordDigest(loginData.getUserId(), loginData.getPassword());
			
		} catch (Exception e) {
			LOG.error("Unexpected exception when processing " + pathInfo, e);
			passwordInput = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), objMapper);
		}
		return passwordInput;
		
	}
	
}
