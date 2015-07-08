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
		RestRequest request = requestWithUserIdAndPath("sample-user-id", null);
		assertEquals("sample-user-id", RequestParserUtil.getUserIdFromRequest(request));
    }
	
	@Test(expected=InvalidUserIdException.class)
    public void testGetUserIdFromRequestThrowsExceptionIfNotExist() throws InvalidUserIdException {
		RestRequest request = blankRequest();
		RequestParserUtil.getUserIdFromRequest(request);
    }
	
	@Test(expected=InvalidUserIdException.class)
    public void testGetUserIdFromRequestThrowsExceptionIfBlank() throws InvalidUserIdException {
		RestRequest request = requestWithUserIdAndPath("", null);
		RequestParserUtil.getUserIdFromRequest(request);
    }
	
	
	@Test
	public void testGetGroupIdFromRequestReturnsString() throws InvalidInstanceIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "/sample-group-id");
		assertEquals("sample-group-id", RequestParserUtil.getInstanceIdFromRequest(request));
	}
	
	@Test
	public void testGetGroupIdFromRequestReturnsStringWhenPathHasQuery() throws InvalidInstanceIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "/sample-group-id/search?q=1");
		assertEquals("sample-group-id", RequestParserUtil.getInstanceIdFromRequest(request));
	}
	
	@Test(expected=InvalidInstanceIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfBlank() throws InvalidInstanceIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "");
		RequestParserUtil.getInstanceIdFromRequest(request);
	}
	
	@Test(expected=InvalidInstanceIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfJustSlash() throws InvalidInstanceIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "/");
		RequestParserUtil.getInstanceIdFromRequest(request);
	}
	
	@Test(expected=InvalidInstanceIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfNotExist() throws InvalidInstanceIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", null);
		RequestParserUtil.getInstanceIdFromRequest(request);
	}
	
	@Test(expected=InvalidInstanceIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionFormatWrong() throws InvalidInstanceIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "/_sample-group-id/search?q=1");
		RequestParserUtil.getInstanceIdFromRequest(request);
	}
	
	
	
	private RestRequest blankRequest() {
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
	
	private RestRequest requestWithUserIdAndPath(final String userId, final String path) {
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
				if (name.equalsIgnoreCase("Portal-User-ID")) {
					return userId;
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
