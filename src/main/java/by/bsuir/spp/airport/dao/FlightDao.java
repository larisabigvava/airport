package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.entity.Pilot;

import java.util.Collection;
import java.sql.Date;

/**
 * Created by Seagull on 21.05.2016.
 */
public interface FlightDao extends BaseDao<Flight> {
    Collection<Flight> findByAirline(Airline airline) throws DaoException;
    Collection<Flight> findByDestination(String destination) throws DaoException;
    Collection<Flight> findByDepartureDate(Date departureDate) throws DaoException;
    Integer update(Flight flight) throws DaoException;
    Collection<Flight> findByPilot(Pilot pilot) throws  DaoException;
}
