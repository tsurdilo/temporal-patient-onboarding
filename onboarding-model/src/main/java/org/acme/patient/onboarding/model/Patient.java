package org.acme.patient.onboarding.model;

public class Patient {
    private String id;
    private String name;
    private String age;
    private String zip;
    private String insurance;
    private String insuranceId;
    private String condition;
    private Hospital hospital;
    private Doctor doctor;
    private String onboarded;
    private String phone;
    private String email;
    private ContactMethod contactMethod = ContactMethod.TEXT;

    public Patient() {}

    public Patient(String id, String name, String age, String zip,
                   String insurance, String insuranceId, String condition, String email, String phone, String contactMethod) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.zip = zip;
        this.insurance = insurance;
        this.insuranceId = insuranceId;
        this.condition = condition;
        this.email = email;
        this.phone = phone;
        this.contactMethod = ContactMethod.valueOf(contactMethod);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getOnboarded() {
        return onboarded;
    }

    public void setOnboarded(String onboarded) {
        this.onboarded = onboarded;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactMethod getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(ContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", zip='" + zip + '\'' +
                ", insurance='" + insurance + '\'' +
                ", insuranceId='" + insuranceId + '\'' +
                ", condition='" + condition + '\'' +
                ", hospital=" + hospital +
                ", doctor=" + doctor +
                ", onboarded='" + onboarded + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", contactMethod=" + contactMethod +
                '}';
    }
}
