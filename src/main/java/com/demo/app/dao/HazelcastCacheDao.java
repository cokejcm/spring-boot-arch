package com.demo.app.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import com.demo.app.domain.Entity;
import com.hazelcast.core.HazelcastInstance;

@Repository
public class HazelcastCacheDao<T extends Entity<K>, K> implements CacheDao<T, K> {

	private Class<?> c;

	@Autowired
	private HazelcastInstance instance;
	@Autowired
	private Environment environment;
	@Autowired
	private Dao<T, K> dao;

	public boolean cacheActive() {
		return Arrays.asList(this.environment.getActiveProfiles()).contains("cache");
	}

	@Override
	public void setType(Class<?> c) {
		this.c = c;
	}

	@Override
	public Class<?> getType() {
		return this.c;
	}

	@Override
	public List<T> findAll() {
		if (cacheActive()) {
			// return
			// (List<T>)instance.getMap(getType().getSimpleName()).getAll();
		}
		return dao.findAll();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findOne(K key) {
		if (cacheActive()) {
			return (T) instance.getMap(getType().getSimpleName()).get(key);
		}
		return dao.findOne(key);
	}

	@Override
	public void deleteOne(K key) {
		if (cacheActive()) {
			instance.getMap(getType().getSimpleName()).remove(key);
		}

	}

	@Override
	public void updateOne(T item) {
		if (cacheActive()) {
			dao.updateOne(item);
			instance.getMap(getType().getSimpleName()).put(item.getId(), item);
		}

	}

	@Override
	public void saveOne(T item) {
		dao.saveOne(item);
		if (cacheActive()) {
			instance.getMap(getType().getSimpleName()).put(item.getId(), item);
		}
	}

}
