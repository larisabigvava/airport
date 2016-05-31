package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.DocumentFormat;
import by.bsuir.spp.airport.service.AirlineService;
import by.bsuir.spp.airport.service.DocumentService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Seagull on 31.05.2016.
 */
public class DownloadAirlinesCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        AirlineService airlineService = AirlineService.getInstance();
        try {
            ArrayList<Airline> airlines = airlineService.findAll();
            DocumentService documentService = DocumentService.getInstance();
            DocumentFormat format = DocumentFormat.valueOf(request.getParameter("format"));
            String link;
            link = documentService.getAirlines(airlines, format).replace("/","\\");
            request.setAttribute("link",link);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "download.jsp";
    }
}
