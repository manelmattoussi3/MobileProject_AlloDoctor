package tn.esprit.doctormobile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView image;

    TextView logo,slogan;

    Animation topAnim,bottomAnim;
    //Session
    SharedPreferences onIntroScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
        anim();
        callNewActivity();

    }


    //INIT ===> Recuperation des composant(Mapping)
    public void init() {
        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);
    }
    //ANIM :TOP ANIMATION : TITLE yji men issar lel imin
    //     BOTTOM ANIMATION : IMAGE tji men imin lel issar
    public void anim() {
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);
    }
    //BCH Yet3ada men splash screen lel intro ken awl mara nranio app sinon ken akther men mara
    //yet3ada lel login
    public void callNewActivity() {
        //Calling New Activity after SPLASH_SCREEN seconds 1s = 1000
        new Handler().postDelayed(new Runnable() {



                                      @Override
                                      public void run() {
                                          onIntroScreen =  getSharedPreferences("onIntroScreen",MODE_PRIVATE);
                                          boolean isFirstTime = onIntroScreen.getBoolean("firstTime",true);

                                          if(isFirstTime) {

                                              SharedPreferences.Editor editor = onIntroScreen.edit();
                                              editor.putBoolean("firstTime",false);
                                              editor.commit();

                                              Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
                                              startActivity(intent);
                                              finish();
                                          }
                                          else {
                                              Intent intent = new Intent(SplashScreen.this, IntroActivity.class);
                                              Pair[] pairs = new Pair[2];
                                              pairs[0] = new Pair<View,String>(image,"logo_image");
                                              pairs[1] = new Pair<View,String>(logo,"logo_text");

                                              ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);

                                              startActivity(intent,options.toBundle());
                                          }

                                      }
                                  }, //Pass time here
                3000 );
    }
}

