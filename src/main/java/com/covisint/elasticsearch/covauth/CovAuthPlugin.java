/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth;

import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.rest.RestModule;

/**
 * Main plugin class that registers with elasticsearch. For every ES rest module, this class will
 * register the CovAuthRestAction class as an action.
 * 
 * @author Michael.Schwartz@covisint.com
 */
public class CovAuthPlugin extends AbstractPlugin {
	
	private static final String NAME = "covauth";
	
	private static final String DESCRIPTION = "Authorizes REST actions using Covisint APIs";

	public String name() {
		return NAME;
	}
	
	public String description() {
		return DESCRIPTION;
	}
	
	public void onModule(RestModule module) {
		module.addRestAction(CovAuthRestAction.class);
	}
	
}
