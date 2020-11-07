package org.example.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phoneNumber;

    @Column(name = "postal_code")
    private String postalCode;
    private int zone;

    @Column(name = "register_date")
    private long registerDate;

    @OneToOne(mappedBy="userEntity",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private CartEntity cart;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public CartEntity getCart() {
        return cart;
    }

    public void setCart(CartEntity cart) {
        this.cart = cart;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public long getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(long registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mobile='" + phoneNumber + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", zone=" + zone +
                ", registerDate=" + registerDate +
                ", cart=" + cart +
                '}';
    }
}
