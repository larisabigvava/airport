package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.DocumentFormat;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.service.DocumentService;
import by.bsuir.spp.airport.service.PilotService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Seagull on 31.05.2016.
 */
public class DownloadAirlinePilotsCommand implements BaseCommand {
    private static final String SESSION_ATTRIBUTE_NAME_AIRLINE = "user";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Airline airline = (Airline)request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE);
        PilotService pilotService = PilotService.getInstance();
        try {
            ArrayList<Pilot> pilots = pilotService.findByAirline(airline);
            DocumentService documentService = DocumentService.getInstance();
            DocumentFormat format = DocumentFormat.valueOf(request.getParameter("format"));
            String link = documentService.getAirlinePilots(pilots, format);
            request.setAttribute("link",link);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "download.jsp";
    }
}
