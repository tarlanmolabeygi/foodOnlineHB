package org.example.repository;

import org.example.domain.entity.RestaurantEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantRepository.class);

    public void save(RestaurantEntity restaurant) {
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(restaurant);

            transaction.commit();
            session.close();

        } catch (HibernateException e) {
            logger.error(e.getMessage());
        }
    }

    public RestaurantEntity findByName(String name) {
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();

            Query<RestaurantEntity> query = session.createQuery("from RestaurantEntity r where r.name=:name", RestaurantEntity.class);
            query.setParameter("name", name);
            RestaurantEntity restaurantEntity = query.getSingleResult();

            session.close();
            return restaurantEntity;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public List<RestaurantEntity> findByZone(int zone) {
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();

            Query<RestaurantEntity> query = session.createQuery("from RestaurantEntity r where r.zone=:zone", RestaurantEntity.class);
            query.setParameter("zone", zone);
            List<RestaurantEntity> restaurantList = query.list();

            session.close();
            return restaurantList;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}