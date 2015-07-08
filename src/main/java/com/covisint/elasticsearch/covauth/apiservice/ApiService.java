/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth.apiservice;

public interface ApiService {
	
	/**
	 * Use API service to fetch user memberships as JSON string.
	 * 
	 * @param realmId
	 * @param userId
	 * @return json string
	 */
	public String getUserGroupMemeberships(String realmId, String userId);
	
}
