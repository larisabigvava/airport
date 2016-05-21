package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Credential;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.service.FlightService;
import by.bsuir.spp.airport.service.PilotService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * Created by Seagull on 21.05.2016.
 */
public class AddPilotCommand implements BaseCommand {
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
        String page = "";
        Pilot pilot = validate(request);
        PilotService service = PilotService.getInstance();
        try{
            boolean result = service.save(pilot);
            if(result){
                FlightService flightService = FlightService.getInstance();
                request.setAttribute("flights",flightService.findByAirline(
                        (Airline)request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE)
                ));
                request.setAttribute("pilots",service.findByAirline(
                        (Airline)request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_AIRLINE)
                ));
                page = "airline.jsp";
            }
        } catch (ServiceException ex){
            throw new CommandException(ex);
        }
        return page;
    }

    private Pilot validate(HttpServletRequest request){
        Pilot pilot = new Pilot();
        pilot.setAirline((Airline) request.getSession().getAttribute("user"));
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
