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
    @PreAuthorize("permitAll()")
    public Profile getById(Principal principal){
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }

        Profile profile = null;
        try {
            String username = principal.getName();
            User user = userDao.getByUserName(username);
            if (user != null) {
                profile = profileDao.getById(user.getId());
            }
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }

        if (profile == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found.");
        }

        return profile;
    }
    }


