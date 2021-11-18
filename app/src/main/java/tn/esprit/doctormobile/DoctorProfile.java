package tn.esprit.doctormobile;



import static android.app.Activity.RESULT_OK;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat ;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import de.hdodenhof.circleimageview.CircleImageView;
import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.Database.Utils;
import tn.esprit.doctormobile.Model.User;
import tn.esprit.doctormobile.R;
import tn.esprit.doctormobile.Session.SessionManager;
import tn.esprit.doctormobile.User.UserDashboard;

public class DoctorProfile extends AppCompatActivity implements View.OnClickListener {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int SELECT_PHOTO = 1;
    private static final int CAPTURE_PHOTO = 2;
    private ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarbHandler = new Handler();
    private boolean hasImageChanged = false;
    Button updatePicBtn,updateBtn;
    TextView usernameprofile;
    TextInputEditText usernameET,fullnameET,passwordEt;

    Bitmap downloadImage;

    Bitmap thumbnail,bitmap;




    TextView backBtn;
    public CircleImageView profileImgView;

    //ON  CREATE VIEW BCH LEL FRAGMENT XML W y7oto fi frameLayout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        // Inflate the layout for this fragment

        //
        init();

        permission();

        String username = SessionManager.getUser(getApplicationContext()).getUsername().toString().trim();
        String password = SessionManager.getUser(getApplicationContext()).getPassword().toString().trim();




        updatePicBtn.setOnClickListener(this);

        loadImageFromDB();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DashboardDoctor.class));
            }
        });

//
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        toolbar.setTitle("Profile");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        setSupportActionBar(toolbar);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Book Detail");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

    }


    public void init(){
        backBtn = findViewById(R.id.back_home);
        profileImgView = findViewById(R.id.profile_iv);
        updatePicBtn =findViewById(R.id.update_pric_btn);
        fullnameET =findViewById(R.id.full_name_profile);
        usernameET = findViewById(R.id.username_profile);
        passwordEt = findViewById(R.id.password_profile);
        updateBtn = findViewById(R.id.update_profile_btn);
        usernameprofile = findViewById(R.id.usernameprofile);



        usernameprofile.setText(SessionManager.getUser(getApplicationContext()).getUsername().toString().trim());


        fullnameET.setText(SessionManager.getUser(getApplicationContext()).getUsername().toString().trim());
        usernameET.setText(SessionManager.getUser(getApplicationContext()).getUsername().toString().trim());
        passwordEt.setText(SessionManager.getUser(getApplicationContext()).getPassword().toString().trim());




        if( SessionManager.getUser(getApplicationContext()).getImage()!=null) {
            Toast.makeText(getApplicationContext(), "IMAGE="+
                            SessionManager.getUser(getApplicationContext()).getImage().toString().trim()
                    , Toast.LENGTH_SHORT).show();
        }

        updateBtn.setEnabled(false);
        updateBtn.setTextColor(getResources().getColor(R.color.black));
        updateBtn.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        changeInputs();

        //ONCLICK SAVE DATA BTN
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileImgView.setDrawingCacheEnabled(true);
                profileImgView.buildDrawingCache();
                Bitmap bitmap = profileImgView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                Toast.makeText(getApplicationContext(), "dataimage="+data, Toast.LENGTH_SHORT).show();

                User user = new User(usernameET.getText().toString().trim()
                        ,usernameET.getText().toString().trim()
                        ,passwordEt.getText().toString().trim(),
                        data
                );

                MyDatabaseHelper.DatabaseInstance(getApplicationContext()).updateUser(
                        SessionManager.getUser(getApplicationContext()).getUsername().toString().trim(),
                        SessionManager.getUser(getApplicationContext()).getPassword().toString().trim(),

                        user);
                Toast.makeText(getApplicationContext(), "DATA PROFILE CHANGED WITH SUCCESS" +
                        "" +
                        "", Toast.LENGTH_SHORT).show();

                //reset shared
                SharedPreferences sharedPreferences =
                        getApplicationContext().getSharedPreferences(SessionManager.MyPREFERENCES, Context.MODE_PRIVATE);


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(SessionManager.USERNAME, usernameET.getText().toString().trim());
                editor.putString(SessionManager.FULLNAME, usernameET.getText().toString().trim());
                editor.putString(SessionManager.PASSWORD, passwordEt.getText().toString().trim());

                editor.apply();

            }
        });



    }


    private void changeInputs() {
        fullnameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    updateBtn.setEnabled(true);
                    updateBtn.setTextColor(getResources().getColor(R.color.white));
                    updateBtn.setBackground(getResources().getDrawable(R.drawable.btn_rounded_login));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        usernameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (!s.toString().trim().isEmpty()) {
                    updateBtn.setEnabled(true);
                    updateBtn.setTextColor(getResources().getColor(R.color.white));
                    updateBtn.setBackground(getResources().getDrawable(R.drawable.btn_rounded_login));                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateBtn.setEnabled(true);
                updateBtn.setTextColor(getResources().getColor(R.color.white));
                updateBtn.setBackground(getResources().getDrawable(R.drawable.btn_rounded_login));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });
    }






    public void permission(){

        //PERMISSION D'ACCESS AU MEDIA DE TELEPHONE
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
        ) {
            profileImgView.setEnabled(false);

            ActivityCompat.requestPermissions(DoctorProfile.this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_CAMERA_REQUEST_CODE);
        }


        else {
            profileImgView.setEnabled(true);

        }
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.update_pric_btn) {
            new MaterialDialog.Builder(DoctorProfile.this)
                    .title("Set your image")
                    .items(R.array.uploadImages)
                    .itemsIds(R.array.itemIds)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            switch (position) {
                                //KEN 0 ==> CONSULTER GALLER
                                case 0:
                                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                    photoPickerIntent.setType("image/*");
                                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                                    break;
                                //CONSULER CAMERA
                                case 1:

                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, CAPTURE_PHOTO);
                                    break;
                                //REMOVE PICTURE
                                case 2:
                                    profileImgView.setImageResource(R.drawable.account_profile);
                                    break;
                            }
                        }
                    })
                    .show();
        } else {
            throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }



    //KIF NE5TAR PHOTO W NSAJEL TASWIRA TETBA3TH MEN CAMERA/GALLERIE lel app
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                profileImgView.setEnabled(true);

                updateBtn.setEnabled(true);
                updateBtn.setTextColor(getResources().getColor(R.color.white));
                updateBtn.setBackground(getResources().getDrawable(R.drawable.btn_rounded_login));
            }
        }
    }



    //YLOADI BA3d MAN5ater PICTURE
    public void setProgressBar(){
        progressBar = new ProgressDialog(DoctorProfile.this);
        progressBar.setCancelable(true);
        progressBar.setMessage("Please wait...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
        progressBarStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus < 100){
                    progressBarStatus += 30;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);
                        }
                    });
                }
                if (progressBarStatus >= 100) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBar.dismiss();

                }

            }
        }).start();
    }


    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");


        //set profile picture form camera and save it
        profileImgView.setMaxWidth(200);

        profileImgView.setImageBitmap(thumbnail);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataByte = baos.toByteArray();
        MyDatabaseHelper.DatabaseInstance(getApplicationContext()).addDoctorPicture(
                usernameET.getEditableText().toString()
                ,dataByte);

    }



    //KIF  NJI PICTURE N3ayt lel addDoctorPicture bch nzidha fi base ama kbal lazm picture n7awlha bitmap
    //ba3d n7awlha lel byte[] bch fi base tetsajl ka blob
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == SELECT_PHOTO){
            if(resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


                    //set profile picture form gallery
                    profileImgView.setImageBitmap(selectedImage);


                    profileImgView.setDrawingCacheEnabled(true);
                    profileImgView.buildDrawingCache();
                    Bitmap bitmap = profileImgView.getDrawingCache();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] dataByte = baos.toByteArray();
                    MyDatabaseHelper.DatabaseInstance(getApplicationContext()).addDoctorPicture(
                            usernameET.getEditableText().toString()
                            ,dataByte);
                    setProgressBar();




                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"EROR="+e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

        }else if(requestCode == CAPTURE_PHOTO){
            if(resultCode == RESULT_OK) {
                setProgressBar();

                onCaptureImageResult(data);


            }
        }
    }

    void loadImageFromDB() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final byte[] bytes = MyDatabaseHelper.DatabaseInstance(getApplicationContext()).retrieveImageFromDB(usernameET.getEditableText().toString());
                    // Show Image from DB in ImageView

                    profileImgView.post(new Runnable() {
                        @Override
                        public void run() {

                            if(bytes!=null)
                                //getImage(byte) ==> jebnha men utils
                                profileImgView.setImageBitmap(Utils.getImage(bytes));
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"<loadImageFromDB> Error : " + e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                }
            }
        }).start();
    }


}
