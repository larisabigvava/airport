package test;

import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Flight;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.entity.Plane;
import by.bsuir.spp.airport.service.FlightService;
import org.junit.Test;

import java.sql.Date;
import java.sql.Time;

import static org.junit.Assert.*;

/**
 * Created by Seagull on 27.05.2016.
 */
public class FlightServiceTest extends ServiceTest {

    @Test
    public void testFindByAirline() throws Exception {
        FlightService flightService = FlightService.getInstance();
        Airline airline = new Airline();
        Integer id = 6;
        airline.setId(6);
        assertNotNull(flightService.findByAirline(airline));
    }

    @Test
    public void testFindAll() throws Exception {
        FlightService flightService = FlightService.getInstance();
        assertNotNull(flightService.findAll());
    }

    @Test
    public void testFindByDestination() throws Exception {
        FlightService flightService = FlightService.getInstance();
        String destination = "Moscow";
        assertNotNull(flightService.findByDestination(destination));
    }

    @Test
    public void testFindByDepartureDate() throws Exception {
        FlightService flightService = FlightService.getInstance();
        Date date = Date.valueOf("2016-05-01");
        assertNotNull(flightService.findByDepartureDate(date));
    }

    @Test
    public void testUpdate() throws Exception {
        FlightService service = FlightService.getInstance();
        Flight flight = new Flight();
        Airline airline = new Airline();
        airline.setId(6);
        Pilot pilot = new Pilot();
        pilot.setId(6);
        Plane plane = new Plane();
        plane.setId(1);
        flight.setId(1);
        flight.setAirline(airline);
        flight.setPlane(plane);
        flight.setPilot(pilot);
        flight.setDestination("Moscow");
        flight.setFlightNumber("1000");
        Date date = Date.valueOf("2016-05-01");
        flight.setDepartureDate(date);
        Time time = Time.valueOf("13:00:00");
        flight.setDepartureTime(time);
        Date dateArrival = Date.valueOf("2016-05-01");
        flight.setArrivalDate(dateArrival);
        Time timeArrival = Time.valueOf("18:00:00");;
        flight.setArrivalTime(timeArrival);
        flight.setSeatsCount(50);
        assertTrue(service.update(flight));
    }
}