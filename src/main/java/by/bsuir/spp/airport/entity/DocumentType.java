package by.bsuir.spp.airport.entity;

public enum DocumentType {
    TICKET("/tmp/ticket"),
    AIRLINE_FLIGHTS("/tmp/airline_flights"),
    AIRLINE_PILOTS("/tmp/airline_pilots"),
    SCHEDULE("/tmp/schedule"),
    AIRLINES("/tmp/airlines");

    private String path;

    DocumentType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
