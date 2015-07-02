package com.covisint.elasticsearch.covauth;

import java.util.Arrays;

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

import com.covisint.elasticsearch.covauth.apiservice.MockApiService;

public class CovAuthRestAction extends BaseRestHandler {
	
	@Inject
	public CovAuthRestAction(final Settings settings, Client client, RestController controller) {
		super(settings, controller, client);
		
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
	 * Check if user can view logs for the requested group id. Only GET requests will be allowed.
	 * 
	 * @param request
	 * @return boolean
	 */
	public boolean checkRequest(RestRequest request) {
		// Temporary mock service - this needs to be initialized somewhere else.
		MockApiService service = new MockApiService(Arrays.asList("group1", "group2", "group3"));
		
		try {
			RequestParser parser = new RequestParser(request);
			String userId = parser.getUserIdFromRequest();
			String groupId = parser.getGroupIdFromRequest();
			
			RequestAuthorizer authorizer = new RequestAuthorizer(service, request);
			return authorizer.canUserViewGroup(userId, groupId);
		}
		catch (RequestParser.InvalidUserIdException ex) {
			logger.info("Denying request because user id was invalid", request);
			return false;
		}
		catch (RequestParser.InvalidGroupIdException ex) {
			logger.info("Denying request because group id was invalid", request);
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
