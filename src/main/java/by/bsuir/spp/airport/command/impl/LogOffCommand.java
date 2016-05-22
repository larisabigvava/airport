package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Seagull on 22.05.2016.
 */
public class LogOffCommand implements BaseCommand{
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies){
            cookie.setValue("");
        }
        request.getSession().invalidate();
        return "index.jsp";
    }
}
