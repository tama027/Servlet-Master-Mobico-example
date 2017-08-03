package com.myproject.parking.lib.utils;

import java.io.IOException;

import java.util.Arrays;
import java.util.Properties;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.core.lib.data.GetMessageVO;
import com.myproject.core.lib.data.ListBankAndAppMessageVO;
import com.myproject.core.lib.data.ListBankAndAppVO;
import com.myproject.core.lib.data.MessageVO;
import com.myproject.core.lib.data.SubmitDataVO;
import com.myproject.core.lib.data.TaskInfoMessageVO;
import com.myproject.core.lib.data.TaskInfoVO;
import com.myproject.core.lib.data.TaskMemberMessageVO;
import com.myproject.core.lib.data.TaskMemberVO;
import com.myproject.parking.lib.service.ParkingEngineException;

public class MessageUtils {
	private static final Logger LOG = LoggerFactory.getLogger(MessageUtils.class);
	private static String generateMessage(MessageVO messageVO,ObjectMapper mapper){
		String result = "";
		try {
			//mapper.setSerializationInclusion(Include.NON_NULL);
			result = mapper.writeValueAsString(messageVO);
			/*for encyrpt response*/
			/*result = CipherUtil.encryptDESede(result, CipherUtil.KEY);*/
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String handleException(Exception e,String otherMessage,ObjectMapper mapper) {
		MessageVO messageVO = new MessageVO();
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			if (e instanceof ParkingEngineException) {
				ParkingEngineException jme = (ParkingEngineException) e;
				if(jme.hasInfo()){
					messageVO.setRc(jme.getErrorCode());
					messageVO.setMessageRc(props.getProperty("rc."+jme.getErrorCode())+ " " + Arrays.toString(jme.getInfo()));
					messageVO.setOtherMessage(otherMessage);
					LOG.warn("MessageVO : [{}]", messageVO);
				}else{
					messageVO.setRc(jme.getErrorCode());
					messageVO.setMessageRc(props.getProperty("rc."+jme.getErrorCode()));
					messageVO.setOtherMessage(otherMessage);
					LOG.warn("MessageVO : [{}]", messageVO);
				}
			} else {
				messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
				messageVO.setMessageRc(props.getProperty("rc."+ParkingEngineException.ENGINE_UNKNOWN_ERROR));
				messageVO.setOtherMessage(e.getMessage());
				LOG.warn("MessageVO : [{}]", messageVO);
			}
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
			messageVO.setOtherMessage(ew.getMessage());
		}
		return generateMessage(messageVO, mapper);
	}
	
	public static String handleExceptionOther(Exception e,String otherMessage,ObjectMapper mapper) {
		MessageVO messageVO = new MessageVO();
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			if (e instanceof ParkingEngineException) {
				ParkingEngineException jme = (ParkingEngineException) e;
				messageVO.setRc(jme.getErrorCode());
				messageVO.setMessageRc(props.getProperty("rc."+jme.getErrorCode()));
				messageVO.setOtherMessage(otherMessage);
				LOG.warn("MessageVO : [{}]", messageVO);
			} else {
				messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
				messageVO.setMessageRc(props.getProperty("rc."+ParkingEngineException.ENGINE_UNKNOWN_ERROR));
				messageVO.setOtherMessage(e.getMessage());
				LOG.warn("MessageVO : [{}]", messageVO);
			}
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
			messageVO.setOtherMessage(ew.getMessage());
		}
		return messageVO.getMessageRc();
	}
	//
	public static String handleSuccess(String otherMessage,ObjectMapper mapper) {
		MessageVO messageVO = new MessageVO();
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
				messageVO.setRc(0);
				messageVO.setMessageRc(props.getProperty("rc.0"));
				messageVO.setOtherMessage(otherMessage);
				LOG.warn("MessageVO : [{}]", messageVO);
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
			messageVO.setOtherMessage(ew.getMessage());
		}
		return generateMessage(messageVO, mapper);
	}
	//
	public static String handleSuccessGetMessage(SubmitDataVO dataVO,ObjectMapper mapper) throws JsonProcessingException {
		GetMessageVO getMessageVO = new GetMessageVO();
		String result = "";
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
				getMessageVO.setRc(0);
				getMessageVO.setMessageRc(props.getProperty("rc.0"));
				getMessageVO.setOtherMessage(dataVO);
				result = mapper.writeValueAsString(getMessageVO);
				LOG.warn("MessageVO : [{}]", getMessageVO);
		} catch (IOException ew) {
			getMessageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			getMessageVO.setMessageRc("IOException on handleException");
		}
		return result;
	}
	
	

	//
	public static String handleSuccessListBankAndApp(ListBankAndAppVO listBankAndAppVO, ObjectMapper objMapper) throws JsonProcessingException {
		ListBankAndAppMessageVO messageVO = new ListBankAndAppMessageVO();
		String result = "";
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			messageVO.setRc(0);
			messageVO.setMessageRc(props.getProperty("rc.0"));
			messageVO.setOtherMessage(listBankAndAppVO);
				result = objMapper.writeValueAsString(messageVO);
				LOG.warn("MessageVO : [{}]", messageVO);
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
		}
		return result;
	}
	
	//
	public static String handleSuccessTaskMember(TaskMemberVO taskMemberVO,ObjectMapper mapper) throws JsonProcessingException {
		TaskMemberMessageVO messageVO = new TaskMemberMessageVO();
		String result = "";
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			messageVO.setRc(0);
			messageVO.setMessageRc(props.getProperty("rc.0"));
			messageVO.setOtherMessage(taskMemberVO);
				result = mapper.writeValueAsString(messageVO);
				LOG.warn("MessageVO : [{}]",messageVO);
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
		}
		return result;
	}
	
	//
	public static String handleSuccessTaskInfo(TaskInfoVO taskInfoVO,ObjectMapper mapper) throws JsonProcessingException {
		TaskInfoMessageVO messageVO = new TaskInfoMessageVO();
		String result = ""; 
		try {			
			Resource resource = new ClassPathResource("parking.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			messageVO.setRc(0);
			messageVO.setMessageRc(props.getProperty("rc.0"));
			messageVO.setOtherMessage(taskInfoVO);
				result = mapper.writeValueAsString(messageVO);
				LOG.warn("MessageVO : [{}]",messageVO);
		} catch (IOException ew) {
			messageVO.setRc(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
			messageVO.setMessageRc("IOException on handleException");
		}
		return result;
	}
	
}
