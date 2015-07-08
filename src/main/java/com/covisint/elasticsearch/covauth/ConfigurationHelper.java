/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth;

import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.settings.Settings;

/**
 * ConfigurationHelper
 * 
 * @author Michael.Schwartz@covisint.com
 */
public class ConfigurationHelper {
	
	/**
	 * YML prefix of this plugin inside elasticsearch.yml
	 */
	private final static String ES_YAML_CONF_PREFIX = "covauth.";
	
	public final String authServiceBaseUrl;
	public final String groupServiceBaseUrl;
	public final String applicationId;
	public final String clientId;
	public final String clientSecret;
	
	public ConfigurationHelper(Settings settings, ESLogger logger) {
		Settings s = settings.getByPrefix(ES_YAML_CONF_PREFIX);
		this.authServiceBaseUrl = s.get("authServiceBaseUrl");
		this.groupServiceBaseUrl = s.get("groupServiceBaseUrl");
		this.applicationId = s.get("applicationId");
		this.clientId = s.get("clientId");
		this.clientSecret = s.get("clientSecret");
	}
	
}
