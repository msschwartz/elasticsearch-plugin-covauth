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
import com.covisint.elasticsearch.covauth.util.*;

public class RequestAuthorizerTest {
	
	@Test
	public void testRequestIsAllowedIfGroupHasViewLogsEntitlement() {
		MockApiService service = new MockApiService("user-group-memberships.json");
		assertTrue(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.GET), "realm", "user", "instance1"));
	}
	
	@Test
	public void testRequestIsAllowedIfGroupHasViewLogsEntitlementWhenMoreEntitlementsExist() {
		MockApiService service = new MockApiService("user-group-memberships.json");
		assertTrue(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.GET), "realm", "user", "instance3"));
	}
	
	@Test
	public void testRequestIsDeniedIfGroupDoesNotHaveViewLogsEntitlement() {
		MockApiService service = new MockApiService("user-group-memberships.json");
		assertFalse(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.GET), "realm", "user", "instance2"));
	}
	
	@Test
	public void testRequestIsDeniedIfUserHasNoMemberships() {
		MockApiService service = new MockApiService("empty-user-group-memberships.json");
		assertFalse(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.GET), "realm", "user", "instance1"));
	}
	
	@Test
	public void testRequestIsDeniedUnlessGet() {
		MockApiService service = new MockApiService("user-group-memberships.json");
		assertTrue(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.GET), "realm", "user", "instance1"));
		assertFalse(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.PUT), "realm", "user", "instance1"));
		assertFalse(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.POST), "realm", "user", "instance1"));
		assertFalse(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.DELETE), "realm", "user", "instance1"));
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
