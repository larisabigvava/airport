package by.bsuir.spp.airport.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 21.04.2016.
 */
public interface BaseCommand {
    String execute(HttpServletRequest request) throws CommandException;
}
