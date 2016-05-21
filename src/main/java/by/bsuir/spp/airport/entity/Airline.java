package by.bsuir.spp.airport.entity;

/**
 * Created by Seagull on 02.05.2016.
 */
public class Airline extends Entity {
    private String name;
    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    private Credential credential;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
