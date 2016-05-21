package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.dao.AirlineDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Credential;
import org.omg.CORBA.DATA_CONVERSION;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class MySqlAirlineDao implements AirlineDao{
    private static final String SELECT_ALL = "SELECT * FROM airline";
    private static final String COLUMN_NAME_ID = "id_airline";
    private static final String COLUMN_NAME_NAME = "name";
    private static final String SELECT_BY_CREDENTIALS = "SELECT * FROM airline WHERE `credentials`=?";
    private static final String SELECT_BY_ID = "SELECT * FROM airline WHERE `id_airline`=?";
    private static final String INSERT = "INSERT INTO airline(`name`,`credentials`) VALUES(?,?)";
    private static final String DELETE = "DELETE FROM airline WHERE id_airline=?";

    private static MySqlAirlineDao instance = new MySqlAirlineDao();

    public static MySqlAirlineDao getInstance() {
        return instance;
    }

    private MySqlAirlineDao(){

    }

    @Override
    public Airline findById(Integer id) throws DaoException {
        Airline airline = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)
                ){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                airline = fillAirline(resultSet);
            }
        } catch (SQLException | NamingException ex){
            throw new DaoException(ex);
        }
        return airline;
    }

    @Override
    public Collection<Airline> findAll() throws DaoException {
        ArrayList<Airline> airlines = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                airlines.add(fillAirline(resultSet));
            }
        } catch (SQLException|NamingException ex){
            throw new DaoException(ex);
        }
        return airlines;
    }

    @Override
    public boolean delete(Integer id) throws DaoException {
        boolean result = false;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE)
                ){
            statement.setInt(1,id);
            if (statement.executeUpdate()==1){
                result = true;
            }
        } catch (SQLException | NamingException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    @Override
    public Integer save(Airline entity) throws DaoException {
        Integer id = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
        ){
            statement.setString(1, entity.getName());
            MySqlCredentialsDao credentialsDao = MySqlCredentialsDao.getInstance();
            statement.setInt(2, credentialsDao.save(entity.getCredential()));
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
    private Airline fillAirline(ResultSet resultSet) throws SQLException, DaoException{
        Airline airline = new Airline();
        airline.setId(resultSet.getInt(COLUMN_NAME_ID));
        airline.setName(resultSet.getString(COLUMN_NAME_NAME));
        MySqlCredentialsDao credentialsDao = MySqlCredentialsDao.getInstance();
        airline.setCredential(credentialsDao.findById(resultSet.getInt("credentials")));
        return airline;
    }
    public Airline findByCredentials(Integer credentialId) throws DaoException{
        Airline airline = null;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_CREDENTIALS)
        ){
            statement.setInt(1, credentialId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                    airline = fillAirline(resultSet);
            }
        } catch (SQLException |NamingException ex) {
            throw new DaoException(ex);
        }
        return airline;
    }
}
