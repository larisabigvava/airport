package test;

import by.bsuir.spp.airport.service.ClientService;
import by.bsuir.spp.airport.service.ServiceException;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.junit.Assert.*;

/**
 * Created by Seagull on 01.05.2016.
 */

public class ClientServiceTest {

    @BeforeClass
    public static void initializeConnectionPool()throws NamingException {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/airport");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
        System.setProperty(Context.URL_PKG_PREFIXES,"org.apache.naming");
        Context initContext = new InitialContext();
        initContext.createSubcontext("java:");
        initContext.createSubcontext("java:comp");
        initContext.createSubcontext("java:comp/env");
        initContext.createSubcontext("java:comp/env/jdbc");
        initContext.bind("java:comp/env/jdbc/airport",dataSource);
    }

    @Test
    public void testFindAll() throws ServiceException {
        ClientService service = ClientService.getInstance();
        assertFalse(service.findAll().isEmpty());
    }
}
