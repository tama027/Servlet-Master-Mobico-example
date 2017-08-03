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
import com.myproject.core.lib.data.DataNotifVO;
import com.myproject.core.lib.data.NotifVO;
import com.myproject.core.lib.data.SubmitDataVO;
import com.myproject.core.lib.data.UserRefVO;
import com.myproject.core.lib.entity.UserData;
import com.myproject.parking.lib.mapper.SubmitDataMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;
import com.myproject.parking.lib.utils.CommonUtil;

@Service
public class SubmitDataService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

	private static final String AUTH_KEY_FCM = "AAAA7OP7Nhk:APA91bFQGDrqHTU2x8ioXusZgp_YSIOXjkzmWUcLavtDHv2-XeV-LsHCYjAICjLZAZfTfehyFh9nWUJdkEjUEobpInumXcbUTq_w8jut1O0iiJF3KpPASarA2h_HV5CA-pcBD4s30Lha";

	private static final String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	@Autowired
	private SubmitDataMapper submitDataMapper;

	@Autowired
	private UserDataMapper userDataMapper;

	private ArrayList<UserRefVO> listUserRef;
	private ArrayList<UserData> listUserData;

	public String save(SubmitDataVO submitData) throws Exception {
		String result = "";
		try {
			LOG.debug("Save Submit data : " + " submit data: " + submitData);

			if (submitData == null) {
				throw new ParkingEngineException(ParkingEngineException.PARAMETER_NOT_COMPLETE);
			}

			if (submitData.getAppId().equals("") || submitData.getAppId() == null) {
				throw new ParkingEngineException(ParkingEngineException.APP_ID_INVALID_OR_NULL);
			}

			if (submitData.getBankId().equals("")) {
				throw new ParkingEngineException(ParkingEngineException.APP_ID_INVALID_OR_NULL);
			}
			// session check
			UserData user = userDataMapper.findUserDataByUserId(submitData.getUserId());

			if (user == null) {
				throw new ParkingEngineException(ParkingEngineException.USERID_INVALID_OR_NOTMATCH);
			}

			if (!user.getSessionKey().equals(submitData.getSessionKey())) {
				LOG.error("Wrong Session Key, Parameter userId : " + submitData.getCreatedBy());
				throw new ParkingEngineException(ParkingEngineException.SESSIONKEY_INVALID_OR_NULL);
			}

			for (int i = 0; i < 1; i++) {
				String issueNum = "ISSUE" + CommonUtil.generateAlphaNumeric(5);
				SubmitDataVO submitDataf = submitDataMapper.findIssueByIssueNumber(issueNum);
				if (submitDataf != null) {
					i = 0;
				} else {
					submitData.setIssueNumber(issueNum);
				}
			}

			// INSERT TO DB
			submitDataMapper.insertToDb(submitData);
			//
			String appId = submitData.getAppId();
			AppMasterVO appMasterVO = submitDataMapper.findAppById(appId);

			NotifVO notifVO = new NotifVO();
			notifVO.setIssueNumber(submitData.getIssueNumber());
			notifVO.setAppName(appMasterVO.getAppName());

			listUserRef = submitDataMapper.findUsrRefByAppId(appId);
			//
			for (int i = 0; i < listUserRef.size(); i++) {
				UserData userData = new UserData();
				userData = userDataMapper.findUserDataByUserId(listUserRef.get(i).getUserId());
				if (userData.getRegId() != null) {
					pushFCMNotification(userData.getRegId(), notifVO);
				}

			}
			LOG.info("submit done with param : " + " submitdata: " + submitData);
			result = "Save Successfuly";

		} catch (ParkingEngineException me) {
			throw me;
		} catch (Exception e) {
			LOG.error("ERROR: " + e, e);
			throw new ParkingEngineException(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
		}

		return result;

	}

	public static void pushFCMNotification(String DeviceIdKey, NotifVO notifVO) throws Exception {

		String authKey = AUTH_KEY_FCM; // You FCM AUTH key
		String FMCurl = API_URL_FCM;

		URL url = new URL(FMCurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + authKey);
		conn.setRequestProperty("Content-Type", "application/json");

		JSONObject data = new JSONObject();
		data.put("to", DeviceIdKey.trim());
		JSONObject info = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
	/*	DataNotifVO dataNotif = new DataNotifVO();
		dataNotif.setDataNotifVO(notifVO);*/
		String x = mapper.writeValueAsString(notifVO);
		//info.put("title", notifVO.getAppName()); // Notification title
		/*info.put("body", "New Issue  : " + notifVO.getIssueNumber()); */
		info.put("message", x); // Notification
		//info.put("notifVO", x);
		// body
		data.put("data", info);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data.toString());
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		LOG.info("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

	}

	public String update(SubmitDataVO submitData) throws Exception {

		try {
			String issueNumber = submitData.getIssueNumber();
			SubmitDataVO submitresult = submitDataMapper.findIssueByIssueNumber(issueNumber);
			if (submitresult == null) {
				LOG.error("Issue NUmber Not Found, Parameter issue number : " + submitData.getIssueNumber());
				throw new ParkingEngineException(ParkingEngineException.ISSUENUMBER_NOT_FOUND);
			}
			if (submitresult.getAppId().equals("")) {
				LOG.error("INVALID APP ID : " + submitresult.getAppId());
				throw new ParkingEngineException(ParkingEngineException.APP_ID_INVALID_OR_NULL);
			}
			if (submitresult.getStatus() == 2) {
				LOG.error("Failed Change status, process is completed : " + submitData.getIssueNumber());
				throw new ParkingEngineException(ParkingEngineException.CHANGESTATUS_PROCESSDONE);
			}
			if (submitresult.getStatus() == 1 && submitData.getStatus() == 1) {
				LOG.error("Failed Change status, cause status same as exist: " + submitData.getIssueNumber());
				throw new ParkingEngineException(ParkingEngineException.CHANGESTATUS_SAMEASEXIST);
			}
			if (submitresult.getStatus() == 0 && submitData.getStatus() == 0) {
				LOG.error("Failed Change status, cause status same as exist: " + submitData.getIssueNumber());
				throw new ParkingEngineException(ParkingEngineException.CHANGESTATUS_SAMEASEXIST);
			}
			if (submitresult.getStatus() == 1 && submitData.getStatus() == 0) {
				LOG.error("Failed Change status, cause cannot change proses to 0 : " + submitData.getIssueNumber());
				throw new ParkingEngineException(ParkingEngineException.CHANGESTATUS_SMALLERTHANEXIST);
			}
			// check if user is PM
			int checkIfPM = submitDataMapper.countUsrRefByUserIdAndJobId(submitData);
			// true if userId and createdBy match || checkPM != 0
			if (submitData.getUserId().equals(submitresult.getCreatedBy()) || checkIfPM != 0) {
				if (submitData.getStatus() != 0) {
					submitDataMapper.updateStatus(submitData.getStatus(), issueNumber);
				}
			} else {
				LOG.error("User access failed, change status failed : " + submitData.getIssueNumber());
				throw new ParkingEngineException(ParkingEngineException.USER_ACCESS_FAIL);
			}

			NotifVO notifVO = new NotifVO();
			notifVO.setIssueNumber(submitData.getIssueNumber());
			notifVO.setSubject("Status Change");
			if (submitData.getStatus() == 1) {
				notifVO.setMessage("Issue Number : " + submitData.getIssueNumber() + "." + "STATUS = ON PROCESS");
			}
			if (submitData.getStatus() == 2) {
				notifVO.setMessage("Issue Number :" + " " + submitData.getIssueNumber() + "." + " STATUS = CLOSE");
			}
			listUserRef = submitDataMapper.findUsrRefByAppId(submitresult.getAppId());
			//
			for (int i = 0; i < listUserRef.size(); i++) {
				UserData userData = new UserData();
				userData = userDataMapper.findUserDataByUserId(listUserRef.get(i).getUserId());
				/* if(userData.getUserId() != submitData.getUserId()){ */
				if (userData.getRegId() != null) {
					pushFCMNotificationGeneral(userData.getRegId(), notifVO);
				}
				// }
			}

		} catch (ParkingEngineException me) {
			throw me;
		} catch (Exception e) {
			LOG.error("ERROR: " + e, e);
			throw new ParkingEngineException(ParkingEngineException.ENGINE_UNKNOWN_ERROR);
		}

		return "Update Status Successfuly";
	}

	public static void pushFCMNotificationGeneral(String DeviceIdKey, NotifVO notifVO) throws Exception {

		String authKey = AUTH_KEY_FCM; // You FCM AUTH key
		String FMCurl = API_URL_FCM;

		URL url = new URL(FMCurl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "key=" + authKey);
		conn.setRequestProperty("Content-Type", "application/json");

		JSONObject data = new JSONObject();
		data.put("to", DeviceIdKey.trim());
		JSONObject info = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		String x = mapper.writeValueAsString(notifVO);
		info.put("title", notifVO.getSubject()); // Notification title
		info.put("body", notifVO.getMessage()); // Notification
		info.put("notifVO", x);
		// body
		data.put("notification", info);

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(data.toString());
		wr.flush();
		wr.close();

		int responseCode = conn.getResponseCode();
		LOG.info("Response Code : " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

	}
}
