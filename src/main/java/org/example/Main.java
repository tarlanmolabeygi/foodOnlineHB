package org.example;

import org.example.domain.entity.AdminEntity;
import org.example.repository.DatabaseConnection;
import org.example.service.RestaurantService;
import org.example.view.MainMenu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main
{
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main( String[] args )
    {
        String fileName = "restaurant.txt";
        new RestaurantService().readDataFromFile(fileName);

        MainMenu.showMainMenu();
    }
}
