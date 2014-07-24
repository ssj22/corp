package net.corp.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface GenericDAO<T, ID extends Serializable> {

    T getById(ID id, boolean lock);

    T getById(ID id);

    T loadById(ID id);

    List<T> findAll();

    @SuppressWarnings("rawtypes")
	List<T> findByCriteria(Map criterias);

    public List<T> findByExample(T exampleInstance, String[] excludeProperty);

    void save(T entity);

    void update(T entity);

    void saveOrUpdate(T entity);

    void merge(T entity);

    void delete(T entity);

    void deleteById(ID id);

    public T saveObject(T entity);

    public void deleteObject(T entity);
    
    public Session getSession();
}
