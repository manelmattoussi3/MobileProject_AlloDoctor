package tn.esprit.doctormobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.nio.file.Files;

import de.hdodenhof.circleimageview.CircleImageView;
import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.Model.User;
import tn.esprit.doctormobile.Session.SessionManager;

public class SignUp extends AppCompatActivity {
    Button btn_signin,btnSignUp;
    TextView patient,doctor;

    TextInputLayout username,password,fullname;
    private MyDatabaseHelper myDatabaseHelper;
    LinearLayout linearLayout;

    CircleImageView doctorImg,patientImg;


    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //Get  singleton instance
        myDatabaseHelper  = MyDatabaseHelper.DatabaseInstance(SignUp.this);
        init();
        //setPhoneWith216();
        goToSignUp();
        onClickRegisterBtn();
        //PAR DEFAUT BLUE TO PATIENT /DOCTOR IMAGE

        patientImg.setBorderColor(this.getResources().getColor(R.color.colorPrimary));
        patientImg.setBorderWidth(5);
        doctorImg.setBorderColor(this.getResources().getColor(R.color.colorPrimary));
        doctorImg.setBorderWidth(5);


        //CHANGE COLOR WHEN CLICK PATIENT
        patientImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBorderImg(patientImg,true);
                patient.setTextColor(getResources().getColor(R.color.purple_200));
                setBorderImg(doctorImg,false);
                doctor.setTextColor(getResources().getColor(R.color.colorPrimary));

            }
        });

        doctorImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBorderImg(doctorImg,true);
                doctor.setTextColor(getResources().getColor(R.color.purple_200));
                setBorderImg(patientImg,false);
                patient.setTextColor(getResources().getColor(R.color.colorPrimary));

            }
        });


    }

    public void init() {
        btn_signin = findViewById(R.id.btn_signin);
        username = findViewById(R.id.edtusername);
        fullname = findViewById(R.id.edtfullname);
        password = findViewById(R.id.edtPassword);
        linearLayout = findViewById(R.id.linearLayout);
        btnSignUp = findViewById(R.id.btnSignIn);
        doctorImg = findViewById(R.id.doctor_img);
        patientImg = findViewById(R.id.patient_img);
        patient = findViewById(R.id.patient_txt);
        doctor = findViewById(R.id.doctor_txt);
           //USER
      user = new User();
    }
//
//    public void setPhoneWith216() {
//        edtPhone.setText("+216");
//        //edtPhone.setTextColor(this.getResources().getColor(R.color.grey_hard));
//
//
//
//    }

    public void goToSignUp() {
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });
    }


    //si awel mara yhezni lil otp sinon li signin
    public void onClickRegisterBtn() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                signUp();

                SharedPreferences sharedPreferences = getSharedPreferences("onotop", MODE_PRIVATE);
                boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);


                if (isFirstTime) {

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstTime", false);
                    editor.commit();

                    Intent intent = new Intent(SignUp.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    startActivity(new Intent(SignUp.this,SignIn.class));
                }

            }


        });

    }
    public void signUp() {
        //if : to check if user exist or not if true then you can't register otherwise you can
        if(!myDatabaseHelper.checkUser(username.getEditText().getText().toString(),password.getEditText().
                getText().toString())) {

            //SET USER OBJECT WITH INPUT DATA
            user.setFullname(fullname.getEditText().getText().toString().trim());
            user.setUsername(username.getEditText().getText().toString().trim());
            user.setPassword(password.getEditText().getText().toString().trim());

            //ROLE
            if(patient.getCurrentTextColor()== getResources().getColor(R.color.purple_200)) {
                user.setRole(patient.getText().toString().trim());
                //SNA3t user 7atit
                user = new User(username.getEditText().getText().toString().trim()
                        ,fullname.getEditText().getText().toString().trim(),
                        password.getEditText().getText().toString().trim(),
                        user.getRole());
                //7atito fi session
                SessionManager.userRegister(user,SignUp.this);
            }
            else {
                user.setRole(doctor.getText().toString().trim());
                //SNA3t user 7atit
                user = new User(username.getEditText().getText().toString().trim()
                        ,fullname.getEditText().getText().toString().trim(),
                        password.getEditText().getText().toString().trim(),
                        user.getRole());
                //7atito fi session
                SessionManager.userRegister(user,SignUp.this);
            }

            //ADD USER TO DATABASE OFFLINE
            myDatabaseHelper.addUser(user);
          //TOAST MESSAGE
            // Toast.makeText(getApplicationContext(), "RETURN"+user, Toast.LENGTH_LONG).show();

            Snackbar.make(linearLayout, "USER CREATED ", Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

        }
        else {

            Toast.makeText(getApplicationContext(), "USER ALREADY EXIST", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        fullname.getEditText().setText("");
        username.getEditText().setText("");
        password.getEditText().setText("");

    }

    //CHANGE BORDER IMAGE COLEUR KIF NE55tarha
    public void setBorderImg(CircleImageView circularImageView, boolean selected) {

        // Set Border
        if(selected) {
            circularImageView.setBorderColor(this.getResources().getColor(R.color.purple_200));
            circularImageView.setBorderWidth(5);
        }
        else
        {
            circularImageView.setBorderColor(this.getResources().getColor(R.color.colorPrimary));
            circularImageView.setBorderWidth(5);

        }

}}