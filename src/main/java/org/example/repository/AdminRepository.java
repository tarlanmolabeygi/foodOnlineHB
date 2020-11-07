package org.example.repository;

import org.example.domain.entity.AdminEntity;
import org.example.domain.entity.RestaurantEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminRepository {
    private static final Logger logger = LoggerFactory.getLogger(AdminRepository.class);

    public AdminEntity findByUsername(String username){
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();

            Query<AdminEntity> query = session.createQuery("from AdminEntity a where a.username=:username", AdminEntity.class);
            query.setParameter("username", username);
            AdminEntity adminEntity = query.getSingleResult();

            session.close();
            return adminEntity;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void changeAdminPassword(String username, String newPassword){
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Query<AdminEntity> query = session.createQuery("from AdminEntity a where a.username=:username", AdminEntity.class);
            query.setParameter("username", username);
            AdminEntity adminEntity = query.getSingleResult();
            adminEntity.setPassword(newPassword);

            session.save(adminEntity);
            transaction.commit();
            session.close();

            logger.info("Password of " + username + " is changed.");
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }
}
