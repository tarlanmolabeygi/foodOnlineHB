package org.example.view;

import org.example.domain.entity.*;
import org.example.repository.CartRepository;
import org.example.repository.FoodRepository;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.example.service.RestaurantService;
import org.example.service.UserService;
import org.example.validation.FieldValidator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserMenu {
    private static RestaurantService restaurantService = new RestaurantService();
    private static UserRepository userRepository = new UserRepository();
    private static UserService userService = new UserService();
    private static CartRepository cartRepository = new CartRepository();

    public static void showMenuForUser(){
        Scanner scanner = new Scanner(System.in);
        do {
            UserEntity user = new UserEntity();
            while (true) {
                try {
                    System.out.print("Enter mobile number of user: ");
                    String userMobile = scanner.next();
                    FieldValidator.mobileValidate(userMobile);
                    user.setPhoneNumber(userMobile);
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            UserEntity userStored = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
            if (userStored != null) {
                user.setId(userStored.getId());
                user.setName(userStored.getName());
                user.setPostalCode(userStored.getPostalCode());
            }

            while (true) {
                try {
                    System.out.print("Enter zone of user: ");
                    String zone = scanner.next();
                    int userZone = Integer.valueOf(zone);
                    FieldValidator.zoneValidate(userZone);
                    user.setZone(userZone);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("There is a mismatch. You must enter a zone number.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            int menu = 0;
            do {
                restaurantService.showRestaurantsByZoneNumber(user);

                System.out.println("1. Filter By Category");
                System.out.println("2. Choose Restaurant");
                System.out.println("3. Edit Users Information");
                System.out.println("4. Exit");
                while (true) {
                    try {
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++ ");
                        System.out.print("> ");
                        String m = scanner.next();
                        menu = Integer.valueOf(m);
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("There is a mismatch. You must enter a number.");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                if (menu == 1) {
                    showRestaurantsByCategoryName(user);
                } else if (menu == 2) {
                    System.out.print("Enter Restaurants Name: ");
                    String restaurantName = scanner.next();
                    showRestaurantFoods(restaurantName, user);
                }else if(menu == 3){
                    UserEntity userEntity = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
                    if (userEntity == null){
                        System.out.println("There is no Information was saved for user " + user.getPhoneNumber());
                    } else{
                        System.out.println();
                        System.out.println("Mobile: " + userEntity.getPhoneNumber());
                        System.out.println("Name: " + userEntity.getName());
                        System.out.println();

                        System.out.print("Enter new Name: ");
                        String name = scanner.next();

                        userEntity.setName(name);
                        userService.changeUserInformation(userEntity);
                    }
                } else if (menu == 4) {
                } else {
                    System.out.println("Choose correct number.");
                }
            } while (menu != 4);

        } while (true);
    }

    public static void showRestaurantsByCategoryName(UserEntity user) {
        System.out.print("Enter category name: ");
        Scanner scanner = new Scanner(System.in);
        String category = scanner.next();
        int menu = 2;

        do {
            restaurantService.printRestaurantsListByZoneByCategory(user.getZone(), category);

            System.out.println("1. Enter Restaurant Name");
            System.out.println("2. Back");
            while (true) {
                try {
                    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++ ");
                    System.out.print("> ");
                    String m = scanner.next();
                    menu = Integer.valueOf(m);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" You must enter a valid number.");

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (menu == 1) {
                System.out.print("Enter Restaurant Name: ");
                String restaurantName = scanner.next();
                showRestaurantFoods(restaurantName, user);
            } else if (menu == 2) {
            } else {
                System.out.println("Choose  a correct number.");
            }
        } while (menu != 2);
    }

    public static void showRestaurantFoods(String restaurantName, UserEntity user) {
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        FoodRepository foodRepository = new FoodRepository();

        RestaurantEntity restaurant = restaurantRepository.findByName(restaurantName);
        if (restaurant == null) {
            System.out.println("There is not any restaurant by this name.");
            return;
        }
        restaurantService.printRestaurantFoods(restaurantName);

        Scanner scanner = new Scanner(System.in);

        CartEntity cart = new CartEntity();
        ArrayList<CartFood> foodsList = new ArrayList<>();
        cart.setRestaurant(restaurant);
        cart.setUserEntity(user);
        int menu = 0;
        do {
            System.out.println();
            System.out.println("1. Add food to cart");
            System.out.println("2. Remove food from cart");
            System.out.println("3. Show cart");
            System.out.println("4. Finish order");
            System.out.println("5. Back");

            while (true) {
                try {
                    System.out.print("> ");
                    String m = scanner.next();
                    menu = Integer.valueOf(m);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println(" You must enter a valid  number.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            if (menu == 1) {
                System.out.print("Enter FoodId FoodCount: ");
                scanner.nextLine();
                String addFoodToCart = scanner.nextLine();
                if (addFoodToCart.split(" ").length != 2){
                    System.out.println("Please enter your input like: FoodId FoodCount");
                } else {
                    try {
                        int foodId = Integer.valueOf(addFoodToCart.split(" ")[0]);
                        int foodCount = Integer.valueOf(addFoodToCart.split(" ")[1]);
                        CartFood cartFood = new CartFood();
                        FoodEntity foodEntity = foodRepository.findFoodById(foodId, restaurant.getId());
                        if (foodEntity == null) {
                            System.out.println("FoodId is mistake.");
                        } else if (foodCount <= 0){
                            System.out.println("FoodCount is mistake");
                        } else {
                            cartFood.setFood(foodEntity);
                            cartFood.setCount(foodCount);
                            cartFood.setCartEntity(cart);
                            foodsList.add(cartFood);
                        }
                    } catch (NumberFormatException e){
                        System.out.println("There is a mismatch. You must enter a number.");
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            } else if (menu == 2) {
                System.out.print("Enter FoodId FoodCount: ");
                scanner.nextLine();
                String addFood = scanner.nextLine();
                if (addFood.split(" ").length != 2){
                    System.out.println("Please enter your input like: FoodId FoodCount");
                } else {
                    try {
                        int foodId = Integer.valueOf(addFood.split(" ")[0]);
                        int foodCount = Integer.valueOf(addFood.split(" ")[1]);
                        if (foodCount <= 0){
                            System.out.println("FoodCount is mistake");
                        } else {
                            boolean foodFound = false;
                            for (int i = foodsList.size() - 1; i >= 0; i--) {
                                if (foodsList.get(i).getFood().getId() == foodId) {
                                    foodFound = true;
                                    if ((foodsList.get(i).getCount() - foodCount) > 0) {
                                        foodsList.get(i).setCount(foodsList.get(i).getCount() - foodCount);
                                    } else if ((foodsList.get(i).getCount() - foodCount) == 0) {
                                        foodsList.remove(i);
                                    } else {
                                        System.out.println("count is bigger than current count.");
                                    }
                                }
                            }

                            if (foodFound == false) {
                                System.out.println("FoodId is invalid.");
                            }
                        }
                    } catch (NumberFormatException e){
                        System.out.println("You must enter a number.");
                    }catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            } else if (menu == 3) {
                System.out.println("FoodId: FoodName - FoodCount - FoodPrice - Price");
                int totalPrice = 0;
                for (CartFood food : foodsList) {
                    int price = food.getFood().getPrice() * food.getCount();
                    System.out.print(food.getFood().getId() + ": " + food.getFood().getName() + " - " +
                            food.getCount() + " - " + food.getFood().getPrice() + " - " +
                            price);
                    totalPrice += price;
                    System.out.println();
                }
                System.out.println("Delivery Cost: " + restaurant.getDeliveryCost());
                totalPrice += restaurant.getDeliveryCost();
                System.out.println("Total Price is: " + totalPrice);
                System.out.println();
            } else if (menu == 4) {
                UserEntity userEntity = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
                if (userEntity == null) {
                    System.out.print("Enter name of User: ");
                    String name = scanner.next();
                    user.setName(name);

                    System.out.print("Enter postal code of user: ");
                    String postalCode = scanner.next();
                    user.setPostalCode(postalCode);

                    userRepository.saveUser(user);
                    try {
                        Thread. sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }
                    userEntity = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
                    user.setId(userEntity.getId());

                } else {
                    user.setId(userEntity.getId());
                    user.setName(userEntity.getName());
                    user.setPostalCode(userEntity.getPostalCode());
                }

                cart.setTimestamp(Instant.now().toEpochMilli());
                Set<CartFood> foodSet = new HashSet<CartFood>(foodsList);
                cart.setCartFoodsList(foodSet);
                user.setCart(cart);
                cartRepository.saveCart(user);
                try {
                    Thread. sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                cart.setId(cartRepository.getIdByTimestamp(cart.getTimestamp()));
                user.setCart(cart);

                cartRepository.saveCartFoodsList(user);
                userService.saveCartInFile(user, cart);

                menu = 5;
            } else if (menu == 5) {

            } else {
                System.out.println("Enter  a valid number.");
            }

        } while (menu != 5);
    }
}
