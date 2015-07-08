package com.covisint.elasticsearch.covauth.apiservice;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.elasticsearch.common.logging.ESLogger;

import com.covisint.core.http.service.core.Page;
import com.covisint.elasticsearch.covauth.ConfigurationHelper;
import com.covisint.platform.group.client.sdk.GroupMembershipSDK;
import com.covisint.platform.group.client.sdk.GroupMembershipSDK.GroupMembershipClient;
import com.covisint.platform.group.client.sdk.GroupMembershipSDK.GroupMembershipClient.ResponseOption;
import com.covisint.platform.group.core.group.Group;
import com.covisint.platform.group.core.group.GroupEntitlement;
import com.covisint.platform.group.core.group.GroupMembership;
import com.covisint.platform.oauth.client.token.sdk.AuthConfigurationProvider;

public class CovisintApiService implements ApiService {
	
	private static final String VIEW_LOGS_ENTITLEMENT_NAME = "VIEW_LOGS";
	private GroupMembershipClient groupMembershipClient;
	
	
	/**
	 * Public constructor
	 * 
	 * @param config
	 * @param logger
	 */
	public CovisintApiService(ConfigurationHelper config, ESLogger logger) {
		GroupMembershipSDK sdk = new GroupMembershipSDK("https://apistg.np.covapp.io/group/v1");
		sdk.setAuthConfigProvider(new CovAuthConfigurationProvider(config, logger));
		groupMembershipClient = sdk.newClient();
	}
	
	
	/** {@inheritDoc} */
	public boolean canUserViewLogsForInstance(String userId, String instanceId) {
		// Find user group memberships owned by the instanceId
		List<GroupMembership> memberships = groupMembershipClient.search(userId, null, 
				instanceId, null, Page.ALL, ResponseOption.INCLUDE_GROUP).checkedGet();
        
        // Iterate over groups checking if one contains the VIEW_LOGS entitlement
        for (GroupMembership membership : memberships) {
        	if (groupContainsViewLogsEntitlement(membership.getGroup())) {
        		return true;
        	}
		}
        
        // No group was found containing VIEW_LOGS entitlement
		return false;
	}
	
	
	/**
	 * Checks if the group contains the view logs entitlement.
	 * 
	 * @param group
	 * @return boolean
	 */
	private boolean groupContainsViewLogsEntitlement(Group group) {
		for (GroupEntitlement entitlement : group.getEntitlements()) {
			if (entitlement.getName().equalsIgnoreCase(VIEW_LOGS_ENTITLEMENT_NAME)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * Private inner class for pulling API configuration from ConfigurationHelper.
	 * 
	 * @author Michael.Schwartz@covisint.com
	 */
	private class CovAuthConfigurationProvider implements AuthConfigurationProvider {
		
		private ConfigurationHelper config;
		private ESLogger logger;
		
		public CovAuthConfigurationProvider(ConfigurationHelper config, ESLogger logger) {
			this.config = config;
			this.logger = logger;
		}
		
		public String getClientSecret() {
			return config.clientSecret;
		}
		
		public String getClientId() {
			return config.clientId;
		}
		
		public URL getAuthServiceBaseUrl() {
			try {
				return new URL(config.authServiceBaseUrl);
			} catch (MalformedURLException e) {
				logger.error("Bad auth service URL");
				return null;
			}
		}
		
		public String getApplicationId() {
			return config.applicationId;
		}
	}

}
