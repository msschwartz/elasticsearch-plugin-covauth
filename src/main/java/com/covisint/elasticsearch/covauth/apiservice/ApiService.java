package com.covisint.elasticsearch.covauth.apiservice;

import java.util.List;

public interface ApiService {
	
	public List<String> getUserGroupMemeberships(String userId);
	
}
