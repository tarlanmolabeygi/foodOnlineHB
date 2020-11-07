package org.example.domain.entity;

import org.example.domain.enums.Category;

import javax.persistence.*;

@Entity
@Table(name = "food")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne()
    @JoinColumn(name="restaurant_id")
    private RestaurantEntity restaurantEntity;

    public FoodEntity(){

    }

    public FoodEntity(String name, int price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
        this.restaurantEntity = restaurantEntity;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
