package org.yearup.data.mysql.impl;

import org.springframework.stereotype.Component;
import org.yearup.data.mysql.interfaces.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    private DataSource dataSource;

    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String allCatQuery = "SELECT * FROM categories";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement allStatement = connection.prepareStatement(allCatQuery);
            ResultSet results = allStatement.executeQuery()){

            while(results.next()){
                int idFromDb = results.getInt("category_id");
                String name = results.getString("name");
                String description = results.getString("description");

                categories.add(new Category(idFromDb, name, description));
            }
        }catch(SQLException e){e.printStackTrace();}

        return categories;
    }

    @Override
    public Category getById(int categoryId)
    {
        // get category by id
        return null;
    }

    @Override
    public Category create(Category category)
    {
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
