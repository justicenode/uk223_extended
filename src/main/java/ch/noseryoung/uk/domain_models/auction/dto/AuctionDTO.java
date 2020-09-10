package ch.noseryoung.uk.domain_models.auction.dto;

import java.util.Calendar;

public class AuctionDTO {
    private int id;
    private double price;
    private Calendar endDate;
    private String name;

    // Empty default constructor
    public AuctionDTO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
