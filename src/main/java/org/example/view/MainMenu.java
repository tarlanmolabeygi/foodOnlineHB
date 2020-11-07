package org.example.view;

import java.util.Scanner;

public class MainMenu {
    public static void showMainMenu() {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println();
                System.out.println("1. Login as User");
                System.out.println("2. Login as Admin");
                System.out.println("+++++++++++++++++++++++++++++++++++++ ");
                System.out.print("> ");
                String number = scanner.next();
                int menu = Integer.valueOf(number);

                if (menu == 1){
                    UserMenu.showMenuForUser();
                } else if (menu == 2){
                    AdminMenu.showMenuForAdmin();
                } else{
                    System.out.println("Please enter a correct number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("There is a mismatch. You must enter a number.");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (true);
    }
}
