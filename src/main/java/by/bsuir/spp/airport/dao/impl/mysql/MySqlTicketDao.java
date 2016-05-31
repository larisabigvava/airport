package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.dao.ClientDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.TicketDao;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Client;
import by.bsuir.spp.airport.entity.Ticket;
import com.sun.org.apache.regexp.internal.RE;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Seagull on 21.05.2016.
 */
public class MySqlTicketDao implements TicketDao {
    private static final String SELECT_BY_CLIENT = "SELECT * FROM ticket WHERE id_client=?";
    private static final String INSERT = "INSERT INTO ticket(`id_seat`,`id_client`,`id_flight`,`price`) VALUES(?,?,?,?)";
    private static final String COLUMN_NAME_ID = "id_ticket";
    private static final String COLUMN_NAME_ID_FLIGHT = "id_flight";
    private static final String COLUMN_NAME_ID_CLIENT = "id_client";
    private static final String COLUMN_NAME_ID_SEAT = "id_seat";
    private static final String COLUMN_NAME_PRICE = "price";
    private static final String SELECT_BY_ID = "SELECT * FROM ticket WHERE id_ticket=?";


    private static MySqlTicketDao instance = new MySqlTicketDao();

    public static MySqlTicketDao getInstance() {
        return instance;
    }

    private MySqlTicketDao(){

    }

    @Override
    public Collection<Ticket> findByClient(Integer id) throws DaoException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_CLIENT);
                ){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                tickets.add(fillTicket(resultSet));
            }
        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return tickets;
    }

    @Override
    public boolean reserveTicket(Ticket ticket) throws DaoException {
        boolean result = false;
        try (
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT)
                ){
            statement.setInt(1,ticket.getSeat().getId());
            statement.setInt(2,ticket.getClient().getId());
            statement.setInt(3,ticket.getFlight().getId());
            statement.setInt(4,ticket.getPrice());
            if (statement.executeUpdate()==1){
                MySqlSeatDao seatDao = MySqlSeatDao.getInstance();
                seatDao.reserve(ticket.getSeat());
                result = true;
            }

        } catch (SQLException|NamingException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private Ticket fillTicket(ResultSet resultSet) throws SQLException, DaoException {
        Ticket ticket = new Ticket();
        ticket.setId(resultSet.getInt(COLUMN_NAME_ID));
        ticket.setPrice(resultSet.getInt(COLUMN_NAME_PRICE));
        MySqlClientDao clientDao = MySqlClientDao.getInstance();
        MySqlFlightDao flightDao = MySqlFlightDao.getInstance();
        MySqlSeatDao seatDao = MySqlSeatDao.getInstance();
        ticket.setClient(clientDao.findById(resultSet.getInt(COLUMN_NAME_ID_CLIENT)));
        ticket.setFlight(flightDao.findById(resultSet.getInt(COLUMN_NAME_ID_FLIGHT)));
        ticket.setSeat(seatDao.findById(resultSet.getInt(COLUMN_NAME_ID_SEAT)));
        return ticket;
    }

    @Override
    public Ticket findById(Integer id) throws DaoException {
        Ticket ticket = null;
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID);
        ){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                ticket = fillTicket(resultSet);
            }
        } catch (SQLException | NamingException e) {
           throw new DaoException(e);
        }
        return ticket;
    }

    @Override
    public Collection<Ticket> findAll() throws DaoException {
        return null;
    }

    @Override
    public Integer save(Ticket entity) throws DaoException {
        return null;
    }
}
