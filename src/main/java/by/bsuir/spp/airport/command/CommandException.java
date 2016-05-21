package by.bsuir.spp.airport.command;

/**
 * Created by Seagull on 21.04.2016.
 */
public class CommandException extends Exception {
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}
