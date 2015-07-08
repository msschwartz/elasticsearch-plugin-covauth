/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth.apiservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Returns a predefined json string for every request.
 */
public class MockApiService implements ApiService {
	
	private String json = null;
	
	public MockApiService(String jsonFile) {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(jsonFile).getFile());
			byte[] encoded = Files.readAllBytes(file.toPath());
			json = new String(encoded, "UTF8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getUserGroupMemeberships(String realmId, String userId) {
		return json;
	}
	
}
