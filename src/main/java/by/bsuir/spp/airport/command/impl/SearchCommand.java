package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.service.FlightService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Seagull on 21.05.2016.
 */
public class SearchCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        FlightService service = FlightService.getInstance();
        try {
            ArrayList<Flight> flights = null;
            switch (request.getParameter("choice")) {
                case "destination": {
                    flights = service.findByDestination(request.getParameter("search_field"));
                    break;
                }
                case "date":{
                    flights = service.findByDepartureDate(Date.valueOf(request.getParameter("search_field")));
                    break;
                }
            }
            request.setAttribute("flights",flights);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "flights.jsp";
    }
}
