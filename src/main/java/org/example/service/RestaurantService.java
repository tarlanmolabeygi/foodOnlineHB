package org.example.service;

import org.example.domain.entity.FoodEntity;
import org.example.domain.entity.RestaurantEntity;
import org.example.domain.entity.UserEntity;
import org.example.domain.enums.Category;
import org.example.repository.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;

public class RestaurantService {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
    RestaurantRepository restaurantRepository = new RestaurantRepository();

    public void readDataFromFile(String fileName) {

        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        int LineNumber = 0;
        int restaurantCount = Integer.parseInt(lines.get(LineNumber));
        LineNumber++;

        for (int i = 1; i <= restaurantCount; i++) {
            String[] restaurantInfo = lines.get(LineNumber).split(", ");
            LineNumber++;

            String name = restaurantInfo[0];
            int foodCount = Integer.parseInt(restaurantInfo[1]);
            int zone = Integer.parseInt(restaurantInfo[2]);
            int deliveryCost = Integer.parseInt(restaurantInfo[3]);

            ArrayList<FoodEntity> foodsList = new ArrayList<>();
            for (int j = 1; j <= foodCount; j++) {
                String[] foodInfo = lines.get(LineNumber).split(", ");
                LineNumber++;

                foodsList.add(new FoodEntity(foodInfo[0], Integer.parseInt(foodInfo[1]), Category.valueOf(foodInfo[2])));
            }

            Set<FoodEntity> foodSet = new HashSet<FoodEntity>(foodsList);
            RestaurantEntity restaurant = new RestaurantEntity(name, zone, deliveryCost, foodSet);

            if (restaurantRepository.findByName(name) != null) {
                System.out.println("Restaurant " + name + " is duplicated.");
            } else {
                restaurantRepository.save(restaurant);
            }

        }
    }

    public void showRestaurantsByZoneNumber(UserEntity user) {
        List<RestaurantEntity> restaurantList = restaurantRepository.findByZone(user.getZone());

        if (restaurantList == null) {
            System.out.println("There is no restaurant in zone " + user.getZone());
            return;
        }
        System.out.println();
        System.out.println("Name  - [ Category ] -  DeliveryCost");
        for (RestaurantEntity restaurant : restaurantList) {
            System.out.print(restaurant.getName() + " - ");

            Set<Category> categorySet = new HashSet<>();
            for (FoodEntity food : restaurant.getFoodsList()) {
                categorySet.add(food.getCategory());
            }
            System.out.print(categorySet + " - " + restaurant.getDeliveryCost());
            System.out.println();
        }
        System.out.println();
    }

    public void printRestaurantsListByZoneByCategory(int zone, String category) {
        List<RestaurantEntity> restaurantList = restaurantRepository.findByZone(zone);

        if (restaurantList == null) {
            System.out.println("There is no restaurant in zone " + zone);
            return;
        }
        System.out.println();
        System.out.println("Name  - [ Category ] -  DeliveryCost");
        for (RestaurantEntity restaurant : restaurantList) {
            Set<Category> categorySet = new HashSet<>();
            for (FoodEntity food : restaurant.getFoodsList()) {
                categorySet.add(food.getCategory());
            }
            if (categorySet.contains(Category.valueOf(category))) {
                System.out.print(restaurant.getName() + " - ");
                System.out.print(categorySet + " - " + restaurant.getDeliveryCost());
                System.out.println();
            }
        }
        System.out.println();
    }

    public void printRestaurantFoods(String restaurantName){
        RestaurantEntity restaurant = restaurantRepository.findByName(restaurantName);
        if (restaurant != null){
            System.out.println();
            System.out.println("Id: Food Name  -  Price  -  Category");
            for(FoodEntity food: restaurant.getFoodsList()){
                System.out.println(food.getId() + ": " + food.getName() + " - " + food.getPrice() +
                        " - " + food.getCategory());
            }
            System.out.println();
        } else{
            System.out.println("There is no restaurant by this name.");
        }

    }
}
