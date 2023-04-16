package dependencies.Model;

import dependencies.Controller.ConnectionController;

import java.sql.SQLException;
import java.util.ArrayList;

public class Reporting {
    private ArrayList<UserContract> userContracts;

    public Reporting(ConnectionController connectionController){
        try {
            userContracts = connectionController.getUserContractDAO().getAllUserContracts();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //Getters
    public ArrayList<UserContract> getUserContracts(){
        return userContracts;
    }
}
