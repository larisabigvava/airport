package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Credential;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.entity.Seat;
import by.bsuir.spp.airport.service.FlightService;
import by.bsuir.spp.airport.service.PilotService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 22.05.2016.
 */
public class EditPilotCommand implements BaseCommand {
    private static final String REQUEST_PARAMETER_NAME_LOGIN = "login";
    private static final String REQUEST_PARAMETER_NAME_PASSWORD = "password";
    private static final String REQUEST_PARAMETER_NAME_IIN = "iin";
    private static final String REQUEST_PARAMETER_NAME_EXPERIENCE = "experience";
    private static final String REQUEST_PARAMETER_NAME_LAST_NAME = "last_name";
    private static final String REQUEST_PARAMETER_NAME_FIRST_NAME = "first_name";
    private static final String REQUEST_PARAMETER_NAME_PATRONYMIC = "patronymic";
    private static final String SESSION_ATTRIBUTE_NAME_AIRLINE = "user";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Pilot pilot = validate(request);
        PilotService service = PilotService.getInstance();
        try{
            service.update(pilot);
            FlightService flightService = FlightService.getInstance();
            request.setAttribute("flights",flightService.findByAirline((Airline) request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE)));
            request.setAttribute("pilots",service.findByAirline((Airline) request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE)));

        }catch (ServiceException e){
            throw new CommandException(e);
        }
        return "airline.jsp";
    }

    private Pilot validate(HttpServletRequest request){
        Pilot pilot = new Pilot();
        pilot.setId(Integer.parseInt(request.getParameter("id")));
        pilot.setAirline((Airline) request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE));
        pilot.setFirstName(request.getParameter(REQUEST_PARAMETER_NAME_FIRST_NAME));
        pilot.setIin(request.getParameter(REQUEST_PARAMETER_NAME_IIN));
        pilot.setExperience(Integer.parseInt(request.getParameter(REQUEST_PARAMETER_NAME_EXPERIENCE)));
        pilot.setPatronymic(request.getParameter(REQUEST_PARAMETER_NAME_PATRONYMIC));
        pilot.setLastName(request.getParameter(REQUEST_PARAMETER_NAME_LAST_NAME));
        Credential credential = new Credential();
        credential.setLogin(request.getParameter(REQUEST_PARAMETER_NAME_LOGIN));
        credential.setPassword(request.getParameter(REQUEST_PARAMETER_NAME_PASSWORD));
        pilot.setCredential(credential);
        return pilot;
    }
}
