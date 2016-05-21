package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Credential;

/**
 * Created by Seagull on 18.05.2016.
 */
public interface CredentialsDao extends BaseDao<Credential> {
    Integer findByCredentials(Credential credential) throws DaoException;
    boolean deleteByLogin(String login) throws DaoException;
}
