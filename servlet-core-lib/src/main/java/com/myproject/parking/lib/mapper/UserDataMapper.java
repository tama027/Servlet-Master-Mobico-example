package com.myproject.parking.lib.mapper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.myproject.core.lib.data.ListMemberVO;
import com.myproject.core.lib.entity.UserData;

public interface UserDataMapper {
	public void createUserData(UserData userData);

	public UserData findUserDataByEmailAndPhoneNo(@Param("email") String email,
			@Param("phoneNo") String phoneNo);

	public UserData findUserDataByEmail(@Param("email") String email);
	
	public  ArrayList<ListMemberVO> findJobNameByJobId(@Param("jobId")String jobId);
	
	public UserData findUserByEmailAndPhoneNoAndActKey(
			@Param("email") String email, @Param("phoneNo") String phoneNo,
			@Param("actKey") String actKey);

	public void updateStatusUser(@Param("email") String email,
			@Param("status") int status, @Param("updatedOn") Date updatedOn);

	public void updatePasswordUser(@Param("email") String email,
			@Param("password") String password,
			@Param("updatedOn") Date updatedOn);

	public void updateLoginSessionKey(@Param("id") int id,
			@Param("sessionKey") String sessionKey,
			@Param("timeGenSessionKey") Date timeGenSessionKey,
			@Param("updatedOn") Date updatedOn);

	public void removeSessionKey(@Param("id") int id,
			@Param("sessionKey") String sessionKey,			
			@Param("updatedOn") Date updatedOn);

	public void updateSessionKeyUser(
			@Param("timeSessionKey") Date timeSessionKey,
			@Param("email") String email);
	
/*	mobico
 * 
 * */
	
	public UserData findUserDataByUserId(@Param("userId") String userId);
	
	public ArrayList<UserData> findListUserDataByUserId(@Param("userId") String userId);
	
	public void updateLoginSessionKeyMob(@Param("userId") String userId,
			@Param("sessionKey") String sessionKey,
			@Param("regId") String regId,
			@Param("updatedOn") Date updatedOn);
	
	public void updateRegId(@Param("userId") String userId,
			@Param("activateKey") String activateKey
		);
	
}
