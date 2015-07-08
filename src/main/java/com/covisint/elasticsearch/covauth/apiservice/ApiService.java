package com.covisint.elasticsearch.covauth.apiservice;

public interface ApiService {
	
	/**
	 * Use API service to search user memberships checking if a group exists which is owned
	 * by the instance, containing the view logs entitlement.
	 * 
	 * @param userId
	 * @param instanceId
	 * @return boolean
	 */
	public boolean canUserViewLogsForInstance(String userId, String instanceId);
	
}
