package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.AirlineDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.entity.Airline;

import java.util.ArrayList;

/**
 * Created by Seagull on 03.05.2016.
 */
public class AirlineService extends BaseService {
    private static AirlineService instance = new AirlineService();
    private static AirlineDao dao = factory.getAirlineDao();

    private AirlineService(){
    }

    public static AirlineService getInstance() {
        return instance;
    }

    public Airline signIn(Integer id) throws ServiceException{
        Airline airline = null;
        try {
            airline = dao.findByCredentials(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return airline;
    }

    public ArrayList<Airline> findAll() throws ServiceException {
        ArrayList<Airline> airlines = null;
        try {
            airlines = new ArrayList<>(dao.findAll());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return airlines;
    }

    public boolean save(Airline airline) throws ServiceException {
        boolean result = false;
        try {
            if (dao.save(airline)!=null){
                result = true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public boolean delete(Integer id) throws ServiceException {
        boolean result = false;
        PilotService service = PilotService.getInstance();
        try {
            if (service.deleteById(id)!=null) {
                if (dao.delete(id)) {
                    result = true;
                }
            }
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

}
