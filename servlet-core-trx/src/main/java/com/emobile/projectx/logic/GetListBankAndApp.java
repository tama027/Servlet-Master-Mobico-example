package com.emobile.projectx.logic;

import java.io.Reader;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.myproject.core.lib.data.ListBankAndAppVO;
import com.myproject.core.lib.data.MessageVO;
import com.myproject.core.lib.data.SubmitDataVO;
import com.myproject.parking.lib.service.GetMessageService;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.service.SubmitDataService;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class GetListBankAndApp implements BaseQueryLogic {
	private static final Logger LOG = LoggerFactory.getLogger(LoginUser.class);
	
	@Autowired
	private GetMessageService messageService;
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response, String data, ObjectMapper objMapper,
			String pathInfo) {
		LOG.debug("Start process Query :"+pathInfo);		
		String result = "";
		try {				
			
			SubmitDataVO submitData = objMapper.readValue(data, SubmitDataVO.class);
			ListBankAndAppVO listBankAndAppVO = messageService.findList(submitData);
			result = MessageUtils.handleSuccessListBankAndApp(listBankAndAppVO, objMapper);
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
