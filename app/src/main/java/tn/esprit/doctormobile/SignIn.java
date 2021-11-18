package tn.esprit.doctormobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.Session.SessionManager;
import tn.esprit.doctormobile.User.UserDashboard;

public class SignIn extends AppCompatActivity {

    Button btn_signIn;
    Button signInBtn;
    TextView edtphone;
    ImageView logo;
    LinearLayout linearLayout;
    TextInputLayout username,passowrd;
    MyDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        goToSignUp();

        onclickSignInbtn();
    }

    public SignIn(){
        databaseHelper = MyDatabaseHelper.DatabaseInstance(this);
    }

    public void init() {
        btn_signIn = findViewById(R.id.btn_sign);
        signInBtn = findViewById(R.id.btn_sign_in);
        //edtphone = findViewById(R.id.edtphone);
        username = findViewById(R.id.username);
        passowrd = findViewById(R.id.password);
        logo = findViewById(R.id.logo);
        linearLayout = findViewById(R.id.linearLayout);

    }




    //MEN SIGN IN ===> SIGN UP
    public void goToSignUp() {
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
    }



    //LOGIN
    private void login() {

        //CONTROL SAISIE
        if(username.getEditText().getText().toString().isEmpty() || passowrd.getEditText().getText().toString().isEmpty()) {
            Snackbar.make(linearLayout, getString(R.string.error_null_input_message), Snackbar.LENGTH_LONG).show();

        }

        else if (databaseHelper.checkUser(username.getEditText().getText().toString().trim()
                , passowrd.getEditText().getText().toString().trim())) {



            Toast.makeText(getApplicationContext(), "ROLEBYUSERNAME ="+databaseHelper.getRoleByUsername(username.getEditText().getText().toString().trim()), Toast.LENGTH_SHORT).show();

            String role = databaseHelper.getRoleByUsername(username.getEditText().getText().toString().trim());
            String fullname = databaseHelper.getFullNameByUsername(username.getEditText().getText().toString().trim());
            if(role.equals("DOCTOR")) {

                SessionManager.userLogin(username.getEditText().getText().toString().trim(),fullname,
                        passowrd.getEditText().getText().toString().trim(),role,getApplicationContext()
                );
                Intent accountsIntent = new Intent(SignIn.this, DashboardDoctor.class);
                startActivity(accountsIntent);

            }
            else {
                SessionManager.userLogin(username.getEditText().getText().toString().trim(),fullname,
                        passowrd.getEditText().getText().toString().trim(),role,getApplicationContext()
                );
                Intent accountsIntent = new Intent(SignIn.this, UserDashboard.class);
                startActivity(accountsIntent);
            }












        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(linearLayout, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }


    public void onclickSignInbtn(){
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
}