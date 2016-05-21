package by.bsuir.spp.airport.entity;

/**
 * Created by Seagull on 02.05.2016.
 */
public class Seat extends Entity {
    private String place;
    private boolean free;
    private Flight flight;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
