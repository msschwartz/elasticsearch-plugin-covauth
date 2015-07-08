/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth;

import java.util.regex.*;

import org.elasticsearch.rest.RestRequest;

public class RequestParser {
	private RestRequest request;
	
	public RequestParser(RestRequest request) {
		this.request = request;
	}
	
	/**
	 * Returns user id from request. 
	 * The user id should exist in the header with key: Portal-User-ID
	 * 
	 * @return String
	 * @throws InvalidUserIdException
	 */
	public String getUserIdFromRequest() throws InvalidUserIdException {
		String userId = request.header("Portal-User-ID");
		if (userId == null || userId.isEmpty()) {
			throw new InvalidUserIdException();
		}
		else {
			return userId;
		}
	}
	
	/**
	 * Returns group id from request.
	 * Group ID is retrieved from the first component of the path.
	 * Valid characters are: a-z, A-Z, 0-9, dash.
	 * 
	 * @return String
	 * @throws InvalidGroupIdException
	 */
	public String getGroupIdFromRequest() throws InvalidGroupIdException {
		String path = request.rawPath();
		if (path == null || path.isEmpty()) {
			throw new InvalidGroupIdException();
		}
		else {
			String[] pathComponents = path.split("/");
			if (pathComponents.length < 2) {
				throw new InvalidGroupIdException();
			}
			else if (isValidGroupId(pathComponents[1])) {
		    	return pathComponents[1];
		    }
		    else {
		    	throw new InvalidGroupIdException();
		    }
		}
	}
	
	/**
	 * Performs validation on group id.
	 * 
	 * @param groupId
	 * @return boolean
	 */
	private boolean isValidGroupId(String groupId) {
		String pattern = "^[a-zA-Z0-9\\-]+$";
	    Pattern r = Pattern.compile(pattern);
	    Matcher m = r.matcher(groupId);
	    return m.matches();
	}
	
	// Exception classes
	public class InvalidUserIdException extends Exception {
		private static final long serialVersionUID = 1L;
	}
	public class InvalidGroupIdException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
