package ch.noseryoung.uk.domain_models.auction;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Calendar;

@Entity
public class Auction {
    @Id
    private int id;
    private double price;
    private Calendar endDate;
    private String name;

    public double getPrice() {
        return price;
    }

    public Auction setPrice(double price) {
        this.price = price;
        return this;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public Auction setEndDate(Calendar endDate) {
        this.endDate = endDate;
        return this;
    }

    public int getId() {
        return id;
    }

    public Auction setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Auction setName(String name) {
        this.name = name;
        return this;
    }
}
