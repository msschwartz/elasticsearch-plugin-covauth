package com.covisint.elasticsearch.covauth;

import java.util.List;

import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestRequest.Method;

import com.covisint.elasticsearch.covauth.apiservice.ApiService;

public class RequestAuthorizer {
	
	private ApiService service;
	private RestRequest request;
	
	public RequestAuthorizer(ApiService service, RestRequest request) {
		this.service = service;
		this.request = request;
	}
	
	public boolean canUserViewGroup(String userId, String groupId) {
		if (request.method() != Method.GET) {
			return false;
		}
		
		List<String> groups = service.getUserGroupMemeberships(userId);
		if (groups.indexOf(groupId) >= 0) {
			return true;
		}
		return false;
	}

}
