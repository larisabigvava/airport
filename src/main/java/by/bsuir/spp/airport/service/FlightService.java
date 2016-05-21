package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.FlightDao;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Flight;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.sql.Date;

/**
 * Created by Seagull on 21.05.2016.
 */
public class FlightService extends BaseService {
    private FlightDao dao = factory.getFlightDao();

    private static FlightService instance = new FlightService();

    private FlightService(){

    }

    public static FlightService getInstance() {
        return instance;
    }


        public ArrayList<Flight> findByAirline(Airline airline) throws ServiceException {
        ArrayList<Flight> flights = null;
        try {
            flights = new ArrayList<>(dao.findByAirline(airline));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return flights;
    }

    public ArrayList<Flight> findAll() throws ServiceException {
        ArrayList<Flight> flights = null;
        try {
            flights = new ArrayList<>(dao.findAll());
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flights;
    }

    public ArrayList<Flight> findByDestination(String destination) throws ServiceException {
        ArrayList<Flight> flights = null;
        try {
            flights = new ArrayList<>(dao.findByDestination(destination));
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flights;
    }

    public ArrayList<Flight> findByDepartureDate(Date date) throws ServiceException {
        ArrayList<Flight> flights = null;
        try {
            flights = new ArrayList<>(dao.findByDepartureDate(date));
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return flights;
    }

    public boolean save(Flight flight) throws ServiceException{
        try {
            if (dao.save(flight)!=null){
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return false;
    }
}
