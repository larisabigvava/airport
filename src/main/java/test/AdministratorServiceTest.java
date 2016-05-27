package test;

import by.bsuir.spp.airport.service.AdministratorService;
import by.bsuir.spp.airport.service.ServiceException;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Seagull on 26.05.2016.
 */
public class AdministratorServiceTest extends ServiceTest {
    @Test
    public void testSignIn() throws ServiceException {
        Integer id = 1;
        AdministratorService service = AdministratorService.getInstance();
        assertNotNull(service.signIn(id));
    }
}