package by.bsuir.spp.airport.service;

/**
 * Created by Seagull on 21.04.2016.
 */
public class ServiceException extends Exception {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
