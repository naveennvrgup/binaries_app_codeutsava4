package binaries.app.codeutsava.restapi.model.farmer;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class FarmerDetailResponse implements Serializable {
    @Expose
    private String name;
    @Expose
    private String contact;
    @Expose
    private String address;
    @Expose
    private String city;
    @Expose
    private String state;

    @Override
    public String toString() {
        return "FarmerDetailResponse{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", dob='" + dob + '\'' +
                ", adhaar='" + adhaar + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAdhaar() {
        return adhaar;
    }

    public void setAdhaar(String adhaar) {
        this.adhaar = adhaar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Expose
    private String dob;
    @Expose
    private String adhaar;
    @Expose
    private String role;
}
