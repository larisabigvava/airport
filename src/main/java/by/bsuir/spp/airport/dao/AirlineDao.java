package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Airline;

/**
 * Created by Seagull on 02.05.2016.
 */
public interface AirlineDao extends BaseDao<Airline> {
    Airline findByCredentials(Integer credentialId) throws DaoException;
    boolean delete(Integer id) throws DaoException;
}
