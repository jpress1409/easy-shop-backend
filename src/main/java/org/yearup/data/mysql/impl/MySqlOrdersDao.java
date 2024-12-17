package org.yearup.data.mysql.impl;

import org.springframework.stereotype.Component;
import org.yearup.data.mysql.interfaces.OrdersDao;
import org.yearup.data.mysql.interfaces.ShoppingCartDao;
import org.yearup.models.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlOrdersDao extends MySqlDaoBase implements OrdersDao {
    public MySqlOrdersDao(DataSource dataSource) {
        super(dataSource);
    }
    @Override
    public Order getByUserId(int userId) {


        String query = "SELECT orders.order_id, orders.user_id, orders.order_date, " +
                "products.product_id, products.name, products.price, order_line_items.quantity, order_line_items.discount_percent " +
                "FROM orders " +
                "JOIN order_line_items ON orders.order_id = order_line_items.order_id " +
                "JOIN products ON order_line_items.product_id = products.product_id " +
                "WHERE orders.user_id = ?";

        Order order = null;

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();

            while (results.next()) {
                Order newOrder = mapRow(results);

                if(order == null){
                    order = new Order();
                    order.setOrderId(newOrder.getOrderId());
                    order.setUserId(newOrder.getUserId());
                    order.setOrderDate(newOrder.getOrderDate());

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }


    public Order create(int userId, Profile profile) {
        Order order = getByUserId(userId);

        String query = "INSERT INTO orders(user_id, date, address, city, state, zip, shipping_amount) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userId);
            statement.setDate(2, order.getOrderDate());
            statement.setString(3, profile.getAddress());
            statement.setString(4, profile.getCity());
            statement.setString(5, profile.getState());
            statement.setString(6, profile.getZip());
            statement.setDouble(7, order.getShippingAmount());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated keys
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    // Retrieve the auto-incremented ID
                    int orderId = generatedKeys.getInt(1);

                    // get the newly inserted category
                    return order;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

   /* @Override
    public Order createLineItem(int userId) {
        Order order = getByUserId(userId);


        String query = "INSERT INTO order_line_items (order_id, product_id, sales_price, quantity, discount) " +
                " VALUES (?, ?, ?, ?, ?);";

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, order.getOrderId());
            statement.setInt(2, item.getProductId());
            statement.setBigDecimal(3, item.getLineTotal());
            statement.setInt(4, item.getQuantity());
            statement.setBigDecimal(5, item.getDiscountPercent());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Retrieve the generated keys
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    // Retrieve the auto-incremented ID
                    order.setLineId(generatedKeys.getInt(1));

                    // get the newly inserted category
                    return order;
                }
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    } */
    public ShoppingCartItem getItemById(int userId){
        ShoppingCartItem item = null;
        String query = "SELECT orders.order_id, orders.user_id, orders.order_date, " +
                "products.product_id, products.name, products.price, order_line_items.quantity, order_line_items.discount_percent " +
                "FROM orders " +
                "JOIN order_line_items ON orders.order_id = order_line_items.order_id " +
                "WHERE orders.user_id = ?";

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();

            while(results.next()){
                Order order = mapRow(results);

            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return item;
    }

    private Order mapRow(ResultSet row) throws SQLException {
        Order order = new Order();

        order.setOrderId(row.getInt("order_id"));
        order.setUserId(row.getInt("user_id"));

        Product product = new Product();
        product.setProductId(row.getInt("product_id"));
        product.setName(row.getString("name"));
        product.setPrice(row.getBigDecimal("price"));

        ShoppingCartItem item = new ShoppingCartItem(product);
        item.setQuantity(row.getInt("quantity"));
        item.setDiscountPercent(row.getBigDecimal("discount_percent"));

        // Add the item to the order
        order.addItem(item);

        return order;
    }

}
