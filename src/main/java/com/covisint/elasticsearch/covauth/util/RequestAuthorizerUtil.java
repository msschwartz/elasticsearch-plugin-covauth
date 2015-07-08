package com.covisint.elasticsearch.covauth.util;

import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestRequest.Method;

import com.covisint.elasticsearch.covauth.apiservice.ApiService;

public final class RequestAuthorizerUtil {
	
	/**
     * Private constructor.
     */
    private RequestAuthorizerUtil() {
        // don't instantiate
    }
	
    /**
     * Check if this request should be allowed.
     * 
     * Rules:
     *   Only GET requests are allowed 
     *   User must have a membership to a group that is owned by the instance and contains view logs entitlement
     * 
     * @param service
     * @param request
     * @param userId
     * @param instanceId
     * @return
     */
	public static boolean checkRequest(ApiService service, RestRequest request, String userId, String instanceId) {
		if (request.method() != Method.GET) {
			return false;
		}
		
		if (service.canUserViewLogsForInstance(userId, instanceId)) {
			return true;
		}
		else {
			return false;
		}
	}

}
