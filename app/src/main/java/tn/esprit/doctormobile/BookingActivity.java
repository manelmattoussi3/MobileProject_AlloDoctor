package tn.esprit.doctormobile;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
;import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.Model.Booking;
import tn.esprit.doctormobile.Session.SessionManager;

public class BookingActivity extends AppCompatActivity {
    CardView ToDate,ToTime;
    TextView calendarTv,timeTv,usernameDoctor;
    private SimpleDateFormat dateFormatter;
    Button SendButton;
    Button sendBtn;
    String FilterToDate,FilterFromDate;

    TextView fullnameTv,usernameTv;
    MyDatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        db= MyDatabaseHelper.DatabaseInstance(getApplicationContext());

        ToDate = findViewById(R.id.calendar_cv);
        ToTime = findViewById(R.id.time_cv);
        usernameDoctor = findViewById(R.id.username_doctor);
        calendarTv = findViewById(R.id.calendar);
        timeTv = findViewById(R.id.time);
        sendBtn = findViewById(R.id.send_book_btn);
        usernameTv = findViewById(R.id.input_username);
        fullnameTv = findViewById(R.id.input_fullname);
        ToDate.requestFocus();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        openCalendar();

        openTime();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBooking();
            }
        });


        //GET DATA OF PATIENT CURRENT
        getUserCurrent();

        //GET DOCTOR USERNAME THAT YOU SEND BOOK REQUESt
        String doctorusername = getIntent().getStringExtra("USERNAME_DOCTOR_BOOK");
        usernameDoctor.setText(doctorusername);


    }
    //OPEN CALENDAR
    public void openCalendar(){
        ToDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(Locale.getDefault());
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                //todo
                                Calendar newDate = Calendar.getInstance();// date ely5tarto
                                newDate.set(year, month, dayOfMonth);//5thit meno y-m-d
                                calendarTv.setText(dateFormatter.format(newDate.getTime()));//affichi fi input date
                                Toast.makeText(getApplicationContext(),
                                        dateFormatter.format(newDate.getTime())
                                        , Toast.LENGTH_SHORT).show();


                            }
                        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
    }

    //OPEN TIME
    public  void openTime(){
        ToTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeTv.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }
    //SAVE BOOKING TO SQLITE
    public void addBooking(){

        //CONTROLE SAISIE
        if(calendarTv.getText().toString().isEmpty() || timeTv.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "DATA REQUIRED", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            Toast.makeText(getApplicationContext(), "DOCTOR="+usernameDoctor.getText()
                    .toString().trim(), Toast.LENGTH_SHORT).show();
            Booking booking = new Booking(calendarTv.getText().toString().trim(),
                    timeTv.getText().toString().trim(),
                    usernameTv.getText().toString().trim(),
                    "50709483",
                    usernameDoctor.getText().toString().trim(),
                    0
            );
            db.addBooking(booking);
            Toast.makeText(getApplicationContext(), "BOOK APPOINTMENT SENT SUCCESSFULLY TO DOCOTR", Toast.LENGTH_SHORT).show();
        }

    }
//flesh eli yarje3 b twaliii
    @Override
    protected void onStart() {
        super.onStart();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);    }


    //GET USER CURRENT
    public void getUserCurrent(){
        usernameTv.setText(SessionManager.getUser(getApplicationContext()).getUsername().trim().toString());
        fullnameTv.setText(SessionManager.getUser(getApplicationContext()).getFullname().trim().toString());


    }
}