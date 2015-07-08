package com.covisint.elasticsearch.covauth.apiservice;

public class MockApiService implements ApiService {

	public boolean canUserViewLogsForInstance(String userId, String instanceId) {
		return true;
	}

}
