package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.command.impl.ReserveTicketCommand;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.ClientDao;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Client;
import org.apache.poi.ss.formula.functions.Na;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Seagull on 21.04.2016.
 */
public class MySqlClientDao implements ClientDao {
    private static final String SELECT_ALL = "SELECT * FROM client";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE id_client = ?";
    private static final String COLUMN_NAME_ID = "id_client";
    private static final String COLUMN_NAME_LAST_NAME = "last_name";
    private static final String COLUMN_NAME_FIRST_NAME = "first_name";
    private static final String COLUMN_NAME_PATRONYMIC = "patronymic";
    private static final String COLUMN_NAME_PASSPORT = "passport";
    private static final String COLUMN_NAME_EMAIL = "email";
    private static final String COLUMN_NAME_CREDENTIALS = "credentials";
    private static final String SELECT_BY_CREDENTIAL = "SELECT * FROM client WHERE credentials=?";
    private static final String UPDATE_USER_INFO = "UPDATE `client` SET `last_name`=?, `first_name`=?," +
            "`patronymic`=?, `passport`=? WHERE `id_client`=?";
    private static final String INSERT_CLIENT_TO_CLIENTS = "INSERT INTO client(`last_name`,`first_name`,`patronymic`," +
            "`passport`,`email`,`credentials`) VALUES(?,?," +
            "?,?,?,?)";

    private static MySqlClientDao instance = new MySqlClientDao();

    public static MySqlClientDao getInstance() {
        return instance;
    }

    private MySqlClientDao(){

    }
    public Client findById(Integer id) throws DaoException {
        Client client = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)
                ){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                client = fillClient(resultSet);
            }
        } catch (SQLException | NamingException ex){
            throw new DaoException(ex);
        }
        return client;
    }

    public Collection<Client> findAll() throws DaoException {
        ArrayList<Client> clients = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                Statement statement = connection.createStatement();
                ){
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                clients.add(fillClient(resultSet));
            }

        } catch (SQLException|NamingException ex) {
            throw new DaoException(ex);
        }
        return clients;
    }

    public Integer save(Client entity) throws DaoException {
        Integer id = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_CLIENT_TO_CLIENTS, Statement.RETURN_GENERATED_KEYS)
                ){
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getPatronymic());
            statement.setString(4, entity.getPassport());
            statement.setString(5, entity.getEmail());
            MySqlCredentialsDao credentialsDao = MySqlCredentialsDao.getInstance();
            statement.setInt(6, credentialsDao.save(entity.getCredential()));
            if (statement.executeUpdate()==1){
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                id = resultSet.getInt(1);
            }
        } catch (SQLException|NamingException ex){
            throw new DaoException(ex);
        }
        return id;
    }

    public Client findByCredentials(Integer credentialId) throws DaoException{
        Client client=null;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_CREDENTIAL)
                ){
            statement.setInt(1, credentialId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                client = fillClient(resultSet);
            }
        } catch (SQLException|NamingException ex) {
            throw new DaoException(ex);
        }
        return client;
    }

    public Client updateUserInfo(Client client) throws DaoException{
        try (
            Connection connection = DatabaseUtil.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_INFO);
        ){
            statement.setString(1, client.getLastName());
            statement.setString(2, client.getFirstName());
            statement.setString(3, client.getPatronymic());
            statement.setString(4, client.getPassport());
            statement.setInt(5, client.getId());
            if(statement.executeUpdate() == 1){
                return client;
            }
        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return null;
    }

    private Client fillClient(ResultSet resultSet) throws SQLException, DaoException{
        Client client = new Client();
        client.setId(resultSet.getInt(COLUMN_NAME_ID));
        client.setLastName(resultSet.getString(COLUMN_NAME_LAST_NAME));
        client.setFirstName(resultSet.getString(COLUMN_NAME_FIRST_NAME));
        client.setPatronymic(resultSet.getString(COLUMN_NAME_PATRONYMIC));
        client.setPassport(resultSet.getString(COLUMN_NAME_PASSPORT));
        client.setEmail(resultSet.getString(COLUMN_NAME_EMAIL));
        return client;
    }

}
