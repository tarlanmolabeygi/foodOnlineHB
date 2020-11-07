package org.example.service;

import org.example.domain.entity.AdminEntity;
import org.example.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public static void changePassword(String username, String currentPassword, String newPassword){
        AdminRepository adminRepository = new AdminRepository();
        AdminEntity adminEntity = new AdminRepository().findByUsername(username);
        if (adminEntity == null) {
            System.out.println("Username is wrong.");
        } else if(adminEntity.getUsername().equals(username) && adminEntity.getPassword().equals(currentPassword)){
            adminRepository.changeAdminPassword(username, newPassword);
        } else {
            System.out.println("Username or Current Password is wrong.");
        }
    }
}
