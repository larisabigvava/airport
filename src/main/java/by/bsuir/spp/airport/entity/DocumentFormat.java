package by.bsuir.spp.airport.entity;

public enum DocumentFormat {
    XLSX(".xlsx"), CSV(".csv"), PDF(".pdf");

    private String format;

    DocumentFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
