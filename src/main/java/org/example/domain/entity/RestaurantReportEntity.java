package org.example.domain.entity;

public class RestaurantReportEntity {
    private String name;
    private int zone;
    private long deliveryPrice;
    private String foodNameSell;

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

    public long getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(long deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getFoodNameSell() {
        return foodNameSell;
    }

    public void setFoodNameSell(String foodNameSell) {
        this.foodNameSell = foodNameSell;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", zone=" + zone +
                ", deliveryPrice=" + deliveryPrice +
                ", foodNameSell='" + foodNameSell + '\'' +
                '}';
    }
}
