package org.yearup.data.mysql.interfaces;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);

    ShoppingCart addProduct(int productId, int userId);

    ShoppingCart updateCart(int productId, int userID, ShoppingCart cart);

    void deleteCart(int userId);

    ShoppingCartItem mapRow(ResultSet row) throws SQLException;
}
