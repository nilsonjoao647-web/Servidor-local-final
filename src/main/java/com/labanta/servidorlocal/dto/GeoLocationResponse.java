package com.labanta.servidorlocal.dto;

public class GeoLocationResponse {
    private String ip;
    private String city;
    private String region;
    private String country_name;

    public GeoLocationResponse() {
    }

    public String getIp() {return ip;}
    public String getCity() {return city;}
    public String getRegion() {return region;}
    public String getCountry_name() {return country_name;}

    public void setIp(String ip) {this.ip = ip;}
    public void setCity(String city) {this.city = city;}
    public void setRegion(String region) {this.region = region;}
    public void setCountry_name(String country_name) {this.country_name = country_name;}

}
