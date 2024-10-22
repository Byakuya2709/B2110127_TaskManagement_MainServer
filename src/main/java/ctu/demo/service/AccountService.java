package ctu.demo.service;

import ctu.demo.dto.AccountRequest;
import ctu.demo.model.Account;
import ctu.demo.model.Group;
import ctu.demo.model.User;
import ctu.demo.repository.AccountRepository;
import ctu.demo.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupRepository groupRepository;

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

    public Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public void createDefaultAdminAccount() throws Exception {
        try {
            if (userService.getUserHasRoleAdmin().isEmpty()) {
                AccountRequest accountRequest = new AccountRequest();
                accountRequest.setEmail("admin@gmail.com");
                accountRequest.setPassword("Admin12345");
                accountRequest.setFullname("Admin Users");
                accountRequest.setAddress("Admin Addresss");
                accountRequest.setBirth("1990-01-01");
                accountRequest.setGender("MALE");

                // Create a new account and save to DB
                registerNewAdminAccount(accountRequest, null);

                logger.info("Admin account created successfully.");
            } else {
                logger.warn("Admin account already exists.");
            }
        } catch (ParseException ex) {
            logger.error("Error parsing the birth date for the admin account: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error parsing the birth date for the admin account.");
        } catch (IOException ex) {
            logger.error("Error processing the avatar image for the admin account: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error processing the avatar image for the admin account.");
        } catch (Exception ex) {
            logger.error("Unexpected error while creating the admin account: {}", ex.getMessage(), ex);
            throw new RuntimeException("An unexpected error occurred while creating the admin account.");
        }
    }

    @Transactional // Đảm bảo rằng phương thức này chạy trong một giao dịch
    public Account registerNewAdminAccount(@Valid AccountRequest accountRequest, MultipartFile image)
            throws ParseException, IOException, Exception {

        // Kiểm tra xem email đã tồn tại chưa
        if (findAccountByEmail(accountRequest.getEmail()) != null) {
            throw new RuntimeException("Email already exists: " + accountRequest.getEmail());
        }

        // Tạo và lưu tài khoản mới
        Account newAccount = new Account();
        newAccount.setEmail(accountRequest.getEmail());
        newAccount.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        newAccount.setRole(Account.Role.ADMIN);

        // Tạo người dùng mới
        User newUser = new User();
        newUser.setAddress(accountRequest.getAddress());
        newUser.setBirth(accountRequest.getBirthdayDate(accountRequest.getBirth()));
        newUser.setFullname(accountRequest.getFullname());
        newUser.setGender(User.Gender.valueOf(accountRequest.getGender()));
        newUser.setDetail("Nhân viên công ty");

        // Xử lý ảnh nếu có
        if (image != null && !image.isEmpty()) {
            byte[] bytes = image.getBytes();
            newUser.setAvatar(bytes);
        }

        // Gán tài khoản cho người dùng
        newUser.setAccount(newAccount);
        newAccount.setUser(newUser);
        // Lưu tài khoản vào cơ sở dữ liệu
        Account savedAccount = saveAccount(newAccount);
        return savedAccount;
    }

    @Transactional
    public void createManagementGroup() {
        try {
            List<User> users = userService.getUserHasRoleAdmin();

            // Check if there are any users available
            if (users.isEmpty()) {
                throw new IllegalStateException("No users available to create a management group.");
            }
            if (groupRepository.findByName("Ban Quản Lý") == null) {
                Group managementGroup = new Group();
                managementGroup.setName("Ban Quản Lý");
                managementGroup.setDescription("Group for managing operations");
                User leader = users.get(0); // Example: select the first admin as leader
                managementGroup.setLeader(leader);
                for (User user : users) {
                    if (user.getGroup() == null) { // Only add if the user does not belong to any group
                        managementGroup.addUser(user); // Add user to the group
                        user.setGroup(managementGroup); // Set group for each user
                        userService.saveUser(user);
                        groupRepository.save(managementGroup);
                    }
                }
            } else {
                Group managementGroup = groupRepository.findByName("Ban Quản Lý");
                for (User user : users) {
                    if (user.getGroup() == null) { // Only add if the user does not belong to any group
                        managementGroup.addUser(user); // Add user to the group
                        user.setGroup(managementGroup); // Set group for each user
                        userService.saveUser(user);

                        groupRepository.save(managementGroup);
                    }
                }
            }
        } catch (IllegalStateException ex) {
            logger.error("Lỗi khi tạo nhóm quản lý: {}", ex.getMessage(), ex);
            throw new RuntimeException("Lỗi khi tạo nhóm quản lý: " + ex.getMessage());
        } catch (Exception ex) {
            logger.error("Đã xảy ra lỗi không mong muốn khi tạo nhóm quản lý: {}", ex.getMessage(), ex);
            throw new RuntimeException("Đã xảy ra lỗi không mong muốn khi tạo nhóm quản lý.");
        }

    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userService.getUserById(userId);
        if (user.getGroup() != null) {
            Group group = user.getGroup();
            // If the user is the leader, handle the leadership reassignment
            if (group.getLeader().equals(user)) {
                // You can either set a new leader or nullify the leader
                // For example, setting the leader to null:
                group.setLeader(null); // or assign a new leader if applicable
            }
            group.getUsers().remove(user); // Remove user from the group
            user.setGroup(null); // Set user's group to null
        }

        // Delete the user
        userService.deleteUser(userId);
    }

    @Transactional // Đảm bảo rằng phương thức này chạy trong một giao dịch
    public Account registerNewAccount(@Valid AccountRequest accountRequest, MultipartFile image)
            throws ParseException, IOException, Exception {

        // Kiểm tra xem email đã tồn tại chưa
        if (findAccountByEmail(accountRequest.getEmail()) != null) {
            throw new RuntimeException("Email already exists: " + accountRequest.getEmail());
        }

        // Tạo và lưu tài khoản mới
        Account newAccount = new Account();
        newAccount.setEmail(accountRequest.getEmail());
        newAccount.setPassword(passwordEncoder.encode(accountRequest.getPassword()));
        newAccount.setRole(Account.Role.EMPLOYEE);

        // Tạo người dùng mới
        User newUser = new User();
        newUser.setAddress(accountRequest.getAddress());
        newUser.setBirth(accountRequest.getBirthdayDate(accountRequest.getBirth()));
        newUser.setFullname(accountRequest.getFullname());
        newUser.setGender(User.Gender.valueOf(accountRequest.getGender()));
        newUser.setDetail("Nhân viên công ty");

        // Xử lý ảnh nếu có
        if (image != null && !image.isEmpty()) {
            byte[] bytes = image.getBytes();
            newUser.setAvatar(bytes);
        }

        // Gán tài khoản cho người dùng
        newUser.setAccount(newAccount);
        newAccount.setUser(newUser);

        Account savedAccount = saveAccount(newAccount);
        return savedAccount;
    }

    @Transactional
    public boolean updatePassword(Account account, String newPassword) {
        try {
            // Mã hóa mật khẩu mới trước khi lưu
            String encodedPassword = passwordEncoder.encode(newPassword);
            account.setPassword(encodedPassword);

            // Lưu tài khoản đã cập nhật với mật khẩu mới
            accountRepository.save(account);

            logger.info("Password updated successfully for account with email: {}", account.getEmail());
            return true; // Trả về true nếu cập nhật thành công
        } catch (Exception e) {
            logger.error("Error updating password for account with email {}: {}", account.getEmail(), e.getMessage());
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
}
