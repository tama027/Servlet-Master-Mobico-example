package com.myproject.parking.lib.service;

import java.awt.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.core.lib.data.AppMasterVO;
import com.myproject.core.lib.data.BankMasterVO;
import com.myproject.core.lib.data.ListBankAndAppVO;
import com.myproject.core.lib.data.MessageIssueVO;
import com.myproject.core.lib.data.NotifVO;
import com.myproject.core.lib.data.SubmitDataVO;
import com.myproject.core.lib.data.UserRefVO;
import com.myproject.core.lib.entity.UserData;
import com.myproject.parking.lib.mapper.GetMessageMapper;
import com.myproject.parking.lib.mapper.SubmitDataMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CommonUtil;

@Service
public class GetMessageService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private GetMessageMapper getMessageMapper;

	@Autowired
	private SubmitDataMapper submitDataMapper;
	
	@Autowired
	private SubmitDataService submitDataService;
	
	@Autowired
	private UserDataMapper userDataMapper;


	private ArrayList<UserRefVO> listUserRef;
	private ArrayList<UserData> listUserData;
	private ArrayList<SubmitDataVO> listSubmitResult;
	

	public SubmitDataVO findIssueByParam(SubmitDataVO submitData) throws Exception {
		LOG.debug("find issue by param: " + " param: " + submitData);
		String issueNumber = submitData.getIssueNumber();
		SubmitDataVO submitDataResult = getMessageMapper.findIssueDataAndMessageByIssueNumber(issueNumber);
		
		
		return submitDataResult;

	}
	
	public ArrayList<SubmitDataVO> findListIssueByParam(SubmitDataVO submitData) throws ParkingEngineException {
		ArrayList<SubmitDataVO> listSubmitDataVO = new ArrayList<SubmitDataVO>();
		
		try{
		LOG.debug("find issue by param: " + " param: " + submitData);
		String issueNumber = submitData.getIssueNumber();
		//validation
		UserData user = userDataMapper.findUserDataByUserId(submitData.getUserId());
		if(user == null){
			throw new ParkingEngineException(ParkingEngineException.USERID_INVALID_OR_NOTMATCH);
		}
	
		if (!user.getSessionKey().equals(submitData.getSessionKey())) {
			LOG.error("Wrong Session Key, Parameter userId : " + submitData.getCreatedBy());
			throw new ParkingEngineException(ParkingEngineException.SESSIONKEY_INVALID_OR_NULL);
		}
			
		listUserRef = submitDataMapper.findUsrRefByUserId(submitData.getUserId());
		listSubmitResult = new ArrayList<SubmitDataVO>();
		for(int i = 0;i<listUserRef.size();i++){
			listSubmitDataVO = submitDataMapper.findIssueByAppId(listUserRef.get(i).getAppId());
			for(SubmitDataVO submitDataVO : listSubmitDataVO){
					SubmitDataVO submitDataResult = getMessageMapper.findIssueDataAndMessageByIssueNumber(submitDataVO.getIssueNumber());
					listSubmitResult.add(submitDataResult);
			}
		}
		
		} catch (ParkingEngineException me) {
			throw me;	
		} catch (Exception e) {
			LOG.error("ERROR: " + e, e);
			throw new ParkingEngineException(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
		}
		
		return listSubmitResult;

	}

	public String saveMessage (MessageIssueVO messageVO) throws ParkingEngineException{
		LOG.debug("save message by param: " + messageVO);
		try{
			
			if(messageVO == null){
				throw new ParkingEngineException(ParkingEngineException.PARAMETER_NOT_COMPLETE);
			}
			getMessageMapper.submitMessage(messageVO);
			
			//notif
			NotifVO notifVO = new NotifVO();
			notifVO.setIssueNumber(messageVO.getIssueNumber());
			notifVO.setSubject("New Message");
			notifVO.setMessage("You Have New Message. IssueNumber : "+ messageVO.getIssueNumber());
			
			SubmitDataVO submitresult = submitDataMapper.findIssueByIssueNumber(messageVO.getIssueNumber());
			listUserRef = submitDataMapper.findUsrRefByAppId(submitresult.getAppId());
			for (int i = 0; i < listUserRef.size(); i++) {
				UserData userData = new UserData();
				userData = userDataMapper.findUserDataByUserId(listUserRef.get(i).getUserId());
				/*if(!userData.getUserId().equals(messageVO.getUserId())){	*/
					if (userData.getRegId() != null) {
						SubmitDataService.pushFCMNotificationGeneral(userData.getRegId(), notifVO);
					}
				}

			//}
			
		} catch (ParkingEngineException me) {
			throw me;	
		} catch (Exception e) {
			LOG.error("ERROR: " + e, e);
			throw new ParkingEngineException(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
		}
		
		return"Insert Data Message Success";
	}

	public ListBankAndAppVO findList(SubmitDataVO submitData) throws ParkingEngineException {
		ListBankAndAppVO listBankAndAppId = new ListBankAndAppVO();
		try{
			
			UserData user = userDataMapper.findUserDataByUserId(submitData.getUserId());
			
			if(user == null){
				throw new ParkingEngineException(ParkingEngineException.USERID_INVALID_OR_NOTMATCH);
			}
		
			if (!user.getSessionKey().equals(submitData.getSessionKey())) {
				LOG.error("Wrong Session Key, Parameter userId : " + submitData.getCreatedBy());
				throw new ParkingEngineException(ParkingEngineException.SESSIONKEY_INVALID_OR_NULL);
			}
			
			ArrayList<BankMasterVO> bankMaster = getMessageMapper.findAllBankList();
			ArrayList<AppMasterVO> appMaster = getMessageMapper.findAllAppList();
			listBankAndAppId.setListAppVO(appMaster);
			listBankAndAppId.setListBankVO(bankMaster);
		} catch (ParkingEngineException me) {
			throw me;	
		} catch (Exception e) {
			LOG.error("ERROR: " + e, e);
			throw new ParkingEngineException(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
		}
	
		return listBankAndAppId;
		
	}
	
	
	
	
	}
