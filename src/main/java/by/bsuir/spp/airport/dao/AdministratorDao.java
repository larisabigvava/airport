package by.bsuir.spp.airport.dao;

import by.bsuir.spp.airport.entity.Administrator;

import java.util.Collection;

/**
 * Created by Seagull on 03.05.2016.
 */
public interface AdministratorDao extends BaseDao<Administrator> {
    Administrator findByCredentials(Integer credentialId) throws DaoException;
}
