package org.acme.patient.onboarding.model;

public class Hospital {
    private String name;
    private String address;
    private String phone;
    private String zip;

    public Hospital() {}

    public Hospital(String name, String address, String phone, String zip) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
