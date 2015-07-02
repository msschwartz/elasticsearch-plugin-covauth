package com.covisint.elasticsearch.covauth;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.rest.RestRequest;
import org.junit.Test;

public class RequestParserTest {
	
	
	@Test
    public void testGetUserIdFromRequestReturnsPortalUserIdFromHeaderAsString() throws RequestParser.InvalidUserIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", null);
		RequestParser parser = new RequestParser(request);
		assertEquals("sample-user-id", parser.getUserIdFromRequest());
    }
	
	@Test(expected=RequestParser.InvalidUserIdException.class)
    public void testGetUserIdFromRequestThrowsExceptionIfNotExist() throws RequestParser.InvalidUserIdException {
		RestRequest request = blankRequest();
		RequestParser parser = new RequestParser(request);
		parser.getUserIdFromRequest();
    }
	
	@Test(expected=RequestParser.InvalidUserIdException.class)
    public void testGetUserIdFromRequestThrowsExceptionIfBlank() throws RequestParser.InvalidUserIdException {
		RestRequest request = requestWithUserIdAndPath("", null);
		RequestParser parser = new RequestParser(request);
		parser.getUserIdFromRequest();
    }
	
	
	@Test
	public void testGetGroupIdFromRequestReturnsString() throws RequestParser.InvalidGroupIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "/sample-group-id");
		RequestParser parser = new RequestParser(request);
		assertEquals("sample-group-id", parser.getGroupIdFromRequest());
	}
	
	@Test
	public void testGetGroupIdFromRequestReturnsStringWhenPathHasQuery() throws RequestParser.InvalidGroupIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "/sample-group-id/search?q=1");
		RequestParser parser = new RequestParser(request);
		assertEquals("sample-group-id", parser.getGroupIdFromRequest());
	}
	
	@Test(expected=RequestParser.InvalidGroupIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfBlank() throws RequestParser.InvalidGroupIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "");
		RequestParser parser = new RequestParser(request);
		parser.getGroupIdFromRequest();
	}
	
	@Test(expected=RequestParser.InvalidGroupIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfJustSlash() throws RequestParser.InvalidGroupIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "/");
		RequestParser parser = new RequestParser(request);
		parser.getGroupIdFromRequest();
	}
	
	@Test(expected=RequestParser.InvalidGroupIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionIfNotExist() throws RequestParser.InvalidGroupIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", null);
		RequestParser parser = new RequestParser(request);
		parser.getGroupIdFromRequest();
	}
	
	@Test(expected=RequestParser.InvalidGroupIdException.class)
	public void testGetGroupIdFromRequestThrowsExceptionFormatWrong() throws RequestParser.InvalidGroupIdException {
		RestRequest request = requestWithUserIdAndPath("sample-user-id", "/_sample-group-id/search?q=1");
		RequestParser parser = new RequestParser(request);
		parser.getGroupIdFromRequest();
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
