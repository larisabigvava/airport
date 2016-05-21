package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Client;
import by.bsuir.spp.airport.service.ClientService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 02.05.2016.
 */
public class SaveUserChangesCommand implements BaseCommand {
    private static final String SESSION_ATTRIBUTE_NAME_CLIENT = "client";
    private static final String REQUEST_PARAMETER_NAME_LAST_NAME = "last_name";
    private static final String REQUEST_PARAMETER_NAME_FIRST_NAME = "first_name";
    private static final String REQUEST_PARAMETER_NAME_PATRONYMIC = "patronymic";
    private static final String REQUEST_PARAMETER_NAME_PASSPORT = "passport";
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        ClientService service = ClientService.getInstance();
        try {

            Client client = (Client) request.getSession().getAttribute(SESSION_ATTRIBUTE_NAME_CLIENT);
            client.setLastName(request.getParameter(REQUEST_PARAMETER_NAME_LAST_NAME));
            client.setFirstName(request.getParameter(REQUEST_PARAMETER_NAME_FIRST_NAME));
            client.setPatronymic(request.getParameter(REQUEST_PARAMETER_NAME_PATRONYMIC));
            client.setPassport(request.getParameter(REQUEST_PARAMETER_NAME_PASSPORT));
            client = service.updateClient(client);
            request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_CLIENT, client);
        } catch (ServiceException e){
            throw new CommandException(e);
        }
            return "user.jsp";
    }
}
