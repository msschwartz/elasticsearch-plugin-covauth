/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth;

import static org.junit.Assert.*;

import java.util.*;
import java.util.Map.Entry;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestRequest.Method;
import org.junit.Test;

import com.covisint.elasticsearch.covauth.apiservice.MockApiService;

public class RequestAuthorizerTest {
	
	@Test
	public void testAllowsAccessIfUserHasMembershipToGroup() {
		List<String> groups = new ArrayList<String>();
		groups.add("group1");
		MockApiService service = new MockApiService(groups);
		RequestAuthorizer authorizer = new RequestAuthorizer(service, restRequest(Method.GET));
		assertTrue(authorizer.canUserViewGroup("user1", "group1"));
	}
	
	@Test
	public void testAllowsAccessIfUserHasMembershipToGroupWhenMultipleMembershipsExist() {
		List<String> groups = new ArrayList<String>();
		groups.add("group1");
		groups.add("group2");
		MockApiService service = new MockApiService(groups);
		RequestAuthorizer authorizer = new RequestAuthorizer(service, restRequest(Method.GET));
		assertTrue(authorizer.canUserViewGroup("user1", "group1"));
		assertTrue(authorizer.canUserViewGroup("user1", "group2"));
	}
	
	@Test
	public void testDenysRequestIfNonGet() {
		List<String> groups = new ArrayList<String>();
		groups.add("group1");
		MockApiService service = new MockApiService(groups);
		RequestAuthorizer authorizer = new RequestAuthorizer(service, restRequest(Method.POST));
		assertFalse(authorizer.canUserViewGroup("user1", "group1"));
		authorizer = new RequestAuthorizer(service, restRequest(Method.DELETE));
		assertFalse(authorizer.canUserViewGroup("user1", "group1"));
		authorizer = new RequestAuthorizer(service, restRequest(Method.PUT));
		assertFalse(authorizer.canUserViewGroup("user1", "group1"));
	}
	
	@Test
	public void testDenysRequestIfUserHasNoMemberships() {
		List<String> emptyList = new ArrayList<String>();
		MockApiService service = new MockApiService(emptyList);
		RequestAuthorizer authorizer = new RequestAuthorizer(service, restRequest(Method.GET));
		assertFalse(authorizer.canUserViewGroup("user1", "group1"));
	}
	
	@Test
	public void testDenysRequestIfGroupNotExistInMemberships() {
		List<String> groups = new ArrayList<String>();
		groups.add("group1");
		groups.add("group2");
		MockApiService service = new MockApiService(groups);
		RequestAuthorizer authorizer = new RequestAuthorizer(service, restRequest(Method.GET));
		assertTrue(authorizer.canUserViewGroup("user1", "group1"));
		assertFalse(authorizer.canUserViewGroup("user1", "group3"));
	}
	
	
	
	private RestRequest restRequest(final Method method) {
		return new RestRequest() {
			public String param(String key, String defaultValue) {
				return null;
			}
			@Override
			public Method method() {
				return method;
			}
			@Override
			public String uri() {
				return null;
			}
			@Override
			public String rawPath() {
				return null;
			}
			@Override
			public boolean hasContent() {
				return false;
			}
			@Override
			public boolean contentUnsafe() {
				return false;
			}
			@Override
			public BytesReference content() {
				return null;
			}
			@Override
			public String header(String name) {
				return null;
			}
			@Override
			public Iterable<Entry<String, String>> headers() {
				return null;
			}
			@Override
			public boolean hasParam(String key) {
				return false;
			}
			@Override
			public String param(String key) {
				return null;
			}
			@Override
			public Map<String, String> params() {
				return null;
			}
		};
	}
	
}
