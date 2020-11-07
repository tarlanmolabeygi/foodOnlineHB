package org.example.repository;

import org.example.domain.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(AdminRepository.class);

    public void saveUser(UserEntity user) {
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            user.setRegisterDate(Instant.now().toEpochMilli());
            session.save(user);

            transaction.commit();
            session.close();
            logger.info("User '" + user.getName() + "' was saved successfully.");

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    public UserEntity findUserByPhoneNumber(String mobile) {
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();

            Query<UserEntity> query = session.createQuery("from UserEntity u where u.mobile=:mobile", UserEntity.class);
            query.setParameter("mobile", mobile);
            UserEntity userEntity = query.getSingleResult();

            session.close();
            return userEntity;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public void changeUserInformation(UserEntity userEntity) {
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();

            Query<UserEntity> query = session.createQuery("from UserEntity u where u.mobile=:mobile", UserEntity.class);
            query.setParameter("mobile", userEntity.getPhoneNumber());
            UserEntity userEntityStored = query.getSingleResult();
            userEntityStored.setName(userEntity.getName());

            session.save(userEntityStored);
            transaction.commit();
            session.close();

            logger.info("Information of user " + userEntity.getPhoneNumber() + " is changed.");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
