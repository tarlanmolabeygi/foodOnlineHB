package org.example.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "cart_food")
public class CartFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int count;

    @ManyToOne()
    @JoinColumn(name="cart_id")
    private CartEntity cartEntity;

    @ManyToOne()
    @JoinColumn(name="food_id")
    private FoodEntity food;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public FoodEntity getFood() {
        return food;
    }

    public void setFood(FoodEntity food) {
        this.food = food;
    }

    public CartEntity getCartEntity() {
        return cartEntity;
    }

    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", count=" + count +
                ", food=" + food +
                '}';
    }
}
