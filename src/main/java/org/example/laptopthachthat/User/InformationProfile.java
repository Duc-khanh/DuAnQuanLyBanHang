package org.example.laptopthachthat.User;

public class InformationProfile {
    private String username;
    private String password;
    private String address;
    private String phoneNumber;

    public void InformationProfile(String username, String password, String address, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public void setPassword(String password) {
        this.password = password;}
}

