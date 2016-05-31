package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.DocumentFormat;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.service.DocumentService;
import by.bsuir.spp.airport.service.FlightService;
import by.bsuir.spp.airport.service.PilotService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Seagull on 31.05.2016.
 */
public class DownloadPilotScheduleCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Pilot pilot = (Pilot)request.getSession().getAttribute("user");
        FlightService flightService = FlightService.getInstance();
        try {
            ArrayList<Flight> flights = flightService.findByPilot(pilot);
            DocumentService documentService = DocumentService.getInstance();
            DocumentFormat format = DocumentFormat.valueOf(request.getParameter("format"));
            String link;
            link = documentService.getPilotSchedule(flights, format).replace("/","\\");
            request.setAttribute("link",link);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "download.jsp";
    }
}
