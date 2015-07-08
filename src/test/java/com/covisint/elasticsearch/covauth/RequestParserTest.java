/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.rest.RestRequest;
import org.junit.Test;

import com.covisint.elasticsearch.covauth.exception.*;
import com.covisint.elasticsearch.covauth.util.*;

public class RequestParserTest {
	
	
	@Test
    public void testGetUserIdFromRequestReturnsPortalUserIdFromHeaderAsString() throws InvalidUserIdException {
		RestRequest request = testRequest("sample-user-id", null, null);
		assertEquals("sample-user-id", RequestParserUtil.getUserIdFromRequest(request));
    }
	
	@Test(expected=InvalidUserIdException.class)
    public void testGetUserIdFromRequestThrowsExceptionIfNotExist() throws InvalidUserIdException {
		RestRequest request = testRequest(null, null, null);
		RequestParserUtil.getUserIdFromRequest(request);
    }
	
	@Test(expected=InvalidUserIdException.class)
    public void testGetUserIdFromRequestThrowsExceptionIfBlank() throws InvalidUserIdException {
		RestRequest request = testRequest("", null, null);
		RequestParserUtil.getUserIdFromRequest(request);
    }
	
	
	@Test
    public void testGetReamlIdFromRequestReturnsPortalUserIdFromHeaderAsString() throws InvalidRealmIdException {
		RestRequest request = testRequest("sample-user-id", "/sample-group-id", "sample-realm-id");
		assertEquals("sample-realm-id", RequestParserUtil.getRealmIdFromRequest(request));
    }
	
	@Test(expected=InvalidRealmIdException.class)
    public void testGetRealmIdFromRequestThrowsExceptionIfNotExist() throws InvalidRealmIdException {
		RestRequest request = testRequest("sample-user-id", "/sample-group-id", null);
		RequestParserUtil.getRealmIdFromRequest(request);
    }
	
	@Test(expected=InvalidRealmIdException.class)
    public void testGetRealmIdFromRequestThrowsExceptionIfBlank() throws InvalidRealmIdException {
		RestRequest request = testRequest("sample-user-id", "/sample-group-id", "");
		RequestParserUtil.getRealmIdFromRequest(request);
    }
	
	
	@Test
	public void testGetGroupIdFromRequestReturnsString() throws InvalidInstanceIdException {
		RestRequest request = testRequest("sample-user-id", "/sample-group-id", null);
		assertEquals("sample-group-id", RequestParserUtil.getInstanceIdFromRequest(request));
	}
	
	@Test
	public void testGetGroupIdFromRequestReturnsStringWhenPathHasQuery() throws InvalidInstanceIdException {
		RestRequest request = testRequest("sample-user-id", "/sample-group-id/search?q=1", null);
		assertEquals("sample-group-id", RequestParserUtil.getInstanceIdFromRequest(request));
	}
	
	@Test(expected=InvalidInstanceIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfBlank() throws InvalidInstanceIdException {
		RestRequest request = testRequest("sample-user-id", "", null);
		RequestParserUtil.getInstanceIdFromRequest(request);
	}
	
	@Test(expected=InvalidInstanceIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfJustSlash() throws InvalidInstanceIdException {
		RestRequest request = testRequest("sample-user-id", "/", null);
		RequestParserUtil.getInstanceIdFromRequest(request);
	}
	
	@Test(expected=InvalidInstanceIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfNotExist() throws InvalidInstanceIdException {
		RestRequest request = testRequest("sample-user-id", null, null);
		RequestParserUtil.getInstanceIdFromRequest(request);
	}
	
	@Test(expected=InvalidInstanceIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionFormatWrong() throws InvalidInstanceIdException {
		RestRequest request = testRequest("sample-user-id", "/_sample-group-id/search?q=1", null);
		RequestParserUtil.getInstanceIdFromRequest(request);
	}
	
	
	
	
	private RestRequest testRequest(final String userId, final String path, final String realmId) {
		return new RestRequest() {
			
			public String param(String key, String defaultValue) {
				return null;
			}
			@Override
			public Method method() {
				return null;
			}
			@Override
			public String uri() {
				return null;
			}
			@Override
			public String rawPath() {
				return path;
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
				if (name.equals("Portal-User-ID")) {
					return userId;
				}
				if (name.equals("Realm-ID")) {
					return realmId;
				}
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
