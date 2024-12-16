package org.yearup.data.mysql.interfaces;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    ShoppingCart addProduct(int productId, int userId);

    ShoppingCart updateCart(int productId, int userID);
}
