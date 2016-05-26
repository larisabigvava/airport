package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.service.FlightService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;

/**
 * Created by Seagull on 26.05.2016.
 */
public class ToEditFlightCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        FlightService service = FlightService.getInstance();
        try{
            request.setAttribute("flight", service.findById(Integer.parseInt(request.getParameter("id"))));
        } catch (ServiceException e){
            throw new CommandException(e);
        }
        return "flightinfo.jsp";
    }
}
