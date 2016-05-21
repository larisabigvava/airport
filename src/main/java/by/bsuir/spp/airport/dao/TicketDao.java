package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Ticket;

import java.util.Collection;

/**
 * Created by Seagull on 21.05.2016.
 */
public interface TicketDao extends BaseDao<Ticket>{
    Collection<Ticket> findByClient(Integer id) throws DaoException;
    boolean reserveTicket(Ticket ticket) throws DaoException;
}
