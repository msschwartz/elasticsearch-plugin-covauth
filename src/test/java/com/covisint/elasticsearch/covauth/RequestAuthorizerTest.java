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
	public void testRequestIsDeniedUnlessGet() {
		MockApiService service = new MockApiService();
		assertTrue(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.GET), "user1", "group1"));
		assertFalse(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.PUT), "user1", "group1"));
		assertFalse(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.POST), "user1", "group1"));
		assertFalse(RequestAuthorizerUtil.checkRequest(service, restRequest(Method.DELETE), "user1", "group1"));
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
