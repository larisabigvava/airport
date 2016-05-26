package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.service.PilotService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 26.05.2016.
 */
public class ToEditPilotCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        PilotService service = PilotService.getInstance();
        try{
            request.setAttribute("pilot", service.findById(Integer.parseInt(request.getParameter("id"))));
        } catch (ServiceException e){
            throw new CommandException(e);
        }
        return "pilotinfo.jsp";
    }
}
