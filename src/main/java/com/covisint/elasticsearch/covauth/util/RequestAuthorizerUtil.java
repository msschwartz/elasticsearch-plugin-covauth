/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth.util;

import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestRequest.Method;
import org.json.JSONArray;
import org.json.JSONObject;

import com.covisint.elasticsearch.covauth.apiservice.ApiService;

/**
 * Utility class for performing the actual authorization on a request.
 * 
 * @author mschwartz
 */
public final class RequestAuthorizerUtil {
	
	private static final String VIEW_LOGS_ENTITLEMENT_NAME = "view-logs";
	
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
     *   Must be a GET request 
     *   User must have a membership to a group that is owned by the instance and contains view logs entitlement
     * 
     * @param service
     * @param request
     * @param realmId
     * @param userId
     * @param instanceId
     * @return
     */
	public static boolean checkRequest(ApiService service, RestRequest request, String realmId, String userId, String instanceId) {
		if (isGet(request) && userHasViewLogsEntitlementInGroupOwnedByInstance(service, realmId, userId, instanceId)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	private static boolean isGet(RestRequest request) {
		return (request.method() == Method.GET);
	}
	
	
	private static boolean userHasViewLogsEntitlementInGroupOwnedByInstance(ApiService service, String realmId, String userId, String instanceId) {
		// Use service to fetch memberships json for this user
		String json = service.getUserGroupMemeberships(realmId, userId);
		
		// Iterate over memberships looking for the group owned by instance id
		JSONArray memberships = new JSONArray(json);
		for (int i = 0; i < memberships.length(); i++) {
			JSONObject membership = memberships.getJSONObject(i);
			JSONObject group = membership.getJSONObject("group");
			JSONObject owner = group.getJSONObject("owner");
			String ownerId = owner.getString("id");
			if (ownerId.equals(instanceId)) {
				// Iterate over entitlements looking for the view logs entitlement
				JSONArray entitlements = group.getJSONArray("entitlements");
				for (int j = 0; j < entitlements.length(); j++) {
					JSONObject entitlement = entitlements.getJSONObject(j);
					String entitlementName = entitlement.getString("name");
					if (entitlementName.equals(VIEW_LOGS_ENTITLEMENT_NAME)) {
						return true;
					}
				}
			}
		}
		
		// couldn't find matching group with view logs entitlement
		return false;
	}

}
