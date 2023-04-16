package dependencies.Model;

import dependencies.Model.DAO.UserDAO;

import java.sql.Date;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String uid;
    private String password;
    private Date dateOfBirth;
    private boolean privilege;

    public User(String firstName, String lastName, String email, String uid, String password, Date dateOfBirth, boolean privilege){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.uid = uid;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.privilege = privilege;
    }

    public User(int id, String firstName, String lastName, String email, String uid, String password, Date dateOfBirth, boolean privilege){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.uid = uid;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.privilege = privilege;
    }


    //Getters
    public int getUserId(){return  id;}
    public String getUserFirstName(){return firstName;}
    public String getUserLastName(){return lastName;}
    public String getUserEmail(){return email;}
    public String getUserUID(){return uid;}
    public String getUserPassword(){return password;}
    public Date getUserDateOfBirth(){return dateOfBirth;}
    public boolean getUserPrivilege(){return privilege;}
}
