/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ctu.demo.service;

import ctu.demo.dto.AccountRequest;
import ctu.demo.model.Account;
import ctu.demo.model.User;
import ctu.demo.repository.AccountRepository;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ADMIN
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    
     @Autowired
   private EmailService emailService;
     
     @Autowired
    private PasswordEncoder passwordEncoder;
    
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
    public Account getAccountByEmail(String mail) {
        return accountRepository.findByEmail(mail).orElse(null);
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
    
     public Account registerNewAccount(AccountRequest accountRequest, MultipartFile image) throws ParseException, IOException, Exception {

        Account newAccount = new Account();
        newAccount.setEmail(accountRequest.getEmail());
        newAccount.setPassword(passwordEncoder.encode(accountRequest.getPassword()));

        User newUser = new User();
        newUser.setAddress(accountRequest.getAddress());
        newUser.setBirth(accountRequest.getBirthdayDate(accountRequest.getBirth()));
        newUser.setFullname(accountRequest.getFullname());
        newUser.setGender(User.Gender.valueOf(accountRequest.getGender()));

        if (image != null && !image.isEmpty()) {
            byte[] bytes = image.getBytes();
            newUser.setAvatar(bytes);
        }

        newAccount.setUser(newUser);
        newUser.setAccount(newAccount);

        return saveAccount(newAccount);
    }
     
      public String generateVerificationCode() {
        // Simple example of generating a verification code
        return String.valueOf((int) (Math.random() * 1000000));
    }
}