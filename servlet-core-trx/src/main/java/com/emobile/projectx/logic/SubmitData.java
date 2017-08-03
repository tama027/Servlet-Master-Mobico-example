package com.emobile.projectx.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.core.lib.data.LoginData;
import com.myproject.core.lib.data.SubmitDataVO;
import com.myproject.parking.lib.service.LoginService;
import com.myproject.parking.lib.service.ParkingEngineException;
import com.myproject.parking.lib.service.SubmitDataService;
import com.myproject.parking.lib.utils.MessageUtils;
import com.myproject.parking.trx.logic.BaseQueryLogic;

public class SubmitData implements BaseQueryLogic {

	@Autowired
	private SubmitDataService submitDataService;

	private static final Logger LOG = LoggerFactory.getLogger(LoginUser.class);

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response, String data, ObjectMapper objMapper,
			String pathInfo) {

		LOG.debug("Start process Query :" + pathInfo);
		String result = "";
		String submitResult = "";
		try {
			// get from header
			/*
			 * String ss = request.getHeader("sessionKey"); String userId =
			 * request.getHeader("userId");
			 */

			SubmitDataVO submitData = objMapper.readValue(data, SubmitDataVO.class);

			if (submitData.getIssueNumber() != null && submitData.getIssueNumber() != "") {
				submitResult = submitDataService.update(submitData);
			} else {
				submitResult = submitDataService.save(submitData);
			}

			String x = objMapper.writeValueAsString(submitResult);
			result = MessageUtils.handleSuccess(x, objMapper);

		} catch (ParkingEngineException e) {
			LOG.error("LoginEngineException when processing " + pathInfo, e);
			result = MessageUtils.handleException(e, "", objMapper);
		} catch (Exception e) {
			LOG.error("Unexpected exception when processing " + pathInfo, e);
			result = MessageUtils.handleException(e, "Unexpected exception when processing " + e.getMessage(),
					objMapper);
		}
		return result;

	}
}
