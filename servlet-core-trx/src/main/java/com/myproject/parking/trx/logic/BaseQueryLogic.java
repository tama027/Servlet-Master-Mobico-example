package com.myproject.parking.trx.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface BaseQueryLogic {
	
	public String process(HttpServletRequest request, HttpServletResponse response,String data, ObjectMapper objMapper, String pathInfo);
}
