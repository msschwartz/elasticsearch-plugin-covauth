package com.covisint.elasticsearch.covauth.apiservice;

import java.util.List;

public class MockApiService implements ApiService {
	
	private List<String> memberships;
	
	public MockApiService(List<String> memberships) {
		this.memberships = memberships;
	}

	public List<String> getUserGroupMemeberships(String userId) {
		return memberships;
	}

}
