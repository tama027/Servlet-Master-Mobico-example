package com.myproject.core.lib.data;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

public class ListMemberVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String jobName;


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getJobName() {
		return jobName;
	}


	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
