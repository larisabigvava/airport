package by.bsuir.spp.airport.dao.impl.mysql;

import by.bsuir.spp.airport.dao.BaseDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.dao.PlaneDao;
import by.bsuir.spp.airport.dao.util.DatabaseUtil;
import by.bsuir.spp.airport.entity.Plane;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Seagull on 21.05.2016.
 */
public class MySqlPlaneDao implements PlaneDao {
    private static final String SELECT_BY_ID = "SELECT * FROM plane WHERE id_plane=?";
    private static final String COLUMN_NAME_ID = "id_plane";
    private static final String COLUMN_NAME_ID_AIRLINE = "id_airline";
    private static final String COLUMN_NAME_PRIVATE_NUMBER = "private_number";
    private static final String COLUMN_NAME_MODEL = "model";
    private static final String COLUMN_NAME_SEAT_COUNT = "seat_count";
    private static MySqlPlaneDao instance = new MySqlPlaneDao();

    public static MySqlPlaneDao getInstance() {
        return instance;
    }

    private MySqlPlaneDao(){

    }
    @Override
    public Plane findById(Integer id) throws DaoException {
        Plane plane = null;
        try(
                Connection connection = DatabaseUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)
        ){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                plane = fillPlane(resultSet);
            }
        } catch (SQLException | NamingException ex){
            throw new  DaoException(ex);
        }
        return plane;
    }

    private Plane fillPlane(ResultSet resultSet) throws SQLException, DaoException {
        Plane plane = new Plane();
        plane.setId(resultSet.getInt(COLUMN_NAME_ID));
        plane.setModel(resultSet.getString(COLUMN_NAME_MODEL));
        plane.setPrivateNumber(resultSet.getString(COLUMN_NAME_PRIVATE_NUMBER));
        plane.setSeatsCount(resultSet.getInt(COLUMN_NAME_SEAT_COUNT));
        MySqlAirlineDao airlineDao = MySqlAirlineDao.getInstance();
        plane.setAirline(airlineDao.findById(resultSet.getInt(COLUMN_NAME_ID_AIRLINE)));
        return plane;
    }

    @Override
    public Collection<Plane> findAll() throws DaoException {
        return null;
    }

    @Override
    public Integer save(Plane entity) throws DaoException {
        return null;
    }
}
