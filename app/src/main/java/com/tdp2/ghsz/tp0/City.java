package com.tdp2.ghsz.tp0;

import java.io.Serializable;

public class City implements Serializable {
    public static final City VOID_CITY = new City(0, "", "");
    private int id;
    private String name = "VOID";
    private String country = "VOID";

    public boolean isVoid() { return this == VOID_CITY; }

    public static City getVoidCity() {
        return VOID_CITY;
    }

    public static City getBuenosAires() {
        return new City(3433955 , "Ciudad Autonoma de Buenos Aires" , "AR");
    }

    public City(int id) { this.id = id; }

    public City(int id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return name + ", " + country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (name != null ? !name.equals(city.name) : city.name != null) return false;
        return country != null ? country.equals(city.country) : city.country == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
