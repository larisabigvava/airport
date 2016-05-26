package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.FlightDao;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Flight;

import javax.naming.NamingException;
import java.sql.*;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Seagull on 21.05.2016.
 */
public class MySqlFlightDao implements FlightDao {
    private static final String COLUMN_NAME_ID = "id_flight";
    private static final String COLUMN_NAME_ID_AIRLINE = "id_airline";
    private static final String COLUMN_NAME_ID_PLANE = "id_plane";
    private static final String COLUMN_NAME_ID_PILOT = "id_pilot";
    private static final String COLUMN_NAME_DEPARTURE_TIME = "departure_time";
    private static final String COLUMN_NAME_DEPARTURE_DATE = "departure_date";
    private static final String COLUMN_NAME_ARRIVAL_TIME = "arrival_time";
    private static final String COLUMN_NAME_ARRIVAL_DATE = "arrival_date";
    private static final String COLUMN_NAME_DESTINATION = "destination";
    private static final String COLUMN_NAME_FLIGHT_NUMBER = "flight_number";
    private static final String COLUMN_NAME_SEATS_COUNT = "seats_count";
    private static final String SELECT_BY_AIRLINE = "SELECT * FROM  flight WHERE id_airline=?";
    private static final String SELECT_ALL = "SELECT * FROM  flight";
    private static final String INSERT = "INSERT INTO flight(`id_plane`,`id_pilot`,`departure_time`,`departure_date`,`arrival_time`,`arrival_date`,`destination`,`flight_number`," +
            "`seats_count`,`id_airline`) VALUES(?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE flight SET `id_plane`=?,`id_pilot`=?,`departure_time`=?," +
            "`departure_date`=?,`arrival_time`=?,`arrival_date`=?,`destination`=?,`flight_number`=?,`id_airline`=? " +
            "WHERE `id_flight`=?";
    private static final String SELECT_BY_ID = "SELECT * FROM  flight WHERE id_flight=?";
    private static final String SELECT_BY_DESTINATION = "SELECT * FROM  flight WHERE destination = ?";
    private static final String SELECT_BY_DEPARTURE_DATE = "SELECT * FROM  flight WHERE departure_date = ?";
    private static MySqlFlightDao instance = new MySqlFlightDao();

    public static MySqlFlightDao getInstance() {
        return instance;
    }

    private MySqlFlightDao(){

    }
    @Override
    public Flight findById(Integer id) throws DaoException {
        Flight flight = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)
        ){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                flight = fillFlight(resultSet);
            }
        } catch (SQLException | NamingException ex){
            throw new  DaoException(ex);
        }
        return flight;
    }

    @Override
    public Collection<Flight> findByDestination(String destination) throws DaoException {
        ArrayList<Flight> flights = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_DESTINATION)
                ){
            statement.setString(1, destination);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                flights.add(fillFlight(resultSet));
            }
        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return  flights;
    }

    @Override
    public Collection<Flight> findByDepartureDate(Date departureDate) throws DaoException {
        ArrayList<Flight> flights = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_DEPARTURE_DATE)
        ){
            statement.setDate(1, departureDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                flights.add(fillFlight(resultSet));
            }
        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return  flights;
    }


    @Override
    public Collection<Flight> findAll() throws DaoException {
        ArrayList<Flight> flights = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                Statement statement = connection.createStatement()
                ){
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                flights.add(fillFlight(resultSet));
            }
        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return flights;
    }

    @Override
    public Integer save(Flight entity) throws DaoException {
        Integer id = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)
        ){
            statement.setInt(1, entity.getPlane().getId());
            statement.setInt(2, entity.getPilot().getId());
            statement.setTime(3, entity.getDepartureTime());
            statement.setDate(4, entity.getDepartureDate());
            statement.setTime(5, entity.getArrivalTime());
            statement.setDate(6, entity.getArrivalDate());
            statement.setString(7, entity.getDestination());
            statement.setString(8, entity.getFlightNumber());
            statement.setInt(9, entity.getSeatsCount());
            statement.setInt(10, entity.getAirline().getId());
            if (statement.executeUpdate()==1){
                ResultSet resultSet = statement.getGeneratedKeys();
                resultSet.next();
                id = resultSet.getInt(1);
            }
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Collection<Flight> findByAirline(Airline airline) throws DaoException {
        ArrayList<Flight> flights = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_AIRLINE);
        ){
            statement.setInt(1,airline.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                flights.add(fillFlight(resultSet));
            }

        } catch (SQLException |NamingException ex) {
            throw new DaoException(ex);
        }
        return flights;
    }

    private Flight fillFlight(ResultSet resultSet) throws SQLException, DaoException {
        Flight flight = new Flight();
        flight.setId(resultSet.getInt(COLUMN_NAME_ID));
        flight.setArrivalDate(resultSet.getDate(COLUMN_NAME_ARRIVAL_DATE));
        flight.setArrivalTime(resultSet.getTime(COLUMN_NAME_ARRIVAL_TIME));
        flight.setDepartureDate(resultSet.getDate(COLUMN_NAME_DEPARTURE_DATE));
        flight.setDepartureTime(resultSet.getTime(COLUMN_NAME_DEPARTURE_TIME));
        flight.setFlightNumber(resultSet.getString(COLUMN_NAME_FLIGHT_NUMBER));
        MySqlPilotDao pilotDao = MySqlPilotDao.getInstance();
        flight.setPilot(pilotDao.findById(resultSet.getInt(COLUMN_NAME_ID_PILOT)));
        flight.setDestination(resultSet.getString(COLUMN_NAME_DESTINATION));
        flight.setSeatsCount(resultSet.getInt(COLUMN_NAME_SEATS_COUNT));
        MySqlPlaneDao planeDao = MySqlPlaneDao.getInstance();
        flight.setPlane(planeDao.findById(resultSet.getInt(COLUMN_NAME_ID_PLANE)));
        MySqlAirlineDao airlineDao = MySqlAirlineDao.getInstance();
        flight.setAirline(airlineDao.findById(resultSet.getInt(COLUMN_NAME_ID_AIRLINE)));
        return flight;
    }

    @Override
    public Integer update(Flight entity) throws DaoException {
        Integer id = null;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE)
                ){
            statement.setInt(1, entity.getPlane().getId());
            statement.setInt(2, entity.getPilot().getId());
            statement.setTime(3, entity.getDepartureTime());
            statement.setDate(4, entity.getDepartureDate());
            statement.setTime(5, entity.getArrivalTime());
            statement.setDate(6, entity.getArrivalDate());
            statement.setString(7, entity.getDestination());
            statement.setString(8, entity.getFlightNumber());
            statement.setInt(9, entity.getAirline().getId());
            statement.setInt(10, entity.getId());
            if (statement.executeUpdate()==1){
                id = entity.getId();
            }
        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return id;
    }
}
