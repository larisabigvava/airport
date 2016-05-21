package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Credential;
import by.bsuir.spp.airport.service.AirlineService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Seagull on 20.05.2016.
 */
public class AddAirlineCommand implements BaseCommand{
    private static final String REQUEST_PARAMETER_NAME_NAME = "name";
    private static final String REQUEST_PARAMETER_NAME_LOGIN = "login";
    private static final String REQUEST_PARAMETER_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Airline airline = validate(request);
        try {
            AirlineService service = AirlineService.getInstance();
            service.save(airline);
            ArrayList<Airline> airlines = service.findAll();
            request.setAttribute("airlines",airlines);
        }catch (ServiceException e) {
            e.printStackTrace();
        }
        return "admin.jsp";
    }

    private Airline validate(HttpServletRequest request){
        Airline airline = new Airline();
        airline.setName(request.getParameter(REQUEST_PARAMETER_NAME_NAME));
        Credential credential = new Credential();
        credential.setLogin(request.getParameter(REQUEST_PARAMETER_NAME_LOGIN));
        credential.setPassword(request.getParameter(REQUEST_PARAMETER_NAME_PASSWORD));
        airline.setCredential(credential);
        return airline;
    }
}
