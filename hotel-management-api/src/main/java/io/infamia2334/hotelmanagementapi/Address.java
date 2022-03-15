package io.infamia2334.hotelmanagementapi;

public class Address {
    private String city;
    private String country;


    public Address() {
    }


    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }


    public String getcity() {
        return this.city;
    }

    public void setcity(String city) {
        this.city = city;
    }

    public String getcountry() {
        return this.country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

}
