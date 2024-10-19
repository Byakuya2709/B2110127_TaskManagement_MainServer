/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.controller;

import ctu.demo.dto.AccountRequest;
import ctu.demo.model.Account;
import ctu.demo.model.Group;
import ctu.demo.model.User.Gender;
import ctu.demo.service.AccountService;
import ctu.demo.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author ADMIN
 */
@Component
public class DataInitializer implements CommandLineRunner {
@Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
//       Create default admin account on application startup
        accountService.createDefaultAdminAccount();
        accountService.createManagementGroup();
    }
}
