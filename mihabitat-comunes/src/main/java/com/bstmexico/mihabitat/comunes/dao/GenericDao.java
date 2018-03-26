package com.bstmexico.mihabitat.comunes.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Interface que define el comportamiento del acceso a datos genérico.
 * 
 * @author Pablo Cruz Santos
 * @version 1.0
 * @since 2015
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T, PK extends Serializable> {

	Collection<T> getList();

	void save(T type);

	T get(PK key);

	T merge(T type);

	void update(T type);

	void delete(T type);

	@SuppressWarnings("rawtypes")
	Collection<T> search(Set<Map.Entry> set);
}
