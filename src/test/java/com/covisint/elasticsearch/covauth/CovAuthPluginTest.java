/* Copyright (C) 2015 Covisint. All Rights Reserved. */
package com.covisint.elasticsearch.covauth;

import static org.junit.Assert.*;

import org.junit.Test;

public class CovAuthPluginTest {
	
	@Test
    public void testCovAuthPluginName() {
    	CovAuthPlugin plugin = new CovAuthPlugin();
        assertEquals("covauth", plugin.name());
    }
    
	@Test
    public void testCovAuthPluginDescription() {
    	CovAuthPlugin plugin = new CovAuthPlugin();
        assertEquals("Authorizes REST actions using Covisint APIs", plugin.description());
    }
}
