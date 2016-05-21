package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Client;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.entity.Ticket;
import by.bsuir.spp.airport.service.SeatService;
import by.bsuir.spp.airport.service.ServiceException;
import by.bsuir.spp.airport.service.TicketService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 21.05.2016.
 */
public class ReserveTicketCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        TicketService service = TicketService.getInstance();
        Ticket ticket = new Ticket();
        Flight flight = new Flight();
        flight.setId(Integer.parseInt(request.getParameter("id_flight")));
        ticket.setClient((Client)request.getSession().getAttribute("user"));
        ticket.setFlight(flight);
        ticket.setPrice(42);
        try {
            SeatService seatService = SeatService.getInstance();
            ticket.setSeat(seatService.findFree(flight));
            service.reserveTicket(ticket);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "flights.jsp";
    }
}
