package tn.esprit.doctormobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailedDoctorActivity extends AppCompatActivity {

    Button bookBtn;
    CircleImageView imageDoctor;
    TextView usernameDoctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_doctor);
        bookBtn=findViewById(R.id.book_app_btn);
        imageDoctor=findViewById(R.id.profile_doctor);
        usernameDoctor=findViewById(R.id.name);


        //SET DATA DOCTOR CURRENT
        setData();



        //ONCLICK BUTTON
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailedDoctorActivity.this,BookingActivity.class);

                //SEND DOCTOR USERNAME to Booking activity
                intent.putExtra("USERNAME_DOCTOR_BOOK",usernameDoctor.getText().toString());
                //SEND IMAGE
                //image.buildDrawingCache();
                //Bitmap bitmap = image.getDrawingCache();

                //intent.putExtra("PICTURE_DOCTOR", bitmap);
                view.getContext().startActivity(intent);
                startActivity(intent);
            }
        });

    }


    //SET DATA ==> bch trecuperi username/doctor dynamic men featuredadapter ely 3aytlnalo fi homefragment
    private void setData() {
        String username = getIntent().getStringExtra("USERNAME_DOCTOR");
        usernameDoctor.setText(username);
        Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("PICTURE_DOCTOR");
        imageDoctor.setImageBitmap(bitmap);

    }
}