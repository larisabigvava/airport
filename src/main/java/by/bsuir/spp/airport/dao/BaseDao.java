package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Entity;

import java.util.Collection;

/**
 * Created by Seagull on 21.04.2016.
 */
public interface BaseDao<T extends Entity> {
    T findById(Integer id) throws DaoException;
    Collection<T> findAll() throws DaoException;
    Integer save(T entity) throws DaoException;
}
