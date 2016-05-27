package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.command.impl.ReserveTicketCommand;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.PilotDao;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Credential;
import by.bsuir.spp.airport.entity.Pilot;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Seagull on 02.05.2016.
 */
public class MySqlPilotDao implements PilotDao {
    private static final String SELECT_ALL = "SELECT * FROM pilot";
    private static final String SELECT_BY_ID = "SELECT * FROM pilot WHERE id_pilot=?";
    private static final String COLUMN_NAME_ID = "id_pilot";
    private static final String COLUMN_NAME_LAST_NAME = "last_name";
    private static final String COLUMN_NAME_FIRST_NAME = "first_name";
    private static final String COLUMN_NAME_PATRONYMIC = "patronymic";
    private static final String COLUMN_NAME_IIN = "iin";
    private static final String COLUMN_NAME_EXPERIENCE = "experience";
    private static final String COLUMN_NAME_AIRLINE_ID = "id_airline";
    private static final String COLUMN_NAME_CREDENTIAL = "credentials";
    private static final String SELECT_BY_CREDENTIALS = "SELECT * FROM pilot WHERE `credentials`=?";
    private static final String DELETE_BY_AIRLINE = "DELETE FROM pilot WHERE id_airline=?";
    private static final String DELETE_BY_ID = "DELETE FROM pilot WHERE id_pilot=?";
    private static final String SELECT_BY_AIRLINE = "SELECT * FROM  pilot WHERE id_airline=?";
    private static final String INSERT = "INSERT INTO pilot(last_name, first_name, patronymic, iin," +
            "experience, id_airline, credentials) VALUES(?,?,?, ?,?,?, ?)";
    private static final String UPDATE = "UPDATE pilot SET last_name=?, first_name=?, patronymic=?, iin=?," +
            "experience=?, id_airline=?, credentials=? WHERE id_pilot=?";
    private static MySqlPilotDao instance = new MySqlPilotDao();

    public static MySqlPilotDao getInstance() {
        return instance;
    }

    private MySqlPilotDao(){

    }
    @Override
    public Pilot findById(Integer id) throws DaoException {
        Pilot pilot = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)
        ){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                pilot = fillPilot(resultSet);
            }
        } catch (SQLException | NamingException ex){
            throw new  DaoException(ex);
        }
        return pilot;
    }

    @Override
    public Collection<Pilot> findByAirline(Airline airline) throws DaoException {
        ArrayList<Pilot> pilots = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_AIRLINE);
        ){
            statement.setInt(1,airline.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                pilots.add(fillPilot(resultSet));
            }

        } catch (SQLException|NamingException ex) {
            throw new DaoException(ex);
        }
        return pilots;
    }

    @Override
    public Collection<Pilot> findAll() throws DaoException {
        return null;
    }

    @Override
    public Integer save(Pilot entity) throws DaoException {
        Integer id = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
                ){
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getPatronymic());
            statement.setString(4, entity.getIin());
            statement.setInt(5, entity.getExperience());
            statement.setInt(6, entity.getAirline().getId());
            MySqlCredentialsDao credentialsDao = MySqlCredentialsDao.getInstance();
            statement.setInt(7,credentialsDao.save(entity.getCredential()));
            if (statement.executeUpdate() == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                id = resultSet.getInt(1);
            }
        } catch (SQLException | NamingException ex){
            throw new DaoException(ex);
        }
        return id;
    }

    @Override
    public Pilot findByCredentials(Integer credentialId) throws DaoException {
        Pilot pilot = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_CREDENTIALS)
                ){
            statement.setInt(1, credentialId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                pilot = fillPilot(resultSet);
            }
        } catch (SQLException | NamingException ex){
            throw new DaoException(ex);
        }
        return pilot;
    }

    @Override
    public Integer deleteByAirlineId(Integer id) throws DaoException {
        Integer rows;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_AIRLINE)
                ){
            statement.setInt(1,id);
            rows = statement.executeUpdate();
        } catch (SQLException |NamingException e) {
            throw new DaoException(e);
        }
        return rows;
    }

    @Override
    public Integer update(Pilot entity) throws DaoException {
        Integer id = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE)
        ){
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getPatronymic());
            statement.setString(4, entity.getIin());
            statement.setInt(5, entity.getExperience());
            statement.setInt(6, entity.getAirline().getId());
            MySqlCredentialsDao credentialsDao = MySqlCredentialsDao.getInstance();
            statement.setInt(7,credentialsDao.update(entity.getCredential()));
            statement.setInt(8,entity.getId());
            if (statement.executeUpdate() == 1){
                id = entity.getId();
            }
        } catch (SQLException | NamingException ex){
            throw new DaoException(ex);
        }
        return id;
    }

    @Override
    public Integer deleteById(Integer id) throws DaoException {
        Integer rows;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID)
        ){
            statement.setInt(1,id);
            rows = statement.executeUpdate();
        } catch (SQLException |NamingException e) {
            throw new DaoException(e);
        }
        return rows;
    }

    private Pilot fillPilot(ResultSet resultSet) throws SQLException, DaoException{
        Pilot pilot = new Pilot();
        pilot.setLastName(resultSet.getString(COLUMN_NAME_LAST_NAME));
        pilot.setFirstName(resultSet.getString(COLUMN_NAME_FIRST_NAME));
        pilot.setPatronymic(resultSet.getString(COLUMN_NAME_PATRONYMIC));
        pilot.setId(resultSet.getInt(COLUMN_NAME_ID));
        MySqlAirlineDao airlineDao = MySqlAirlineDao.getInstance();
        pilot.setAirline(airlineDao.findById(resultSet.getInt(COLUMN_NAME_AIRLINE_ID)));
        pilot.setExperience(resultSet.getInt(COLUMN_NAME_EXPERIENCE));
        pilot.setIin(resultSet.getString(COLUMN_NAME_IIN));
        MySqlCredentialsDao credentialsDao = MySqlCredentialsDao.getInstance();
        pilot.setCredential(credentialsDao.findById(resultSet.getInt(COLUMN_NAME_CREDENTIAL)));
        return  pilot;
    }
}
