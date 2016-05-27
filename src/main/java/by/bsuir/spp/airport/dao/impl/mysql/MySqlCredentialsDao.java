package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.dao.CredentialsDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Credential;

import javax.naming.NamingException;
import java.sql.*;
import java.util.Collection;

/**
 * Created by Seagull on 18.05.2016.
 */
public class MySqlCredentialsDao implements CredentialsDao{
    private static final String SELECT_ALL = "SELECT * FROM user_credential";
    private static final String SELECT_BY_ID = "SELECT * FROM user_credential WHERE id_user = ?";
    private static final String COLUMN_NAME_ID = "id_user";
    private static final String COLUMN_NAME_LOGIN = "login";
    private static final String COLUMN_NAME_PASSWORD = "password";
    private static final String SELECT_BY_LOGIN_AND_PASSWORD = "SELECT id_user FROM user_credential WHERE `login`=? AND `password`=?";
    private static final String INSERT_CLIENT_TO_CREDENTIALS = "INSERT INTO user_credential(`login`,`password`) VALUES(?,?)";
    private static final String UPDATE = "UPDATE user_credential SET `login`=?,`password`=? WHERE id_user=?";
    private static final String DELETE_BY_LOGIN = "DELETE FROM user_credential WHERE login=?";

    private static MySqlCredentialsDao instance = new MySqlCredentialsDao();

    public static MySqlCredentialsDao getInstance() {
        return instance;
    }

    private MySqlCredentialsDao(){

    }

    @Override
    public Integer findByCredentials(Credential credential) throws DaoException {
        Integer id = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_LOGIN_AND_PASSWORD)
                ){
            statement.setString(1, credential.getLogin());
            statement.setString(2, credential.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                id = resultSet.getInt(COLUMN_NAME_ID);
            }
        } catch(SQLException | NamingException ex){
            throw new DaoException(ex);
        }
        return id;
    }

    @Override
    public Credential findById(Integer id) throws DaoException {
        Credential credential = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)
                ){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                credential = fillCredentials(resultSet);
            }
        } catch (SQLException | NamingException ex){
            throw new  DaoException(ex);
        }
        return credential;
    }

    @Override
    public Collection<Credential> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean deleteByLogin(String login) throws DaoException {
        boolean result = false;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_LOGIN)
                ){
            statement.setString(1,login);
            if (statement.executeUpdate()==1){
                result = true;
            }
        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Integer save(Credential entity) throws DaoException {
        Integer id = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_CLIENT_TO_CREDENTIALS, Statement.RETURN_GENERATED_KEYS)
        ){
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
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

    public Integer update(Credential credential) throws DaoException{
        Integer id = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE)
                ){
            statement.setString(1, credential.getLogin());
            statement.setString(2, credential.getPassword());
            statement.setInt(3, credential.getId());
            statement.executeUpdate();
            id = findByCredentials(credential);
        } catch (SQLException|NamingException e){
            throw new DaoException(e);
        }
        return id;
    }

    private Credential fillCredentials(ResultSet resultSet) throws SQLException{
        Credential credential = new Credential();
        credential.setLogin(resultSet.getString("login"));
        credential.setPassword(resultSet.getString("password"));
        return credential;
    }
}
