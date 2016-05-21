package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.service.AirlineService;
import by.bsuir.spp.airport.service.CredentialService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Seagull on 20.05.2016.
 */
public class DeleteAirlineCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            AirlineService service = AirlineService.getInstance();
            if (service.delete(Integer.parseInt(request.getParameter("id")))){
                CredentialService credentialService = CredentialService.getInstance();
                credentialService.deleteByLogin(request.getParameter("login"));
            }
            ArrayList<Airline> airlines = service.findAll();
            request.setAttribute("airlines",airlines);
        }catch (ServiceException e) {
            e.printStackTrace();
        }
        return "admin.jsp";
    }
}
