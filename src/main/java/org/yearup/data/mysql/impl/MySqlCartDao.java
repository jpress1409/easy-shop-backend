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
    public ShoppingCart addProduct(int productId, int userId) {


        ShoppingCart cart = getByUserId(userId);
        Product product = productDao.getById(productId);

        ShoppingCartItem existingItem = cart.get(productId);
        int quantity= existingItem.getQuantity();
        if(existingItem == null || quantity == 0 ) {
            quantity = 1;
        }
            String query = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
            try (Connection connection = getConnection()) {

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, userId);
                statement.setInt(2, product.getProductId());
                statement.setInt(3, quantity);
                statement.executeUpdate();


            } catch (SQLException e) {
                e.printStackTrace();
            }


        return cart;
    }


    @Override
    public ShoppingCart updateCart(int productId, int userId, ShoppingCart cart) {
        String query = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE product_id = ? AND user_id = ?";

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
