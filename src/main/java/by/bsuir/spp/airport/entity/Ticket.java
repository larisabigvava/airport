package by.bsuir.spp.airport.entity;

/**
 * Created by Seagull on 02.05.2016.
 */
public class Ticket extends Entity {
    private int price;
    private Seat seat;
    private Client client;
    private Flight flight;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
