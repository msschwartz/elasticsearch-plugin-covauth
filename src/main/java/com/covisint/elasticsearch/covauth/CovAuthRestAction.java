/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestFilter;
import org.elasticsearch.rest.RestFilterChain;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestStatus;

import com.covisint.elasticsearch.covauth.apiservice.*;
import com.covisint.elasticsearch.covauth.exception.*;
import com.covisint.elasticsearch.covauth.util.*;

/**
 * Authorizes every REST request using a RestFilter on the main RestController.
 * 
 * @author Michael.Schwartz@covisint.com
 */
public class CovAuthRestAction extends BaseRestHandler {
	
	private final ApiService service;
	private final ConfigurationHelper config;
	
	@Inject
	public CovAuthRestAction(Settings settings, RestController controller, Client client) {
		super(settings, controller, client);
		
		config = new ConfigurationHelper(settings, logger);
		//service = new CovisintApiService(config, logger);
		service = new MockApiService("user-group-memberships.json");
		
		controller.registerFilter(new RestFilter() {
			
			@Override
			public void process(RestRequest request, RestChannel channel, RestFilterChain filterChain) {
				boolean allow = checkRequest(request);
				if (allow) {
					allow(request, filterChain, channel);
				} else {
					deny(request, filterChain, channel);
				}
			}
			
		});
	}
	
	/**
	 * Checks if user can view logs for the requested instance id.
	 * 
	 * @param request
	 * @return boolean
	 */
	public boolean checkRequest(RestRequest request) {
		try {
			String realmId = RequestParserUtil.getRealmIdFromRequest(request);
			String userId = RequestParserUtil.getUserIdFromRequest(request);
			String instanceId = RequestParserUtil.getInstanceIdFromRequest(request);
			
			return RequestAuthorizerUtil.checkRequest(service, request, realmId, userId, instanceId);
		}
		catch (InvalidRealmIdException e) {
			logger.info("Denying request because realm id was invalid: {}", request);
			return false;
		}
		catch (InvalidUserIdException ex) {
			logger.info("Denying request because user id was invalid: {}", request);
			return false;
		}
		catch (InvalidInstanceIdException ex) {
			logger.info("Denying request because instance id was invalid: {}", request);
			return false;
		}
	}
	
	public void allow(RestRequest request, RestFilterChain filterChain, RestChannel channel){
		filterChain.continueProcessing(request, channel);
	}
	
	public void deny(RestRequest request, RestFilterChain filterChain, RestChannel channel) {
		channel.sendResponse(new BytesRestResponse(RestStatus.FORBIDDEN, "forbidden"));
	}
	
	@Override
	protected void handleRequest(RestRequest request, RestChannel channel, Client client) throws Exception {}
	
}
