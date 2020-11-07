package org.example.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
@Table(name = "cart")
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long timestamp;

    @OneToOne()
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy="cartEntity",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CartFood> cartFoodsList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public RestaurantEntity getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantEntity restaurant) {
        this.restaurant = restaurant;
    }

    public Set<CartFood> getCartFoodsList() {
        return cartFoodsList;
    }

    public void setCartFoodsList(Set<CartFood> cartFoodsList) {
        this.cartFoodsList = cartFoodsList;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", restaurant=" + restaurant +
                ", cartFoodsList=" + cartFoodsList +
                '}';
    }
}
