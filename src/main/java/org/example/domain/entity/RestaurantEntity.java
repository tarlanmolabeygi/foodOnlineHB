package org.example.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int zone;
    @Column(name = "delivery_cost")
    private int deliveryCost;

    @OneToMany(mappedBy="restaurantEntity",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<FoodEntity> foodsList;

    public RestaurantEntity(){

    }

    public RestaurantEntity(String name, int zone, int deliveryCost, Set<FoodEntity> foodsList) {
        this.name = name;
        this.zone = zone;
        this.deliveryCost = deliveryCost;
        this.foodsList = foodsList;
        setRestaurantInFoods();
    }

    private void setRestaurantInFoods(){
        for(FoodEntity foodEntity : foodsList){
            foodEntity.setRestaurantEntity(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(int deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Set<FoodEntity> getFoodsList() {
        return foodsList;
    }

    public void setFoodsList(Set<FoodEntity> foodsList) {
        this.foodsList = foodsList;
    }

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", zone=" + zone +
                ", deliveryCost=" + deliveryCost +
                ", foodsList=" + foodsList +
                '}';
    }
}
