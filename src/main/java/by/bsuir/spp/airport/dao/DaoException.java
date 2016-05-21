package by.bsuir.spp.airport.dao;

/**
 * Created by Seagull on 21.04.2016.
 */
public class DaoException extends Exception {
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoException(Throwable cause) {
        super(cause);
    }
}
