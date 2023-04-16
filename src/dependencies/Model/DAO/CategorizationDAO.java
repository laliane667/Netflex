package dependencies.Model.DAO;
import dependencies.Controller.ConnectionController;
import dependencies.Model.Categorization;
import dependencies.Model.Category;
import dependencies.Model.Video;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategorizationDAO {
    private final Connection connection;

    public CategorizationDAO(Connection connection) {
        this.connection = connection;
    }

    public void addCategorization(Categorization categorization) throws SQLException {
        String query = "INSERT INTO VideoCategorizations (videosId, categoriesId) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, categorization.getVideoId());
            preparedStatement.setInt(2, categorization.getCategoryId());

            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<Integer> getAllCategoriesIds() throws SQLException {
        ArrayList<Integer> categoriesIds = new ArrayList<>();
        String query = "SELECT DISTINCT categoriesId FROM VideoCategorizations";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("categoriesId");
                    categoriesIds.add(id);
                }
            }
        }
        return categoriesIds;
    }

    public ArrayList<Category> getAllCategories(ConnectionController connectionController) throws SQLException{
        ArrayList<Category> categories = new ArrayList<>();
        ArrayList<Integer> categoriesIds = getAllCategoriesIds();
        for(Integer i : categoriesIds){
            categories.add(connectionController.getCategoryDAO().getCategoryById(i));
        }
        return  categories;
    }

    public void removeCategorization(int categorizationId) throws SQLException {
        String query = "DELETE FROM VideoCategorizations WHERE categorizationsId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, categorizationId);

            preparedStatement.executeUpdate();
        }
    }

    public boolean categorizationExists(int videoId, int categoryId) throws SQLException {
        String query = "SELECT * FROM VideoCategorizations WHERE videosId = ? AND categoriesId = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, videoId);
            preparedStatement.setInt(2, categoryId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public int[] getAllVideosInCategory(Category category) throws SQLException {
        ArrayList<Integer> videoIds = new ArrayList<>();

        String query = "SELECT videosId FROM VideoCategorizations WHERE categoriesId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, category.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int videoId = resultSet.getInt("videosId");
                videoIds.add(videoId);
            }
        }

        // Convert the List<Integer> to int[]
        int[] idsArray = new int[videoIds.size()];
        for (int i = 0; i < videoIds.size(); i++) {
            idsArray[i] = videoIds.get(i);
        }

        return idsArray;
    }

}
