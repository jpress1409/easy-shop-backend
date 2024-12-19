package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.mysql.interfaces.ProfileDao;
import org.yearup.data.mysql.interfaces.UserDao;
import org.yearup.models.User;
import org.yearup.models.Profile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("profile")
@CrossOrigin
public class ProfilesController {

    private ProfileDao profileDao;
    private UserDao userDao;

    @Autowired
    public ProfilesController(ProfileDao profileDao, UserDao userDao){
        this.profileDao = profileDao;
        this.userDao = userDao;
    }
    @GetMapping
    public Profile getByUserId(Principal principal){
        try{
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
            }
            int userId = user.getId();
            return profileDao.getById(userId);
        }catch(Exception e){
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
    @PutMapping
    public void updateProfile(Principal principal, @RequestBody Profile profile){
        try{
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
            }
            int userId = user.getId();
            profileDao.update(userId, profile);
        }catch(Exception e){
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    }


