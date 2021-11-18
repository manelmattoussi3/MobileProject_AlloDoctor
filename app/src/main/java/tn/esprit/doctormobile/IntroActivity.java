package tn.esprit.doctormobile;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import tn.esprit.doctormobile.Helper.SliderAdapter;
public class IntroActivity extends AppCompatActivity {


    ViewPager viewPager;
    LinearLayout dotsLayout;
    SliderAdapter sliderAdapter;
    Button btnStarted;
    TextView dots[];
    //ANIMATION
    Animation animation;
    //Current position
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //HOOKS
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);// 3aytlha linear layout 5ater bch nasn3 3 cercle w n7outha fhiom
        //Call Adapter
        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);// 7atit sliderAdapter fi west viewPAger
        btnStarted = findViewById(R.id.get_started_btn);
        btnStarted.setVisibility(View.INVISIBLE);
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

        //Go To Login
        goToLogin();
    }

    //3 points bch tbadl
    private void addDots(int position) {

        dots = new TextView[3];
        dotsLayout.removeAllViews();// pour assurer eno linearlayout fiha 7ata children
        for(int i = 0 ;i<dots.length ; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));//ya3mli cercle
            dots[i].setTextSize(35);//size

            dotsLayout.addView(dots[i]);//zid circle fi linear layout
        }
        if(dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }

    }

    ViewPager.OnPageChangeListener changeListener =  new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            currentPosition = position;

            if(position == 0)  {
                btnStarted.setVisibility(View.INVISIBLE);
            }
            else if(position == 1) {
                btnStarted.setVisibility(View.INVISIBLE);

            }
            else {
                animation = AnimationUtils.loadAnimation(IntroActivity.this,R.anim.bottom_animation);
                btnStarted.setAnimation(animation);
                btnStarted.setVisibility(View.VISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public void skip(View view) {

        startActivity(new Intent(getApplicationContext(),SignIn.class));
        finish();

    }
    public void next(View view) {
        viewPager.setCurrentItem(currentPosition +1);


    }
    public void goToLogin() {
        btnStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignIn.class));

            }
        });
    }
}