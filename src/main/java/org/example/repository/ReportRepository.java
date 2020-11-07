package org.example.repository;

import org.example.domain.entity.RestaurantReportEntity;
import org.example.domain.entity.RestaurantReportSellEntity;
import org.example.domain.entity.UserReportEntity;
import org.example.util.DateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ReportRepository {
    private static final Logger logger = LoggerFactory.getLogger(ReportRepository.class);

    public List<UserReportEntity> UserShoppingLastYear() {
        List<UserReportEntity> usersList = new ArrayList<>();
        try {

            String sql = String.format("SELECT report.name, report.mobile, sum(report.food_price) as total_price, report.register_date FROM (\n" +
                    "SELECT u.name, u.mobile, u.register_date, (cf.count * f.price) as food_price  FROM food_online.users u JOIN food_online.cart c ON u.id = c.user_id \n" +
                    "JOIN food_online.cart_food cf ON c.id = cf.cart_id\n" +
                    "JOIN food_online.food f ON cf.food_id = f.id\n" +
                    ") as report\n" +
                    "WHERE report.register_date > (UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 365 day)) * 1000)\n" +
                    "GROUP BY report.mobile");

            Session session = DatabaseConnection.getSessionFactory().openSession();
            Query query = session.createSQLQuery(sql);
            List<Object[]> result = query.getResultList();
            session.close();

            for (Object[] list : result) {
                UserReportEntity user = new UserReportEntity();
                user.setName(list[0].toString());
                user.setMobile(list[1].toString());
                user.setAvrragePrice(Integer.parseInt(list[2].toString()));
                long timestamp = Long.valueOf(list[3].toString());
                user.setMonth(DateUtil.timestampToMonth(timestamp));

                usersList.add(user);
            }

            return usersList;

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public List<RestaurantReportSellEntity> RestaurantInfoBasedFoodSellLastYear() {
        List<RestaurantReportSellEntity> restaurantsList = new ArrayList<>();
        String sql = String.format("SELECT report1.rname, report1.zone, report1.fname, sum(report1.sell_food) as sfood from (\n" +
                "SELECT r.name as rname, r.zone, f.name as fname, (f.price * cf.count) as sell_food, c.timestamp\n" +
                "FROM food_online.restaurant as r \n" +
                "JOIN food_online.food as f ON r.id = f.restaurant_id\n" +
                "JOIN food_online.cart_food as cf ON f.id = cf.food_id\n" +
                "JOIN food_online.cart as c ON c.id = cf.cart_id\n" +
                "WHERE c.timestamp > (UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 365 day)) * 1000)\n" +
                ") as report1\n" +
                "GROUP BY report1.rname, report1.fname");

        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Query query = session.createSQLQuery(sql);
            List<Object[]> result = query.getResultList();
            session.close();

            for (Object[] list : result) {
                RestaurantReportSellEntity restaurant = new RestaurantReportSellEntity();
                restaurant.setName(list[0].toString());
                restaurant.setZone(Integer.parseInt(list[1].toString()));
                restaurant.setFoodName(list[2].toString());
                restaurant.setTotalPrice(Integer.parseInt(list[3].toString()));

                restaurantsList.add(restaurant);
            }
            return restaurantsList;

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public List<RestaurantReportEntity> RestaurantInfoBasedDeliveryLastYear() {
        List<RestaurantReportEntity> restaurantsList = new ArrayList<>();


        String sql = String.format("SELECT report.rname, report.zone, sum(report.delivery_cost) as total_delivery FROM\n" +
                "(\n" +
                "SELECT r.name as rname, r.zone, r.delivery_cost FROM food_online.restaurant as r JOIN food_online.cart as c ON r.id = c.restaurant_id\n" +
                "WHERE c.timestamp > (UNIX_TIMESTAMP(DATE_SUB(NOW(), INTERVAL 365 day)) * 1000)\n" +
                ") as report\n" +
                "GROUP BY rname");
        try {
            Session session = DatabaseConnection.getSessionFactory().openSession();
            Query query = session.createSQLQuery(sql);
            List<Object[]> result = query.getResultList();
            session.close();


            for (Object[] list : result) {
                RestaurantReportEntity restaurant = new RestaurantReportEntity();
                restaurant.setName(list[0].toString());
                restaurant.setZone(Integer.parseInt(list[1].toString()));
                restaurant.setDeliveryPrice(Integer.parseInt(list[2].toString()));

                restaurantsList.add(restaurant);
            }
            return restaurantsList;

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }
}
