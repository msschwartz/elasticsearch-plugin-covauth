package com.covisint.elasticsearch.covauth;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.plugins.AbstractPlugin;
import org.elasticsearch.rest.RestModule;

public class CovAuthPlugin extends AbstractPlugin {

	public String name() {
		return "covauth";
	}

	public String description() {
		return "Authorizes REST actions using Covisint APIs";
	}

	@Override
	public void processModule(Module module) {
		if (module instanceof RestModule) {
			((RestModule) module).addRestAction(CovAuthRestAction.class);
		}
	}

}
