package org.example.service;

import org.example.domain.entity.RestaurantReportEntity;
import org.example.domain.entity.RestaurantReportSellEntity;
import org.example.domain.entity.UserReportEntity;
import org.example.repository.ReportRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReportService {
    public static ReportRepository reportRepository = new ReportRepository();

    public void ReportOfUsersShoppingLastYear() {
        List<UserReportEntity> usersList = reportRepository.UserShoppingLastYear();
        Map<Integer, List<UserReportEntity>> usersByMonth = usersList.stream()
                .collect(Collectors.groupingBy(UserReportEntity::getMonth, Collectors.toList()));

        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println("Row - Month - Total Price :  Name,  Mobile");

        usersByMonth.entrySet().stream().forEach(e -> {

            int row = atomicInteger.incrementAndGet();
            String message = String.format("%-3d -   %d", row, e.getKey());
            System.out.println(message);

            long count = e.getValue().stream().filter(f -> f.getAvrragePrice() < 100_000).count();
            if (count != 0) {
                System.out.println("  ===================== <100 ====================      : ");
                e.getValue().stream().filter(f -> f.getAvrragePrice() < 100_000).forEach(u -> {
                    String report = String.format("%28s %s, %s", "", u.getName(), u.getMobile());
                    System.out.println(report);
                });
            }

            count = e.getValue().stream().filter(f -> f.getAvrragePrice() >= 100_000 && f.getAvrragePrice() <= 500_000).count();
            if (count != 0) {
                System.out.println("  ====================== 100 to 500 ================== : ");
                e.getValue().stream().filter(f -> f.getAvrragePrice() >= 100_000 && f.getAvrragePrice() <= 500_000).forEach(u -> {
                    String report = String.format("%28s %s, %s", "", u.getName(), u.getMobile());
                    System.out.println(report);
                });
            }

            count = e.getValue().stream().filter(f -> f.getAvrragePrice() > 500_000).count();
            if (count != 0) {
                System.out.println(" ================== >500 ==================== : ");
                e.getValue().stream().filter(f -> f.getAvrragePrice() > 500_000).forEach(u -> {
                    String report = String.format("%28s %s, %s", "", u.getName(), u.getMobile());
                    System.out.println(report);
                });
            }

        });
    }

    public void restaurantReportLastYear() {
        List<RestaurantReportSellEntity> restaurantSellList = reportRepository.RestaurantInfoBasedFoodSellLastYear();

        Map<String, RestaurantReportSellEntity> restaurantsSellListByName = restaurantSellList.stream()
                .collect(Collectors.toMap(RestaurantReportSellEntity::getName, Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(RestaurantReportSellEntity::getTotalPrice))));
        List<RestaurantReportEntity> restaurantReportList = reportRepository.RestaurantInfoBasedDeliveryLastYear();

        for(RestaurantReportEntity restaurantEntity : restaurantReportList){
            restaurantEntity.setFoodNameSell(restaurantsSellListByName.get(restaurantEntity.getName()).getFoodName());
        }

        Map<Integer, List<RestaurantReportEntity>> restaurantByZone = restaurantReportList.stream()
                .collect(Collectors.groupingBy(RestaurantReportEntity::getZone, Collectors.toList()));

        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println("Row - zone - Del. Price :  Name,  Food");

        restaurantByZone.entrySet().stream().forEach(e -> {

            int row = atomicInteger.incrementAndGet();
            String msg = String.format("%-3d -   %d", row, e.getKey());
            System.out.println(msg);

            long count = e.getValue().stream().filter(f -> f.getDeliveryPrice() < 1_000_000).count();
            if (count != 0) {
                System.out.println(" =======================  <1 ======================= : ");
                e.getValue().stream().filter(f -> f.getDeliveryPrice() < 1_000_000).forEach(u -> {
                    String report = String.format("%26s %s, %s", "", u.getName(), u.getFoodNameSell());
                    System.out.println(report);
                });
            }

            count = e.getValue().stream().filter(f -> f.getDeliveryPrice() >= 1_000_000 && f.getDeliveryPrice() <= 2_000_000).count();
            if (count != 0) {
                System.out.println(" ======================  1 to 2 =======================  : ");
                e.getValue().stream().filter(f -> f.getDeliveryPrice() >= 1_000_000 && f.getDeliveryPrice() <= 2_000_000).forEach(u -> {
                    String report = String.format("%26s %s, %s", "", u.getName(), u.getFoodNameSell());
                    System.out.println(report);
                });
            }

            count = e.getValue().stream().filter(f -> f.getDeliveryPrice() > 2_000_000).count();
            if (count != 0) {
                System.out.println("  =================   >2  ====================: ");
                e.getValue().stream().filter(f -> f.getDeliveryPrice() > 2_00_000).forEach(u -> {
                    String report = String.format("%26s %s, %s", "", u.getName(), u.getFoodNameSell());
                    System.out.println(report);
                });
            }

        });
    }
}
