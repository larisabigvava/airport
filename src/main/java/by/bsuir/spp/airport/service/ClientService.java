package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.ClientDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.entity.Client;

import java.util.ArrayList;

/**
 * Created by Seagull on 21.04.2016.
 */
public class ClientService extends BaseService {
    private static ClientService instance = new ClientService();
    private static ClientDao dao = factory.getClientDao();

    private ClientService(){
    }

    public static ClientService getInstance() {
        return instance;
    }

    public ArrayList<Client> findAll() throws ServiceException{
        ArrayList<Client> clients;
        try {
            clients = new ArrayList<>(dao.findAll());
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return clients;
    }

    public Client signIn(Integer id) throws ServiceException{
        Client client = null;
        try {
            client = dao.findByCredentials(id);
        } catch (DaoException e){
            throw new ServiceException(e);
        }
        return client;
    }

    public Client updateClient(Client client) throws ServiceException{
        try {
            client = dao.updateUserInfo(client);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return client;
    }

    public boolean save(Client client) throws ServiceException{
        try {
            if (dao.save(client)!=null){
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return false;
    }
}
