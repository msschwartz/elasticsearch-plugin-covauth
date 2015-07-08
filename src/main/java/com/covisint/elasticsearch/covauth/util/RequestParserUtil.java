/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth.util;

import java.util.regex.*;

import org.elasticsearch.rest.RestRequest;

import com.covisint.elasticsearch.covauth.exception.*;

/**
 * Utility class for parsing arguments in request.
 * 
 * @author mschwartz
 */
public final class RequestParserUtil {
	
	/**
     * Private constructor.
     */
    private RequestParserUtil() {
        // don't instantiate
    }
    
    /**
     * Returns realm id from request. Realm ID should exist in header with key: Realm-ID
     * 
     * @param request
     * @return Header Realm-ID value
     * @throws InvalidRealmIdException 
     */
    public static String getRealmIdFromRequest(RestRequest request) throws InvalidRealmIdException {
    	String userId = request.header("Realm-ID");
		if (userId == null || userId.isEmpty()) {
			throw new InvalidRealmIdException();
		}
		else {
			return userId;
		}
	}
	
	/**
	 * Returns user id from request. The user id should exist in the header with key: Portal-User-ID
	 * 
	 * @param request
	 * @return Header Portal-User-ID value
	 * @throws InvalidUserIdException
	 */
	public static String getUserIdFromRequest(RestRequest request) throws InvalidUserIdException {
		String userId = request.header("Portal-User-ID");
		if (userId == null || userId.isEmpty()) {
			throw new InvalidUserIdException();
		}
		else {
			return userId;
		}
	}
	
	/**
	 * Returns instance id from request.
	 * Instance ID is retrieved from the first component of the path.
	 * Valid characters are: a-z, A-Z, 0-9, dash.
	 * 
	 * @param request
	 * @return Instance id
	 * @throws InvalidInstanceIdException
	 */
	public static String getInstanceIdFromRequest(RestRequest request) throws InvalidInstanceIdException {
		String path = request.rawPath();
		if (path == null || path.isEmpty()) {
			throw new InvalidInstanceIdException();
		}
		else {
			String[] pathComponents = path.split("/");
			if (pathComponents.length < 2) {
				throw new InvalidInstanceIdException();
			}
			else if (isValidInstanceId(pathComponents[1])) {
		    	return pathComponents[1];
		    }
		    else {
		    	throw new InvalidInstanceIdException();
		    }
		}
	}
	
	/**
	 * Performs regex validation on instance id.
	 * 
	 * @param groupId
	 * @return boolean
	 */
	private static boolean isValidInstanceId(String groupId) {
		String pattern = "^[a-zA-Z0-9\\-]+$";
	    Pattern r = Pattern.compile(pattern);
	    Matcher m = r.matcher(groupId);
	    return m.matches();
	}
}
