package org.example.service;

import org.example.domain.entity.CartEntity;
import org.example.domain.entity.CartFood;
import org.example.domain.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final UserRepository userRepository = new UserRepository();

    public void changeUserInformation(UserEntity userEntity) {
        userRepository.changeUserInformation(userEntity);
    }

    public void saveCartInFile(UserEntity user, CartEntity cartEntity){
        String fileName = user.getPhoneNumber() + "-" + cartEntity.getTimestamp() + ".txt";

        ArrayList<String> content = new ArrayList<>();
        content.add("Restaurant Name: " + cartEntity.getRestaurant().getName());
        content.add("Date/Time: " + DateUtil.timestampToDateString(cartEntity.getTimestamp()));
        content.add("");
        content.add("Customer Name: " + user.getName());
        content.add("Customer Mobile: " + user.getPhoneNumber());
        content.add("--------------------------------------");
        content.add("row: FoodName - FoodCount - FoodPrice - Price");

        int row = 1;
        int totalPrice = 0;
        for(CartFood food : user.getCart().getCartFoodsList()){
            int price = food.getFood().getPrice() * food.getCount();
            content.add(row + ": " + food.getFood().getName() + " - " +
                    food.getCount() + " - " + food.getFood().getPrice() + " - " +
                    price);
            totalPrice += price;
            row++;
        }
        content.add("--------------------------------------");
        content.add("Delivery Cost: " + user.getCart().getRestaurant().getDeliveryCost());
        totalPrice += user.getCart().getRestaurant().getDeliveryCost();
        content.add("Total Price is: " + totalPrice);

        try {
            Files.write(Paths.get("carts/",fileName),content , Charset.defaultCharset());
            logger.info("Cart Information saved successfully in file: " + fileName);
            System.out.println("++++++++++++++++++++++++++++++++++++++++");
            System.out.println();

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
