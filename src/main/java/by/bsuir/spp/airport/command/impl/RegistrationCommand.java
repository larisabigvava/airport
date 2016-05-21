package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.Client;
import by.bsuir.spp.airport.entity.Credential;
import by.bsuir.spp.airport.service.ClientService;
import by.bsuir.spp.airport.service.CredentialService;
import by.bsuir.spp.airport.service.ServiceException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 02.05.2016.
 */
public class RegistrationCommand implements BaseCommand {
    private static final String REQUEST_PARAMETER_NAME_LAST_NAME = "last_name";
    private static final String REQUEST_PARAMETER_NAME_FIRST_NAME = "first_name";
    private static final String REQUEST_PARAMETER_NAME_PATRONYMIC = "patronymic";
    private static final String REQUEST_PARAMETER_NAME_PASSPORT = "passport";
    private static final String REQUEST_PARAMETER_NAME_EMAIL = "email";
    private static final String REQUEST_PARAMETER_NAME_LOGIN = "login";
    private static final String REQUEST_PARAMETER_NAME_PASSWORD = "password_1";
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = "";
        try {
            Client client = validate(request);
            ClientService service = ClientService.getInstance();
            Boolean result = service.save(client);
            if (result){
                page = "index.jsp";
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }

    private Client validate(HttpServletRequest request){
        Client client = new Client();
        Credential credential = new Credential();
        client.setLastName(request.getParameter(REQUEST_PARAMETER_NAME_LAST_NAME));
        client.setFirstName(request.getParameter(REQUEST_PARAMETER_NAME_FIRST_NAME));
        client.setPatronymic(request.getParameter(REQUEST_PARAMETER_NAME_PATRONYMIC));
        client.setEmail(request.getParameter(REQUEST_PARAMETER_NAME_EMAIL));
        client.setPassport(request.getParameter(REQUEST_PARAMETER_NAME_PASSPORT));
        credential.setLogin(request.getParameter(REQUEST_PARAMETER_NAME_LOGIN));
        credential.setPassword(request.getParameter(REQUEST_PARAMETER_NAME_PASSWORD));
        client.setCredential(credential);
        return client;
    }
}
