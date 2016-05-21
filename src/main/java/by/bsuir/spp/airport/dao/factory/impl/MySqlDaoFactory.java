package by.bsuir.spp.airport.dao.factory.impl;

import by.bsuir.spp.airport.dao.*;
import by.bsuir.spp.airport.dao.factory.DaoFactory;
import by.bsuir.spp.airport.dao.impl.mysql.*;

/**
 * Created by Seagull on 21.04.2016.
 */
public class MySqlDaoFactory extends DaoFactory {
    private static MySqlDaoFactory instance = new MySqlDaoFactory();

    private MySqlDaoFactory(){

    }

    public static MySqlDaoFactory getInstance() {
        return instance;
    }


    @Override
    public ClientDao getClientDao() {
        return MySqlClientDao.getInstance();
    }

    @Override
    public PilotDao getPilotDao() {
        return MySqlPilotDao.getInstance();
    }

    @Override
    public AirlineDao getAirlineDao() {
        return MySqlAirlineDao.getInstance();
    }

    @Override
    public CredentialsDao getCredentialDao() {
        return MySqlCredentialsDao.getInstance();
    }

    @Override
    public AdministratorDao getAdministratorDao() {
        return MySqlAdministratorDao.getInstance();
    }

    @Override
    public FlightDao getFlightDao() {
        return MySqlFlightDao.getInstance();
    }

    @Override
    public TicketDao getTicketDao() {
        return MySqlTicketDao.getInstance();
    }

    @Override
    public SeatDao getSeatDao() {
        return MySqlSeatDao.getInstance();
    }
}
