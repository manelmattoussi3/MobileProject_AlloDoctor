package tn.esprit.doctormobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import tn.esprit.doctormobile.User.UserDashboard;

public class VerifyOtpActivity extends AppCompatActivity {


    private EditText inputCode0, inputCode1, inputCode2, inputCode3, inputCode4, inputCode5;
    private TextView textMobile;
    Button verifyBtn;
    String fullCode;
    ProgressBar progressBar;
    String verificationId;
    TextView resendCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        init();
        setupOTPInputs();

        getIntentExtra();

        resendCode();
    }


    private void init() {

        textMobile = findViewById(R.id.mobile_txt);

        textMobile.setText(String.format("+1-%s", getIntent().getStringExtra("mobile")));

        inputCode0 = findViewById(R.id.code_txt0);
        inputCode1 = findViewById(R.id.code_txt1);
        inputCode2 = findViewById(R.id.code_txt2);
        inputCode3 = findViewById(R.id.code_txt3);
        inputCode4 = findViewById(R.id.code_txt4);
        inputCode5 = findViewById(R.id.code_txt5);
        progressBar = findViewById(R.id.progressBar);
        verifyBtn = findViewById(R.id.button_verify);
        resendCode = findViewById(R.id.resend_otp);


    }


    //KIF N3amer code
    private void getIntentExtra() {
        verificationId = getIntent().getStringExtra("verificationId");

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //CONTROLE
                if (inputCode0.getText().toString().trim().isEmpty() ||
                        inputCode1.getText().toString().trim().isEmpty() ||
                        inputCode2.getText().toString().trim().isEmpty() ||
                        inputCode3.getText().toString().trim().isEmpty() ||
                        inputCode4.getText().toString().trim().isEmpty() ||
                        inputCode5.getText().toString().trim().isEmpty()) {

                    Toast.makeText(VerifyOtpActivity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                fullCode = inputCode0.getText().toString() + inputCode1.getText().toString()
                        + inputCode2.getText().toString() + inputCode3.getText().toString()
                        + inputCode4.getText().toString() + inputCode5.getText().toString();

                if (verificationId != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    verifyBtn.setVisibility(View.INVISIBLE);

                    //YVERIFI CODE EST CE QUE WSEL LEL NUM S7i7
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            fullCode


                    );
                    FirebaseAuth.getInstance().signInWithCredential(
                            phoneAuthCredential
                    ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);//
                            verifyBtn.setVisibility(View.VISIBLE);
                            if (task.isSuccessful()) {//KEN sms wselt w enti da5lt code s77i7 w clickt button
                                Intent intent = new Intent(getApplicationContext(),UserDashboard.class);
                                //KEN YVERIFI W YCONSOMI FI DES WEB SERVICES DISTANTS
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "The verification code entered was invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }



    //SET UP INPUTS addText Changed listener kif ybadl text ysir action
    private void setupOTPInputs() {
        inputCode0.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    //Y3awd yab3th code on cas ou d'échec
    private void resendCode() {
        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+216" + getIntent().getStringExtra("mobile"),
                        60, TimeUnit.SECONDS
                        , VerifyOtpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {


                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyOtpActivity.this, "message failed verification" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                newVerificationId = verificationId;
                                Toast.makeText(VerifyOtpActivity.this, "OTP Sent" + newVerificationId, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
