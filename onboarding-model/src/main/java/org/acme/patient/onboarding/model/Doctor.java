package org.acme.patient.onboarding.model;

public class Doctor {
    private String name;
    private String img;
    private String specialty;

    public Doctor() {}

    public Doctor(String name, String img, String specialty) {
        this.name = name;
        this.img = img;
        this.specialty = specialty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
