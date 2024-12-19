package org.yearup.data.mysql.interfaces;


import org.yearup.models.LineItem;
import org.yearup.models.Order;
import org.yearup.models.Profile;
import org.yearup.models.ShoppingCartItem;

public interface OrdersDao {

    Order getByUserId(int userId);

    Order create(Order order);

   LineItem toLineItem(ShoppingCartItem item, Order order);

   LineItem createLineItem(LineItem orderItem);
}
