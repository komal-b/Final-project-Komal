package com.welcomehome.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private Logger loggers = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.registerUser(user);
    }
    public List<Role> getAllRoles(){
       return userDao.getAllRoles();
    }

    public ResponseEntity loginUser(User user) {
        User dbUser = userDao.loginUser(user);
        ResponseEntity response = null;
        try{

            loggers.info("User Name: "+user.getUserName());
            loggers.info("User Password: "+user.getPassword());
            if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
                loggers.info("Authentication Successful!");
                loggers.info("Returning response: " + new LoginResponse(true, dbUser.getUserName(), "User logged in successfully!").getUserName());
                response =  ResponseEntity.ok(new LoginResponse(true, dbUser.getUserName(), "User logged in successfully!"));
            } else {
                response = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse(false, null, "Login failed! "));
            }

        }catch (Exception e){
            loggers.error("Authentication Failed: ",e);
        }
        return response;
    }
}
