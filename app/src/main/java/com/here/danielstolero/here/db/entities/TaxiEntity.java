package com.here.danielstolero.here.db.entities;

import com.here.danielstolero.here.models.Taxi;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "taxis")
public class TaxiEntity implements Taxi, Comparable {

    @PrimaryKey
    private int id;
    private String name;
    private int eta;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public TaxiEntity() {
    }

    @Ignore
    public TaxiEntity(int id, String name, int eta) {
        this.id = id;
        this.name = name;
        this.eta = eta;
    }

    public TaxiEntity(Taxi taxi) {
        this.id = taxi.getId();
        this.name = taxi.getName();
        this.eta = taxi.getEta();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        TaxiEntity taxi = (TaxiEntity) o;
        return Integer.compare(this.eta, taxi.eta);
    }
}
