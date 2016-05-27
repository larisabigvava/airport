package test;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.BeforeClass;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by Seagull on 26.05.2016.
 */
public abstract class ServiceTest {
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
}
