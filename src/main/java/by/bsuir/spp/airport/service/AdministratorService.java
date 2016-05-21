package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.AdministratorDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.entity.Administrator;

/**
 * Created by Seagull on 03.05.2016.
 */
public class AdministratorService extends BaseService {
    private static AdministratorService instance = new AdministratorService();
    private static AdministratorDao dao = factory.getAdministratorDao();

    private AdministratorService(){
    }

    public static AdministratorService getInstance() {
        return instance;
    }

    public Administrator signIn(Integer id) throws ServiceException{
        Administrator administrator = null;
        try {
            administrator = dao.findByCredentials(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return administrator;
    }
}
