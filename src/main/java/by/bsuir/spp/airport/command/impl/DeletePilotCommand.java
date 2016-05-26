package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.service.CredentialService;
import by.bsuir.spp.airport.service.PilotService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Seagull on 22.05.2016.
 */
public class DeletePilotCommand implements BaseCommand{
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        try {
            PilotService service = PilotService.getInstance();
            if (service.delete(Integer.parseInt(request.getParameter("id")))){
                CredentialService credentialService = CredentialService.getInstance();
                credentialService.deleteByLogin(request.getParameter("login"));
            }
            ArrayList<Pilot> pilots = service.findByAirline((Airline) request.getSession().getAttribute("user"));
            request.setAttribute("pilots",pilots);
        }catch (ServiceException e) {
            throw new CommandException(e);
        }
        return "admin.jsp";
    }
}
