package org.yearup.data.mysql.interfaces;


import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCartItem;

public interface OrdersDao {

    Order getByUserId(int userId);

    Order create(int userId, Profile profile);

   // Order createLineItem(ShoppingCartItem item, int orderId);
}
