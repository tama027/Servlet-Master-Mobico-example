package com.myproject.parking.lib.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.core.lib.data.LoginData;
import com.myproject.core.lib.entity.UserData;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CipherUtil;
import com.myproject.parking.lib.utils.CommonUtil;

@Service
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private UserDataMapper userDataMapper;
	
	@Autowired
	private AppsTimeService timeService;
	
	/*mobicoLogic
	 * 
	 * */
	
	public LoginData loginMob(LoginData loginData) throws ParkingEngineException {
		LOG.debug("login with param : " + " loginData: " + loginData );	
		
		try{
		
		UserData user = userDataMapper.findUserDataByUserId(loginData.getUserId());
		
		if(user == null){
			throw new ParkingEngineException(ParkingEngineException.ENGINE_USER_NOT_FOUND);
		}
		
		String passwordDB = user.getPassword();
		String passwordInput = CipherUtil.passwordDigest(loginData.getUserId(), loginData.getPassword());
		
		if(!passwordDB.equals(passwordInput)){
			throw new ParkingEngineException(ParkingEngineException.ENGINE_WRONG_EMAIL_OR_PASSWORD);
		}
		// generate session key
		String sessionKey = user.getUserId() + CommonUtil.generateAlphaNumeric(30);
		user.setSessionKey(sessionKey);
		user.setRegId(loginData.getRegId());
		user.setUpdatedOn(timeService.getCurrentTime());
		/*userDataMapper.updateRegId(user.getUserId(), user.getRegId());*/
		userDataMapper.updateLoginSessionKeyMob(user.getUserId(), user.getSessionKey(), user.getRegId(), user.getUpdatedOn());
		loginData.setSessionKey(sessionKey);
		loginData.setPassword("*******");
		LOG.info("login done with param : " + " loginData: " + loginData);
		
	} catch (ParkingEngineException me) {
		throw me;	
	} catch (Exception e) {
		LOG.error("ERROR: " + e, e);
		throw new ParkingEngineException(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
	}
		return loginData;
		
	}
	
}
