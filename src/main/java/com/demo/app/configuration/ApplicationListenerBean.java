package com.demo.app.configuration;

import java.util.Set;

import org.reflections.Reflections;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.demo.app.service.Service;
import com.demo.app.util.Constants;

@Component
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent>{

	/**
	 * Repopulate the cache after a reload event
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// For every Cacheable service bean, populate the cache with its data
		Reflections reflections = new Reflections(Constants.SERVICE_PACKAGE);
		Set<Class<? extends Service>> allClasses = reflections.getSubTypesOf(Service.class);
		for (Class<? extends Service> clazz : allClasses) {
			ContextProvider.getBean(clazz).cacheAll();
		}
	}

}