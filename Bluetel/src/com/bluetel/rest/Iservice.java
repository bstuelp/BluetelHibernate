package com.bluetel.rest;

import javax.ws.rs.core.Response;

import com.bluetel.entity.Customer;

public interface Iservice
{
	public Response getMetrics(int id);

	public Response setMeterReaders(String json);
	
	public boolean validJson(String json);
	
	public Customer parseJsonJavaObject(String strJson);
}
