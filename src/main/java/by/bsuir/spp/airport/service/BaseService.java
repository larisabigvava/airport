package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.factory.DaoFactory;

/**
 * Created by Seagull on 21.04.2016.
 */
abstract class BaseService {
    static DaoFactory factory = DaoFactory.takeDaoFactory();
}
