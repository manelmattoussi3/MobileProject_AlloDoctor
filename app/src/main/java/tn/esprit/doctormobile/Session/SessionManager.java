package tn.esprit.doctormobile.Session;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences;
import android.widget.Toast;

import tn.esprit.doctormobile.Model.User;
import tn.esprit.doctormobile.SignUp;


public class SessionManager {


    public static final String MyPREFERENCES = "MyPrefs";
    public static final String ID = "idKey";
    public static final String USERNAME = "usernameKey";
    public static final String FULLNAME = "fullnameKey";
    public static final String PASSWORD = "passwordKey";
    public static final String ROLE = "roleKey";


    //USER LOGIN SharedPerferences heya session bch nsajlofiha data
    public static void userLogin(String username,String fullname, String password, String role, Context mCtx) {
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SessionManager.USERNAME, username);
        editor.putString(SessionManager.FULLNAME, fullname);
        editor.putString(SessionManager.PASSWORD, password);
        editor.putString(SessionManager.ROLE, role);
        editor.apply();//commit
    }

    //USER REGISTER
    public static void userRegister(User user, Context mCtx) {
        SharedPreferences sharedPreferences =
        mCtx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ID, user.getId());

        editor.putString(SessionManager.USERNAME, user.getUsername());
        editor.putString(SessionManager.PASSWORD, user.getPassword());
        editor.putString(SessionManager.FULLNAME, user.getFullname());
        editor.putString(SessionManager.ROLE, user.getRole());
        Toast.makeText(mCtx, "ROLEDATA ***=" + user.getRole(), Toast.LENGTH_SHORT).show();

        editor.apply();
    }


    //GET USER connectee
    public static User getUser(Context mCtx) {

        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//    public User(String username, String fullname, String phone, String password, String role) {

        return new User(
                //paramettre lowel ken e user fih valeur yraje3 e id si nn yraje3 chaine vide
                sharedPreferences.getInt(ID, -1),
                //paramettre lowel ken e user fih valeur yraje3 e USERNAME si nn yraje3 chaine vide
                sharedPreferences.getString(SessionManager.USERNAME, ""),
                //paramettre lowel ken e user fih valeur yraje3 e FULLNAME si nn yraje3 chaine vide
                sharedPreferences.getString(SessionManager.FULLNAME, ""),
                //paramettre lowel ken e user fih valeur yraje3 e PASSWORD si nn yraje3 chaine vide

                sharedPreferences.getString(SessionManager.PASSWORD, ""),
                //paramettre lowel ken e user fih valeur yraje3 e ROLE si nn yraje3 chaine vide

                sharedPreferences.getString(SessionManager.ROLE, "")

        );


    }


    //USER LOGOUT
    public static void userLogout(Context mCtx) {
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();//open session
        editor.clear();//nfargo l9athya men frigidaire
        editor.apply();//nsakro frigidaire
        mCtx.startActivity(new Intent(mCtx, SignUp.class));
    }

}
