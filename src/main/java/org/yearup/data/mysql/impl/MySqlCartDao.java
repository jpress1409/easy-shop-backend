package org.yearup.data.mysql.impl;

import org.springframework.stereotype.Component;
import org.yearup.data.mysql.interfaces.ProductDao;
import org.yearup.data.mysql.interfaces.ShoppingCartDao;
import org.yearup.models.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlCartDao extends MySqlDaoBase implements ShoppingCartDao {

    private ProductDao productDao;

    public MySqlCartDao(DataSource dataSource, ProductDao productDao) {
        super(dataSource);
        this.productDao = productDao;

    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        ShoppingCart cart = new ShoppingCart();
        String query = "SELECT shopping_cart.product_id, shopping_cart.quantity " +
                "FROM shopping_cart " +
                "WHERE shopping_cart.user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                int quantity = resultSet.getInt("quantity");

                Product product = productDao.getById(productId);

                ShoppingCartItem item = new ShoppingCartItem();
                item.setProduct(product);
                item.setQuantity(quantity);

                cart.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cart;
    }

    @Override
    public ShoppingCart addProduct(int productId, int userId) {
        ShoppingCart cart = getByUserId(userId);
        Product product = productDao.getById(productId);

        ShoppingCartItem existingItem = cart.get(productId);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);


            String query = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? AND product_id = ?";
            try (Connection connection = getConnection()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, existingItem.getQuantity());
                statement.setInt(2, userId);
                statement.setInt(3, productId);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem();
            newItem.setProduct(product);
            newItem.setQuantity(1);
            cart.add(newItem);

            String query = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
            try (Connection connection = getConnection()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userId);
                statement.setInt(2, productId);
                statement.setInt(3, 1);  // Initial quantity is 1
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cart;
    }


    @Override
    public ShoppingCart updateCart(int productId, int userId) {
        String query = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE product_id = ? AND user_id = ?";

        int quantity;
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, productId);
            statement.setInt(2, userId);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                return getByUserId(userId);
            } else {
                throw new SQLException("Product not found in cart.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating cart", e);
        }
    }

}
