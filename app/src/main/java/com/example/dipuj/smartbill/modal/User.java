package com.example.dipuj.smartbill.modal;

public class User {

    private String userId;
    private String name;
    private String email;
    private String address;
    private String meterId;
    private String mobileNo;
    private String updated;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", meterId='" + meterId + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }

    public User(/*String userId, */String name, String email, String address, String meterId,
                String mobileNo, String updated) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.meterId = meterId;
        this.mobileNo = mobileNo;
        this.updated = updated;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
