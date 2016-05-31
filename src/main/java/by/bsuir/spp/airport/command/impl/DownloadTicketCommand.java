package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.DocumentFormat;
import by.bsuir.spp.airport.entity.Ticket;
import by.bsuir.spp.airport.service.DocumentService;
import by.bsuir.spp.airport.service.ServiceException;
import by.bsuir.spp.airport.service.TicketService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 30.05.2016.
 */
public class DownloadTicketCommand implements BaseCommand {
    private static final String REQUEST_PARAMETER_LINK = "link";
    private static final String REQUEST_PARAMETER_FORMAT = "format";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        TicketService ticketService = TicketService.getInstance();
        DocumentService service = DocumentService.getInstance();
        try {
            Ticket ticket = ticketService.findById(Integer.parseInt(request.getParameter("id")));
            DocumentFormat format = DocumentFormat.valueOf(request.getParameter(REQUEST_PARAMETER_FORMAT));
            String link = service.getTicket(ticket, format).replace("/","\\");
            request.setAttribute(REQUEST_PARAMETER_LINK, link);
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
        return "download.jsp";
    }
}
