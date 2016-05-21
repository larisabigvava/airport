package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.PilotDao;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Pilot;

import java.util.ArrayList;

/**
 * Created by Seagull on 03.05.2016.
 */
public class PilotService extends BaseService {
    private static PilotService instance = new PilotService();
    private static PilotDao dao = factory.getPilotDao();

    private PilotService(){
    }

    public static PilotService getInstance() {
        return instance;
    }

    public Pilot signIn(Integer id) throws ServiceException{
        Pilot pilot = null;
        try {
            pilot = dao.findByCredentials(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return pilot;
    }

    public Integer deleteById(Integer id) throws ServiceException {
        Integer rows;
        try {
            rows = dao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return rows;
    }

    public ArrayList<Pilot> findByAirline(Airline airline) throws ServiceException {
        ArrayList<Pilot> pilots = null;
        try {
            pilots = new ArrayList<>(dao.findByAirline(airline));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return pilots;
    }

    public boolean save(Pilot pilot) throws ServiceException{
        boolean result = false;
        try{
            if (dao.save(pilot) != null){
                result = true;
            }
        } catch (DaoException ex){
            throw new ServiceException(ex);
        }
        return result;
    }
}
