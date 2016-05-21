package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.SeatDao;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.entity.Seat;

/**
 * Created by Seagull on 21.05.2016.
 */
public class SeatService extends BaseService {
    private static SeatService instance = new SeatService();
    private static SeatDao dao = factory.getSeatDao();

    private SeatService(){
    }

    public static SeatService getInstance() {
        return instance;
    }

    public Seat findFree(Flight flight) throws ServiceException{
        Seat seat = null;
        try {
            seat = dao.findFree(flight);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return seat;
    }
}
