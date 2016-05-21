package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Pilot;

import java.util.Collection;

/**
 * Created by Seagull on 02.05.2016.
 */
public interface PilotDao extends BaseDao<Pilot> {
    Pilot findByCredentials(Integer credentialId) throws DaoException;
    Integer deleteById(Integer id) throws DaoException;
    Collection<Pilot> findByAirline(Airline airline) throws DaoException;
}
