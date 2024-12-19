package org.yearup.data.mysql.impl;

import org.springframework.stereotype.Component;
import org.yearup.data.mysql.interfaces.ProfileDao;
import org.yearup.models.Category;
import org.yearup.models.Profile;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setInt(1, profile.getUserId());
            statement.setString(2, profile.getFirstName());
            statement.setString(3, profile.getLastName());
            statement.setString(4, profile.getPhone());
            statement.setString(5, profile.getEmail());
            statement.setString(6, profile.getAddress());
            statement.setString(7, profile.getCity());
            statement.setString(8, profile.getState());
            statement.setString(9, profile.getZip());

            statement.executeUpdate();

            return profile;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile getById(int userId) {
        Profile profile = null;
        String getByIdQuery = "SELECT * FROM profiles WHERE user_id = ?";

        try(Connection connection = getConnection();
            PreparedStatement getByIdStatement = connection.prepareStatement(getByIdQuery)){
            getByIdStatement.setInt(1, userId);

            try(ResultSet results = getByIdStatement.executeQuery()){
                if(results.next()){
                    return mapRow(results);

                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return profile;
    }
    @Override
    public void update(int userId, Profile profile) {
        String sql = "UPDATE profiles SET first_name = ?, last_name = ?, phone = ?, email = ?, address = ?, city = ?, state = ?, zip = ?  WHERE user_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {


            statement.setString(1, profile.getFirstName());
            statement.setString(2, profile.getLastName());
            statement.setString(3, profile.getPhone());
            statement.setString(4, profile.getEmail());
            statement.setString(5, profile.getAddress());
            statement.setString(6, profile.getCity());
            statement.setString(7, profile.getState());
            statement.setString(8, profile.getZip());
            statement.setInt(9, userId);

            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error updating user profile", e);
        }
    }
    private Profile mapRow(ResultSet row) throws SQLException
    {

        int userId = row.getInt("user_id");
        String firstName = row.getString("first_name");
        String lastName = row.getString("last_name");
        String phone = row.getString("phone");
        String email = row.getString("email");
        String address = row.getString("address");
        String city = row.getString("city");
        String state = row.getString("state");
        String zip = row.getString("zip");

        return new Profile(userId,firstName,lastName,phone,email,address,city,state,zip);
    }
}
