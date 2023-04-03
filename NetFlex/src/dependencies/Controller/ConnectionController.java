package dependencies.Controller;

import dependencies.Model.DAO.UserDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Date;

public class ConnectionController {
    Connection connection = null;
    private UserDAO userDAO = null;
    private final String url = "jdbc:mysql://localhost:8889/NetFlex";
    private final String username = "root";
    private final String password = "root";
    public ConnectionController(){

        // Nouvel utilisateur à insérer dans la base de données
        //User user = new User("John Doe", "john.doe@example.com", "password123", new Date(2000, 1, 1));

        try {
            connection = DriverManager.getConnection(url, username, password);
            userDAO = new UserDAO(connection);
            System.out.println("Connection success");
            // Création d'une instance de la classe DAO
            //UserDaoInterface userDao = new UserDAO(connection);

            // Insertion de l'utilisateur dans la base de données
            //userDao.insertUser(user);

            // Fermeture de la connexion
            //connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            System.out.println("Closing connection");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getters
    public Connection getConnection(){return connection;}

    public UserDAO getUserDAO() {return userDAO;}
}
