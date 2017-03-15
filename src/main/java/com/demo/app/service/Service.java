package com.demo.app.service;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.demo.app.dao.HzCacheDao;
import com.demo.app.domain.Entity;

public abstract class Service<T extends Entity<K>, K extends Serializable> {

	public abstract Class<?> getType();

	@Autowired
	public abstract HzCacheDao<T, K> getCacheDao();

	@PostConstruct
	public void init() {
		getCacheDao().setType(getType());
	}

	public void cacheAll(){
		getCacheDao().cacheAll();
	}
}
