package org.example.view;

import org.example.domain.entity.AdminEntity;
import org.example.repository.AdminRepository;
import org.example.service.AdminService;
import org.example.service.ReportService;

import java.util.Scanner;

public class AdminMenu {
    private static AdminRepository adminRepository = new AdminRepository();
    private static ReportService reportService = new ReportService();


    public static void showMenuForAdmin() {
        Scanner scanner = new Scanner(System.in);
        int invalid = 0;
        do {
            try {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                AdminEntity adminEntity = adminRepository.findByUsername(username);
                if (adminEntity == null) {
                    System.out.println("Username is wrong.");
                    invalid++;
                } else if(adminEntity.getUsername().equals(username) && adminEntity.getPassword().equals(password)){
                    showPanelOfRestaurant(username);
                    return;
                } else {
                    System.out.println("Username or Password is wrong.");
                    invalid++;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (invalid < 3);
    }

    private static void showPanelOfRestaurant(String username) {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println();
                System.out.println("1. Report  users shopping in last year");
                System.out.println("2. Report restaurants income delivery in last year");
                System.out.println("3. Change Password");
                System.out.println("4. exit");
                System.out.println("+++++++++++++++++++++++++++++++++++");
                System.out.print("> ");
                String num = scanner.next();
                int menu = Integer.valueOf(num);

                if (menu == 1){
                    reportService.ReportOfUsersShoppingLastYear();
                }else if(menu == 2){
                    reportService.restaurantReportLastYear();
                } else if (menu == 3){
                    changeMenusPassword(username);
                } else if(menu == 4){
                    return;
                } else {
                    System.out.println("Please enter a correct number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("There is a mismatch. You must enter a number.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }

    private static void changeMenusPassword(String username){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your username is: " + username);

        System.out.print("Enter a valid password: ");
        String currentPassword = scanner.next();

        System.out.print("Enter you're new Password: ");
        String newPassword = scanner.next();

        new AdminService().changePassword(username, currentPassword, newPassword);
    }
}
