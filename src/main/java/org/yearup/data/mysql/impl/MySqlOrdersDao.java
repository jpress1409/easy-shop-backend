package org.yearup.data.mysql.impl;

import org.springframework.stereotype.Component;
import org.yearup.data.mysql.interfaces.OrdersDao;
import org.yearup.models.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
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


        String query = "SELECT orders.order_id, orders.user_id, orders.date, " +
                "products.product_id, products.name, products.price, order_line_items.quantity, order_line_items.discount " +
                "FROM orders " +
                "JOIN order_line_items ON orders.order_id = order_line_items.order_id " +
                "JOIN products ON order_line_items.product_id = products.product_id " +
                "WHERE orders.user_id = ?";

        Order order = null;

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet results = statement.executeQuery();

            while (results.next()) order.setUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }


    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders(user_id, date, address, city, state, zip, shipping_amount) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
            statement.setInt(1, order.getUserId());
            statement.setDate(2, order.getOrderDate());
            statement.setString(3, order.getAddress());
            statement.setString(4, order.getCity());
            statement.setString(5,order.getState());
            statement.setString(6,order.getZip());
            statement.setBigDecimal(7,order.getShippingAmount());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    order.setOrderId(orderId);
                    return order;
                }
            }
        }
        catch(SQLException e){
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }
}
