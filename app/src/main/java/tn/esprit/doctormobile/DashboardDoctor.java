package tn.esprit.doctormobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import tn.esprit.doctormobile.Session.SessionManager;

public class DashboardDoctor extends AppCompatActivity {

    CardView booking_status,logout_cv,profile_cv;
    CircleImageView userImageView;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboar_doctor);
        booking_status = findViewById(R.id.booking_status);
        username = findViewById(R.id.user_name);
        userImageView = findViewById(R.id.user_photo);
        booking_status = findViewById(R.id.booking_status);
        profile_cv = findViewById(R.id.profile_cv);
        logout_cv = findViewById(R.id.logout_cv);

        username.setText(SessionManager.getUser(getApplicationContext()).getUsername().trim());

        if( SessionManager.getUser(getApplicationContext()).getImage()==null) {

            userImageView.setImageResource(R.drawable.account_profile);

        }
        profile_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardDoctor.this,DoctorProfile.class));
            }
        });


            booking_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardDoctor.this,BStatusActivity.class));
            }
        });
        logout_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManager.userLogout(DashboardDoctor.this);
                startActivity(new Intent(DashboardDoctor.this,SignIn.class));
            }
        });

    }
}