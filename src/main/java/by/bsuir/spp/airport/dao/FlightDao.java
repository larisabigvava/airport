package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Flight;

import java.util.Collection;
import java.sql.Date;

/**
 * Created by Seagull on 21.05.2016.
 */
public interface FlightDao extends BaseDao<Flight> {
    Collection<Flight> findByAirline(Airline airline) throws DaoException;
    Collection<Flight> findByDestination(String destination) throws DaoException;
    Collection<Flight> findByDepartureDate(Date departureDate) throws DaoException;
}
