package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.entity.Plane;
import by.bsuir.spp.airport.service.FlightService;
import by.bsuir.spp.airport.service.PilotService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Seagull on 22.05.2016.
 */
public class EditFlightCommand implements BaseCommand{
    private static final String SESSION_ATTRIBUTE_NAME_AIRLINE = "user";
    private static final String REQUEST_PARAMETER_NAME_ID_PLANE = "id_plane";
    private static final String REQUEST_PARAMETER_NAME_ID_PILOT = "id_pilot";
    private static final String REQUEST_PARAMETER_NAME_DEPARTURE_TIME = "departure_time";
    private static final String REQUEST_PARAMETER_NAME_DEPARTURE_DATE = "departure_date";
    private static final String REQUEST_PARAMETER_NAME_ARRIVAL_TIME = "arrival_time";
    private static final String REQUEST_PARAMETER_NAME_ARRIVAL_DATE = "arrival_date";
    private static final String REQUEST_PARAMETER_NAME_DESTINATION = "destination";
    private static final String REQUEST_PARAMETER_NAME_FLIGHT_NUMBER = "flight_number";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Flight flight = validate(request);
        FlightService service = FlightService.getInstance();
        try {
            service.update(flight);
            PilotService pilotService = PilotService.getInstance();
            request.setAttribute("flights",service.findByAirline((Airline) request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE)));
            request.setAttribute("pilots",pilotService.findByAirline((Airline) request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE)));

        } catch (ServiceException e){
            throw new CommandException(e);
        }
        return "airline.jsp";
    }

    private Flight validate(HttpServletRequest request) {
        Flight flight = new Flight();
        flight.setId(Integer.parseInt(request.getParameter("id")));
        flight.setDestination(request.getParameter(REQUEST_PARAMETER_NAME_DESTINATION));
        flight.setFlightNumber(request.getParameter(REQUEST_PARAMETER_NAME_FLIGHT_NUMBER));
        flight.setDepartureTime(Time.valueOf(request.getParameter(REQUEST_PARAMETER_NAME_DEPARTURE_TIME)));
        flight.setDepartureDate(Date.valueOf(request.getParameter(REQUEST_PARAMETER_NAME_DEPARTURE_DATE)));
        flight.setArrivalTime(Time.valueOf(request.getParameter(REQUEST_PARAMETER_NAME_ARRIVAL_TIME)));
        flight.setArrivalDate(Date.valueOf(request.getParameter(REQUEST_PARAMETER_NAME_ARRIVAL_DATE)));
        Airline airline = (Airline)request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE);
        flight.setAirline(airline);
        Pilot pilot = new Pilot();
        pilot.setId(Integer.parseInt(request.getParameter(REQUEST_PARAMETER_NAME_ID_PILOT)));
        flight.setPilot(pilot);
        Plane plane = new Plane();
        plane.setId(Integer.parseInt(request.getParameter(REQUEST_PARAMETER_NAME_ID_PLANE)));
        flight.setPlane(plane);
        return flight;
    }
}
