package com.deb.vid4u;

public class paymentdetails {
    private String filename ,currency,email,phone;
    private Integer amount;

    public paymentdetails(String filename, Integer amount, String currency, String email, String phone) {
        this.filename = filename;
        this.amount = amount;
        this.currency = currency;
        this.email = email;
        this.phone = phone;
    }

    public paymentdetails() {

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
