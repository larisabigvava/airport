package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Client;

/**
 * Created by Seagull on 21.04.2016.
 */
public interface ClientDao extends BaseDao<Client> {
    Client findByCredentials(Integer credentialId) throws DaoException;
    Client updateUserInfo(Client client) throws DaoException;
}
