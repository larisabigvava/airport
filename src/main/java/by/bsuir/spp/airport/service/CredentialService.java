package by.bsuir.spp.airport.service;

import by.bsuir.spp.airport.dao.ClientDao;
import by.bsuir.spp.airport.dao.CredentialsDao;
import by.bsuir.spp.airport.dao.DaoException;
import by.bsuir.spp.airport.entity.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Seagull on 18.05.2016.
 */
public class CredentialService extends BaseService {
    private CredentialsDao dao = factory.getCredentialDao();
    private static final String SESSION_ATTRIBUTE_NAME_USER = "user";
    private static final String SESSION_ATTRIBUTE_NAME_ROLE = "role";


    private static CredentialService instance = new CredentialService();

    private CredentialService(){}

    public static CredentialService getInstance() {
        return instance;
    }

    public String signIn(Credential credential, HttpServletRequest request) throws ServiceException{
        String page = null;
        Integer id = null;
        try{
            id = dao.findByCredentials(credential);
            if (id!=null){
                Client client = ClientService.getInstance().signIn(id);
                if (client!=null){
                    TicketService ticketService = TicketService.getInstance();
                    request.setAttribute("tickets",ticketService.findByClient(client.getId()));
                    request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_USER, client);
                    request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_ROLE, "client");
                    page = "user.jsp";
                } else {
                    Pilot pilot = PilotService.getInstance().signIn(id);
                    if (pilot != null){
                        request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_USER, pilot);
                        request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_ROLE, "pilot");
                        page = "pilot.jsp";
                    } else {
                        Administrator administrator = AdministratorService.getInstance().signIn(id);
                        if (administrator != null){
                            AirlineService service = AirlineService.getInstance();
                            request.setAttribute("airlines",service.findAll());
                            request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_USER, administrator);
                            request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_ROLE, "administrator");
                            page = "admin.jsp";
                        } else {
                            Airline airline = AirlineService.getInstance().signIn(id);
                            if (airline != null){
                                FlightService flightService = FlightService.getInstance();
                                PilotService pilotService = PilotService.getInstance();
                                request.setAttribute("flights",flightService.findByAirline(airline));
                                request.setAttribute("pilots",pilotService.findByAirline(airline));
                                request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_USER, airline);
                                request.getSession().setAttribute(SESSION_ATTRIBUTE_NAME_ROLE, "airline");
                                page = "airline.jsp";
                            }
                        }
                    }
                }
            }
        } catch (DaoException ex){
            throw new ServiceException(ex);
        }
        return page;
    }

    public boolean deleteByLogin(String login) throws ServiceException {
        boolean result = false;
        try {
            if (dao.deleteByLogin(login)){
                result = true;
            }
        }catch (DaoException ex){
            throw new ServiceException(ex);
        }
        return result;
    }
}
