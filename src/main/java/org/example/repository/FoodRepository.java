package org.example.repository;

import org.example.domain.entity.FoodEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FoodRepository {
    private static final Logger logger = LoggerFactory.getLogger(FoodRepository.class);

    public FoodEntity findFoodById(int foodId) {
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            FoodEntity foodEntity = session.get(FoodEntity.class, foodId);

            transaction.commit();
            session.close();

            return foodEntity;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public FoodEntity findFoodById(int foodId, int restaurantId){
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            FoodEntity foodEntity = session.get(FoodEntity.class, foodId);

            transaction.commit();
            session.close();

            return foodEntity;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }
}
