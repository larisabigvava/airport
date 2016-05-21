package by.bsuir.spp.airport.entity;

/**
 * Created by Seagull on 18.05.2016.
 */
public class Credential extends Entity {
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
