package by.bsuir.spp.airport.command.impl;

import by.bsuir.spp.airport.command.BaseCommand;
import by.bsuir.spp.airport.command.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 22.05.2016.
 */
public class DeleteFlightCommand implements BaseCommand {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}
