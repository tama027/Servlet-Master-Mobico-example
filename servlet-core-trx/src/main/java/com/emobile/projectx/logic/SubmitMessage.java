package com.emobile.projectx.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.core.lib.data.MessageIssueVO;
import com.myproject.core.lib.data.NotifVO;
import com.myproject.core.lib.data.SubmitDataVO;
import com.myproject.core.lib.entity.UserData;
import com.myproject.parking.lib.service.GetMessageService;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.service.SubmitDataService;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class SubmitMessage implements BaseQueryLogic {
	private static final Logger LOG = LoggerFactory.getLogger(LoginUser.class);
	
	@Autowired
	private GetMessageService messageService;
	
	
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response, String data, ObjectMapper objMapper,
			String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {				
			//get from header
		/*	String ss = request.getHeader("sessionKey");
			String userId = request.getHeader("userId");*/
			 
			MessageIssueVO message = objMapper.readValue(data, MessageIssueVO.class);
			String x = messageService.saveMessage(message);	
			//String result = objMapper.writeValueAsString(x);
			result = MessageUtils.handleSuccess(x, objMapper);
			
		} catch (ParkingEngineException e) {
			LOG.error("LoginEngineException when processing " + pathInfo, e);
			result = MessageUtils.handleException(e, "", objMapper);
		} catch (Exception e) {
			LOG.error("Unexpected exception when processing " + pathInfo, e);
			result = MessageUtils.handleException(e, "Unexpected exception when processing "+ e.getMessage(), objMapper);
		}
		return result;	
		
	}

}
