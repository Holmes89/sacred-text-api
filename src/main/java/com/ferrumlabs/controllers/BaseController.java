package com.ferrumlabs.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class BaseController  {

	public final static String VERSION = "platformadminapi.v1";
	public static final String V1_MEDIA_STRING = "application/vnd."+VERSION+"+json";
	public static final MediaType V1_MEDIA_TYPE = new MediaType("application","vnd."+VERSION+"+json");
		
	
	public BaseController()
	{
		super();
	
	}
	
	protected HttpHeaders createEntityHeaders()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-PlatformAdminApi-Media-Type", VERSION);
		headers.setContentType(V1_MEDIA_TYPE);
		return headers;
	}
	protected transient Logger log = LoggerFactory.getLogger(this.getClass());
	public Logger log()
	{
		return (log);
	}
}