package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;
import by.bsuir.spp.airport.entity.*;
import by.bsuir.spp.airport.service.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 02.05.2016.
 */
public class SignInCommand implements BaseCommand {
    private static final String REQUEST_PARAMETER_NAME_LOGIN = "login";
    private static final String REQUEST_PARAMETER_NAME_PASSWORD = "password";
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = "index.jsp";
        try {
            Credential credential = validate(request);
            CredentialService service = CredentialService.getInstance();
            String result = service.signIn(credential, request);
            if (result != null){
                page = result;
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return page;
    }

    private Credential validate(HttpServletRequest request){
        Credential credential = new Credential();
        String login="";
        String password="";
        if (request.getCookies().length != 0) {
            Cookie[] dataSaved = new Cookie[request.getCookies().length];
            dataSaved = request.getCookies();
            Cookie cookie;
            for (int i = 0; i < request.getCookies().length; i++) {
                cookie = dataSaved[i];
                if (cookie.getName().equals("login")) {
                    login = cookie.getValue();
                } else if (cookie.getName().equals("password")) {
                    password = cookie.getValue();
                }
            }
        }
        credential.setLogin(login);
        credential.setPassword(password);
//        credential.setLogin(request.getParameter(REQUEST_PARAMETER_NAME_LOGIN));
//        credential.setPassword(request.getParameter(REQUEST_PARAMETER_NAME_PASSWORD));
        return credential;
    }
}
