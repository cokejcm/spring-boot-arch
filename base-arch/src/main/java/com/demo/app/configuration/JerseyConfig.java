package com.demo.app.configuration;

import javax.annotation.PostConstruct;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

/**
 * Provides the location of the packages with the Rest resources and @Providers
 *
 * @author coke
 *
 */
public class JerseyConfig extends ResourceConfig {

	@PostConstruct
	public void init() {
		// Register components where DI is needed
		this.configureSwagger();
	}

	public JerseyConfig() {
		register(RequestContextFilter.class);
		packages("com.demo.app.controller");
		packages("com.demo.app.configuration");
		register(JacksonFeature.class);
		property(ServerProperties.MOXY_JSON_FEATURE_DISABLE, true);
		this.configureSwagger();
	}

	private void configureSwagger() {
		// Available at localhost:port/swagger.json
		this.register(ApiListingResource.class);
		this.register(SwaggerSerializers.class);

		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setConfigId("springboot-jersey-swagger-docker-example");
		beanConfig.setTitle("Spring Boot + Jersey + Swagger + Docker Example");
		beanConfig.setVersion("v2");
		beanConfig.setHost("localhost:9090");
		beanConfig.setContact("Coke");
		beanConfig.setSchemes(new String[] { "http", "https" });
		beanConfig.setBasePath("/app");
		// config.setBasePath("/");
		beanConfig.setResourcePackage("com.demo.app.controller");
		beanConfig.setPrettyPrint(true);
		beanConfig.setScan(true);
	}

}
