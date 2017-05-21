package com.demo.app.configuration;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * Provides the location of the packages with the Rest resources and @Providers
 *
 * @author coke
 *
 */
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(RequestContextFilter.class);
		packages("com.demo.app.controller");
		packages("com.demo.app.configuration");
		register(JacksonFeature.class);
		property(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);
	}

}
