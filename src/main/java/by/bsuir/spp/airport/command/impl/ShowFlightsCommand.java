package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.service.FlightService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Seagull on 21.05.2016.
 */
public class ShowFlightsCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        FlightService service = FlightService.getInstance();
        try {
            ArrayList<Flight> flights = service.findAll();
            request.setAttribute("flights",flights);
        } catch (ServiceException e){
            throw new CommandException(e);
        }
        return "flights.jsp";
    }
}
