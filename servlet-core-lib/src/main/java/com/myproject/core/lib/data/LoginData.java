package com.myproject.core.lib.data;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class LoginData implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String userId;
	private String regId;
	private String password;
	private String sessionKey;
	
	/*private String name;
	private String phoneNo;
	private String groupUser;
	private String email;*/
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	/*public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}*/

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/*public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getGroupUser() {
		return groupUser;
	}

	public void setGroupUser(String groupUser) {
		this.groupUser = groupUser;
	}
*/
	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

}
