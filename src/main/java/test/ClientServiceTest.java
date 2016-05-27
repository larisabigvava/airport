package test;

import by.bsuir.spp.airport.entity.Client;
import by.bsuir.spp.airport.entity.Credential;
import by.bsuir.spp.airport.service.ClientService;
import by.bsuir.spp.airport.service.ServiceException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Seagull on 01.05.2016.
 */

public class ClientServiceTest extends ServiceTest {
    @Test
    public void testFindAll() throws ServiceException {
        ClientService service = ClientService.getInstance();
        assertFalse(service.findAll().isEmpty());
    }

    @Test
    public void testSignIn() throws ServiceException {
        Integer id = 15;
        ClientService service = ClientService.getInstance();
        assertNotNull(service.signIn(id));
    }

    @Test
    public void testSave() throws ServiceException {
        Client client = new Client();
        client.setFirstName("newClient");
        client.setLastName("newClientniy");
        client.setPatronymic("newClientovich");
        client.setPassport("BM1982365");
        client.setEmail("newclient@mail.ru");
        Credential credential = new Credential();
        credential.setLogin("newClient");
        credential.setPassword("newPassword");
        client.setCredential(credential);
        ClientService service = ClientService.getInstance();
        assertTrue(service.save(client));
    }
}
