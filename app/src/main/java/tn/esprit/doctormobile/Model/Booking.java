package tn.esprit.doctormobile.Model;



public class Booking {

    private int id ,status;
    private String date,time,username,userphone,userdoctor;


    public Booking() {
    }

    public Booking(int id, String date, String time, String username, String userphone, String userdoctor, int status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.username = username;
        this.userphone = userphone;
        this.userdoctor = userdoctor;
        this.status = status;
    }
    public Booking(int id, String date, String time, String username, String userphone, int status) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.username = username;
        this.userphone = userphone;
        this.status = status;
    }

    public Booking( String date, String time, String username, String userphone, String userdoctor,int status) {
        this.status = status;
        this.date = date;
        this.time = time;
        this.username = username;
        this.userphone = userphone;
        this.userdoctor = userdoctor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getUserdoctor() {
        return userdoctor;
    }

    public void setUserdoctor(String userdoctor) {
        this.userdoctor = userdoctor;
    }
}
