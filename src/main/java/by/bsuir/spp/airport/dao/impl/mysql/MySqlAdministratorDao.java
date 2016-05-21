package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.dao.AdministratorDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Administrator;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Seagull on 03.05.2016.
 */
public class MySqlAdministratorDao implements AdministratorDao {
    private static final String SELECT_ALL = "SELECT * FROM `administrator`";
    private static final String COLUMN_NAME_ID = "id_admin";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String SELECT_BY_CREDENTIALS = "SELECT * FROM administrator WHERE `credentials`=?";

    private static MySqlAdministratorDao instance = new MySqlAdministratorDao();

    public static MySqlAdministratorDao getInstance() {
        return instance;
    }

    private MySqlAdministratorDao(){

    }
    @Override
    public Administrator findById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public Collection<Administrator> findAll() throws DaoException {
        return null;
    }

    @Override
    public Integer save(Administrator entity) throws DaoException {
        return null;
    }

    private Administrator fillAdministrator(ResultSet resultSet) throws SQLException, DaoException{
        Administrator administrator = new Administrator();
        administrator.setId(resultSet.getInt(COLUMN_NAME_ID));
        administrator.setName(resultSet.getString(COLUMN_NAME_NAME));
        return administrator;
    }
    public Administrator findByCredentials(Integer credentialId) throws DaoException{
        Administrator administrator = null;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_CREDENTIALS)
        ){
            statement.setInt(1, credentialId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                administrator = fillAdministrator(resultSet);
            }
        } catch (SQLException |NamingException ex) {
            throw new DaoException(ex);
        }
        return administrator;
    }
}
