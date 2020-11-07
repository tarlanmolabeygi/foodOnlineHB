package org.example.repository;

import org.example.domain.entity.CartEntity;
import org.example.domain.entity.CartFood;
import org.example.domain.entity.RestaurantEntity;
import org.example.domain.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CartRepository {
    private static final Logger logger = LoggerFactory.getLogger(CartRepository.class);

    public void saveCart(UserEntity user){
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            CartEntity cartEntity = user.getCart();
            session.save(cartEntity);

            transaction.commit();
            session.close();

        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    public int getIdByTimestamp( Long timestamp){

        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();

            Query<CartEntity> query = session.createQuery("from CartEntity c where c.timestamp=:timestamp", CartEntity.class);
            query.setParameter("timestamp", timestamp);
            CartEntity cartEntity = query.getSingleResult();

            session.close();
            return cartEntity.getId();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return -1;
    }

    public void saveCartFoodsList(UserEntity user){

        for (CartFood cartFood : user.getCart().getCartFoodsList()){

            Session session = DatabaseConnection.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(cartFood);

            transaction.commit();
            session.close();
        }
    }
}
