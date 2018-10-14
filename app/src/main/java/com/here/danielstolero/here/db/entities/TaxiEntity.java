package com.here.danielstolero.here.db.entities;

import com.here.danielstolero.here.models.Taxi;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "taxis")
public class TaxiEntity implements Taxi, Comparable<TaxiEntity> {

    @PrimaryKey
    private int id;
    private String name;
    private int eta;
    private int color;

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

    @Override
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Ignore
    public TaxiEntity(int id, String name, int eta, int color) {
        this.id = id;
        this.name = name;
        this.eta = eta;
        this.color = color;
    }

    public TaxiEntity(Taxi taxi) {
        this.id = taxi.getId();
        this.name = taxi.getName();
        this.eta = taxi.getEta();
        this.color = taxi.getColor();

    }

    @Override
    public int compareTo(@NonNull TaxiEntity taxi) {
        return Integer.compare(this.eta, taxi.eta);
    }
}
