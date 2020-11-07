package org.example.domain.entity;

public class UserReportEntity {
    private String mobile;
    private String name;
    private int month;
    private int avrragePrice;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getAvrragePrice() {
        return avrragePrice;
    }

    public void setAvrragePrice(int avrragePrice) {
        this.avrragePrice = avrragePrice;
    }

    @Override
    public String toString() {
        return "{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", month=" + month +
                ", totalPrice=" + avrragePrice +
                '}';
    }
}
