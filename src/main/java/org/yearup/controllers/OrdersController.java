package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.mysql.interfaces.OrdersDao;
import org.yearup.data.mysql.interfaces.ProfileDao;
import org.yearup.data.mysql.interfaces.ShoppingCartDao;
import org.yearup.data.mysql.interfaces.UserDao;
import org.yearup.models.Order;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.models.Profile;

import java.security.Principal;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrdersController {

    private UserDao userDao;
    private OrdersDao ordersDao;
    private ProfileDao profileDao;
    private ShoppingCartDao cartDao;

    @Autowired
    public OrdersController(UserDao userDao, OrdersDao ordersDao, ProfileDao profileDao, ShoppingCartDao cartDao){
        this.ordersDao = ordersDao;
        this.userDao = userDao;
        this.profileDao = profileDao;
        this.cartDao = cartDao;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Order getByUserId(Principal principal){
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return ordersDao.getByUserId(userId);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
   /* @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Order create(Principal principal){
        try
        {
            // get the currently logged in username
            String userName = principal.getName();
            // find database user by userId
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

            // use the shoppingcartDao to get all items in the cart and return the cart
            return ordersDao.create(userId, profileDao.getById(userId)) + ordersDao.createLineItem(cartDao.mapRow());
        }
        catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    } */
}
