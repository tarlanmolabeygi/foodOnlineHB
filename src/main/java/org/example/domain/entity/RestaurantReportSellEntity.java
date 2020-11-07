package org.example.domain.entity;

public class RestaurantReportSellEntity {
    private String name;
    private int zone;
    private String foodName;
    private long totalPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", zone='" + zone + '\'' +
                ", foodName='" + foodName + '\'' +
                ", price='" + totalPrice + '\'' +
                '}';
    }
}
