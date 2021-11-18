package tn.esprit.doctormobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class SendOtpActivity extends AppCompatActivity   {
    EditText inputMobileNumber, inputOtp;
    Button btnGetOtp, btnVerifyOtp,verifyotp;
    ConstraintLayout layoutInput, layoutVerify;

    EditText inputMobile;
    TextView textMobile;

    Button getOtpBtn;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);




        initViews();

        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {


                //KENO EMPTY YRAJA3 alert
                if(inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SendOtpActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                //LOAD
                progressBar.setVisibility(View.VISIBLE);
                btnGetOtp.setVisibility(View.INVISIBLE);

                //60 = temps maximal ken yfouto m3ach yab3th
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+216"+inputMobile.getText().toString(),
                        60, TimeUnit.SECONDS
                        ,SendOtpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btnGetOtp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                btnGetOtp.setVisibility(View.VISIBLE);
                                Toast.makeText(SendOtpActivity.this, "message failed verification"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                btnGetOtp.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(),VerifyOtpActivity.class);
                                intent.putExtra("mobile",inputMobile.getText().toString());
                                intent.putExtra("verificationId",verificationId);
                                startActivity(intent);
                            }
                        });




            }
        });


    }

    private void initViews() {


        inputMobile = findViewById(R.id.input_mobile);
        btnGetOtp = findViewById(R.id.get_otp_btn);
        progressBar = findViewById(R.id.progressBar);



    }



}