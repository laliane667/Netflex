package dependencies.Model.DAO;
import dependencies.Model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Connection connection;

    public CategoryDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categoriesList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM categories");

            while (rs.next()) {
                int categoryId = rs.getInt("categoriesId");
                String categoryName = rs.getString("categoriesName");
                String categoryDescription = rs.getString("categoriesDescription");
                Category category = new Category(categoryId, categoryName, categoryDescription);
                categoriesList.add(category);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categoriesList;
    }


    public Category getCategoryById(int id) throws SQLException {
        String query = "SELECT * FROM categories WHERE categoriesId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("categoriesName");
                    String description = resultSet.getString("categoriesDescription");
                    Category category = new Category(id, name, description);
                    return category;
                } else {
                    return null;
                }
            }
        }
    }
    public Category getCategoryByName(String name) throws SQLException {
        String query = "SELECT * FROM categories WHERE categoriesName = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("categoriesId");
                    String description = resultSet.getString("categoriesDescription");
                    Category category = new Category(id, name, description);
                    return category;
                } else {
                    return null;
                }
            }
        }
    }


    public void addCategory(Category category) throws SQLException {
        String query = "INSERT INTO categories (categoriesName, categoriesDescription) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.executeUpdate();
        }
    }

    public void updateCategory(Category category) throws SQLException {
        String query = "UPDATE categories SET categoriesName = ?, categoriesDescription = ? WHERE categoriesId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getId());
            statement.executeUpdate();
        }
    }

    public void deleteCategory(int id) throws SQLException {
        String query = "DELETE FROM categories WHERE categoriesId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
