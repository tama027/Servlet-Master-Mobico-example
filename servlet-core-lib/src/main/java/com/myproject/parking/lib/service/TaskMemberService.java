package com.myproject.parking.lib.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.core.lib.data.BankMasterVO;
import com.myproject.core.lib.data.ListMemberVO;
import com.myproject.core.lib.data.SubmitDataVO;
import com.myproject.core.lib.data.TaskInfoVO;
import com.myproject.core.lib.data.TaskMemberVO;
import com.myproject.parking.lib.mapper.GetMessageMapper;
import com.myproject.parking.lib.mapper.SubmitDataMapper;
import com.myproject.parking.lib.mapper.UserDataMapper;

@Service
public class TaskMemberService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private SubmitDataMapper submitDataMapper;

	@Autowired
	private GetMessageMapper getMessageMapper;

	@Autowired
	private UserDataMapper userDataMapper;

	public TaskMemberVO findTaskMember(SubmitDataVO submitDataVO) throws ParkingEngineException {
		TaskMemberVO taskMemberVO = new TaskMemberVO();

		SubmitDataVO submitResult = submitDataMapper.findIssueByIssueNumber(submitDataVO.getIssueNumber());

		if (submitResult != null) {
			BankMasterVO bankMasterVO = getMessageMapper.findBankById(submitResult.getBankId());

			taskMemberVO.setBankName(bankMasterVO.getBankName());
			taskMemberVO.setTaskName(submitResult.getTaskName());
			ArrayList<ListMemberVO> listMemberVO = submitDataMapper.findUsrRefAllByAppId(submitResult.getAppId());
			if (listMemberVO != null) {
				taskMemberVO.setListMemberVO(listMemberVO);
			} else {
				listMemberVO = new ArrayList<ListMemberVO>();
			}
		} else {
			throw new ParkingEngineException(ParkingEngineException.DATA_NOT_FOUND);
		}
		return taskMemberVO;

	}

	public TaskInfoVO findTaskInfo(SubmitDataVO submitDataVO) throws ParkingEngineException {
		TaskInfoVO taskInfoVO = new TaskInfoVO();
		SubmitDataVO submitResult = submitDataMapper.findIssueByIssueNumber(submitDataVO.getIssueNumber());

		if (submitResult != null) {
			BankMasterVO bankMasterVO = getMessageMapper.findBankById(submitResult.getBankId());
			taskInfoVO = submitDataMapper.findTaskInfoByAppUd(submitResult.getAppId());	
			taskInfoVO.setTaskName(submitResult.getTaskName());
			taskInfoVO.setTaskDesc(submitResult.getTaskDesc());
			if(submitResult.getStatus() == 0){
				taskInfoVO.setStrStatus("Pending (No Action)");
			}else if(submitResult.getStatus() == 1){
				taskInfoVO.setStrStatus("On Process");
			}else{
				taskInfoVO.setStrStatus("Completed/Closed");
			}
					
		} else {
			throw new ParkingEngineException(ParkingEngineException.DATA_NOT_FOUND);
		}
		return taskInfoVO;
		
	}

}
