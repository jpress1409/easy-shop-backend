package org.yearup.data.mysql.impl;

import org.springframework.stereotype.Component;
import org.yearup.data.mysql.interfaces.ProductDao;
import org.yearup.data.mysql.interfaces.ShoppingCartDao;
import org.yearup.models.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
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
        String query = "SELECT products.product_id, products.name, products.price, shopping_cart.quantity " +
                "FROM shopping_cart " +
                "JOIN products ON " +
                "shopping_cart.product_id = products.product_id " +
                "WHERE shopping_cart.user_id = ?";

        ShoppingCart cart = new ShoppingCart();

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                ShoppingCartItem item = mapRow(results);
                cart.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cart;
    }
    @Override
    public ShoppingCart addProduct(int productId, int userId, int quantity) {

            String query = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?,?,?) ON DUPLICATE KEY UPDATE quantity = quantity + ?";

            try (Connection connection = getConnection()) {
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, userId);
                    statement.setInt(2, productId);
                    statement.setInt(3, quantity);
                    statement.setInt(4, quantity);
                    statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        return getByUserId(userId);
    }


    @Override
    public void updateCart(int productId, int userId, int quantity) {
        String query = "UPDATE shopping_cart SET quantity = ? WHERE product_id = ? AND user_id = ?";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, quantity);
            statement.setInt(2, userId);
            statement.setInt(3, productId);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating cart", e);
        }
    }
    @Override
    public void deleteCart(int userId){
        String sql = "DELETE FROM shopping_cart " +
                " WHERE user_id = ?;";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public ShoppingCartItem mapRow(ResultSet row) throws SQLException {
        int productId = row.getInt("product_id");
        int quantity = row.getInt("quantity");
        String productName = row.getString("name");
        BigDecimal price = row.getBigDecimal("price");

        Product product = new Product();
        product.setProductId(productId);
        product.setName(productName);
        product.setPrice(price);

        ShoppingCartItem item = new ShoppingCartItem(product);
        item.setQuantity(quantity);

        return item;
    }

}
