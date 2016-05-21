package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.SeatDao;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.entity.Seat;

import javax.naming.NamingException;
import java.sql.*;
import java.util.Collection;

/**
 * Created by Seagull on 21.05.2016.
 */
public class MySqlSeatDao implements SeatDao {
    private static final String SELECT_BY_ID = "SELECT * FROM seat WHERE id_seat=?";
    private static final String UPDATE = "UPDATE  seat SET `free`=1 WHERE id_seat=?";
    private static final String FIND_FREE = "SELECT * FROM seat WHERE free=1 AND id_flight=?";
    private static final String COLUMN_NAME_ID = "id_seat";
    private static final String COLUMN_NAME_ID_FLIGHT = "id_flight";
    private static final String COLUMN_NAME_PLACE = "place";
    private static final String COLUMN_NAME_FREE = "free";

    private static MySqlSeatDao instance = new MySqlSeatDao();

    public static MySqlSeatDao getInstance() {
        return instance;
    }

    private MySqlSeatDao(){

    }
    @Override
    public Seat findById(Integer id) throws DaoException {
        Seat seat = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)
        ){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                seat = fillSeat(resultSet);
            }
        } catch (SQLException | NamingException ex){
            throw new  DaoException(ex);
        }
        return seat;
    }

    private Seat fillSeat(ResultSet resultSet) throws SQLException, DaoException {
        Seat seat = new Seat();
        seat.setId(resultSet.getInt(COLUMN_NAME_ID));
        MySqlFlightDao flightDao = MySqlFlightDao.getInstance();
        seat.setFlight(flightDao.findById(resultSet.getInt(COLUMN_NAME_ID_FLIGHT)));
        seat.setFree(resultSet.getBoolean(COLUMN_NAME_FREE));
        seat.setPlace(resultSet.getString(COLUMN_NAME_PLACE));
        return seat;
    }
    @Override
    public Collection<Seat> findAll() throws DaoException {
        return null;
    }

    @Override
    public boolean reserve(Seat seat) throws DaoException {
        boolean result = false;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE)
                ){
            statement.setInt(1,seat.getId());
            if (statement.executeUpdate()==1){
                result = true;
            }

        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return  result;
    }

    @Override
    public Seat findFree(Flight flight) throws DaoException {
        Seat seat = null;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_FREE)
                ){
            statement.setInt(1,flight.getId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                seat = fillSeat(resultSet);
            }
        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return seat;
    }

    @Override
    public Integer save(Seat entity) throws DaoException {
        return null;
    }
}
