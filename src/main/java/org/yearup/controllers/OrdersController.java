package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.mysql.interfaces.*;
import org.yearup.models.*;


import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrdersController {

    private UserDao userDao;
    private OrdersDao ordersDao;
    private ProfileDao profileDao;
    private ShoppingCartDao cartDao;
    private ProductDao productDao;

    @Autowired
    public OrdersController(UserDao userDao, OrdersDao ordersDao, ProfileDao profileDao, ShoppingCartDao cartDao, ProductDao productDao){
        this.ordersDao = ordersDao;
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Order getByUserId(Principal principal){
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();


            return ordersDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public Order create(Principal principal){
        Date date = Date.valueOf(LocalDate.now());



        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            Profile profile = profileDao.getById(user.getId());

            Order order = new Order(
                    profile.getUserId(),
                    date,
                    profile.getAddress(),
                    profile.getCity(),
                    profile.getState(),
                    profile.getZip(),
                    cartDao.getByUserId(userId).getTotal());

            cartDao.deleteCart(userId);
            return ordersDao.create(order);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }


}
