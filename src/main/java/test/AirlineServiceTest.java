package test;

import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Credential;
import by.bsuir.spp.airport.service.AirlineService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Seagull on 27.05.2016.
 */
public class AirlineServiceTest extends ServiceTest {

    @Test
    public void testSignIn() throws Exception {
        Integer id = 28;
        AirlineService airlineService = AirlineService.getInstance();
        assertNotNull(airlineService.signIn(id));
    }

    @Test
    public void testFindAll() throws Exception {
        AirlineService airlineService = AirlineService.getInstance();
        assertFalse(airlineService.findAll().isEmpty());
    }

    @Test
    public void testSave() throws Exception {
        AirlineService airlineService = AirlineService.getInstance();
        Airline airline = new Airline();
        airline.setName("newAirline");
        Credential credential = new Credential();
        credential.setLogin("newAirline");
        credential.setPassword("newPassword");
        airline.setCredential(credential);
        assertTrue(airlineService.save(airline));
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 9;
        AirlineService airlineService = AirlineService.getInstance();
        assertTrue(airlineService.delete(id));
    }
}