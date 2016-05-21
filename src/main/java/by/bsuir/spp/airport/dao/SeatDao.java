package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.entity.Seat;

/**
 * Created by Seagull on 21.05.2016.
 */
public interface SeatDao extends BaseDao<Seat> {
    Seat findFree(Flight flight) throws DaoException;
    boolean reserve(Seat seat) throws DaoException;
}
