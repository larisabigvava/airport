package test;

import by.bsuir.spp.airport.entity.Airline;
import by.bsuir.spp.airport.entity.Credential;
import by.bsuir.spp.airport.entity.Pilot;
import by.bsuir.spp.airport.service.PilotService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Seagull on 27.05.2016.
 */
public class PilotServiceTest extends ServiceTest {

    @Test
    public void testSignIn() throws Exception {
        Integer id = 6;
        PilotService pilotService = PilotService.getInstance();
        assertNotNull(pilotService.signIn(id));
    }

    @Test
    public void testDeleteByAirline() throws Exception {
        Integer id = 6;
        PilotService pilotService = PilotService.getInstance();
        assertNotNull(pilotService.deleteByAirline(id));
    }

    @Test
    public void testFindByAirline() throws Exception {
        Airline airline = new Airline();
        Integer id = 6;
        airline.setId(id);
        PilotService pilotService = PilotService.getInstance();
        assertNotNull(pilotService.findByAirline(airline));
    }

    @Test
    public void testSave() throws Exception {
        PilotService pilotService = PilotService.getInstance();
        Pilot pilot = new Pilot();
        pilot.setLastName("newPilotskiy");
        pilot.setFirstName("newPilot");
        pilot.setPatronymic("newPilotovich");
        pilot.setExperience(1);
        pilot.setIin("1234567");
        Credential credential = new Credential();
        credential.setLogin("newPilot");
        credential.setPassword("newPass");
        pilot.setCredential(credential);
        assertTrue(pilotService.save(pilot));

    }

    @Test
    public void testDelete() throws Exception {
        PilotService pilotService = PilotService.getInstance();
        Integer id = 6;
        assertTrue(pilotService.delete(id));
    }

    @Test
    public void testFindById() throws Exception {
        Integer id = 6;
        PilotService pilotService = PilotService.getInstance();
        assertNotNull(pilotService.findById(id));
    }

    @Test
    public void testUpdate() throws Exception {
        PilotService pilotService = PilotService.getInstance();
        Pilot pilot = new Pilot();
        Integer id = 6;
        pilot.setId(id);
        pilot.setLastName("newPilotskiy");
        pilot.setFirstName("newPilot");
        pilot.setPatronymic("newPilotovich");
        pilot.setExperience(1);
        pilot.setIin("1234567");
        Credential credential = new Credential();
        credential.setLogin("newPilot");
        credential.setPassword("newPass");
        pilot.setCredential(credential);
        assertTrue(pilotService.update(pilot));
    }
}