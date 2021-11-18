package tn.esprit.doctormobile.Model;



public class User {
    private int id;
    private String username;
    private String fullname;
    private String phone;
    private String password;
    private String role;
    private byte[] image;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public User(){

    }

    public User(int id, String username, String fullname, String Phone, String password,String role) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.fullname = fullname;
        this.phone = Phone;
        this.password = password;
    }

    public User(int id,String username, String fullname, String password, String role) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    public User(int id, String username, String fullname,  String password, String role, byte[] image) {
        this.username = username;
        this.fullname = fullname;
        this.id = id;
        this.password = password;
        this.role = role;
        this.image = image;
    }
    public User(String username, String fullname, String password) {
        this.username = username;
        this.fullname = fullname;
        this.phone = phone;
        this.password = password;

    }

    public User(String username, String fullname, String password, String role) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
    }
    public User(String username, String fullname, String password, byte[]image) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.image = image;
    }


    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}