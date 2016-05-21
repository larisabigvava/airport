package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.TicketDao;
import by.bsuir.spp.airport.entity.Ticket;

import java.util.ArrayList;

/**
 * Created by Seagull on 21.05.2016.
 */
public class TicketService extends BaseService {
    private static TicketService instance = new TicketService();
    private static TicketDao dao = factory.getTicketDao();

    private TicketService(){
    }

    public static TicketService getInstance() {
        return instance;
    }

    public ArrayList<Ticket> findByClient(Integer id) throws ServiceException{
        ArrayList<Ticket> tickets = null;
        try {
            tickets = new ArrayList<>(dao.findByClient(id));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return tickets;
    }

    public boolean reserveTicket(Ticket ticket) throws ServiceException{
        boolean result = false;
        try {
            result = dao.reserveTicket(ticket);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return result;
    }

}
