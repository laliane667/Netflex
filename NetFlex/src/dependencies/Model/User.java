package dependencies.Model;

import dependencies.Model.DAO.UserDAO;

import java.sql.Date;

public class User {
    private int id;
    private String name;
    private String uid;
    private String password;
    private String email;
    private Date dateOfBirth;

    public User(String uid, String name, String email, String password, Date dateOfBirth){
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }

    //Getters
    public int getUserId(){return  id;}
    public String getUserUID(){return uid;}
    public String getUserName(){return name;}
    public String getUserEmail(){return email;}
    public String getUserPassword(){return password;}
    public Date getUserDateOfBirth(){return dateOfBirth;}

}
