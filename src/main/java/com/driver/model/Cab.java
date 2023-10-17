package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name = "Cab")
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cabId;

   private int perKmRate;

   boolean available;

   @OneToOne
   @JoinColumn
   Driver driver;

    public Cab() {
    }

    public Cab( int perKmRate, boolean available) {

        this.perKmRate = perKmRate;
        this.available = available;
    }

    public int getCabId() {
        return cabId;
    }

    public void setCabId(int cabId) {
        this.cabId = cabId;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getPerKmRate() {
        return perKmRate;
    }

    public void setPerKmRate(int perKmRate) {
        this.perKmRate = perKmRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
