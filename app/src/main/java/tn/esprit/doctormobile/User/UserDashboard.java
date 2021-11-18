package tn.esprit.doctormobile.User;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.daimajia.slider.library.SliderLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import tn.esprit.doctormobile.DetailedDoctorActivity;
import tn.esprit.doctormobile.Fragment.HistoryFragment;
import tn.esprit.doctormobile.Fragment.HomeFragment;
import tn.esprit.doctormobile.Fragment.ProfileFragment;
import tn.esprit.doctormobile.Fragment.SettingFragment;
import tn.esprit.doctormobile.HelperClasses.HomeAdapter.FeaturedAdapter;
import tn.esprit.doctormobile.Model.Doctor;
import tn.esprit.doctormobile.Model.User;
import tn.esprit.doctormobile.R;
import tn.esprit.doctormobile.Session.SessionManager;
import tn.esprit.doctormobile.SignIn;

public class UserDashboard extends AppCompatActivity    implements
        ChipNavigationBar.OnItemSelectedListener ,NavigationView.OnNavigationItemSelectedListener {

    TextView username;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;
    ChipNavigationBar navigationBar;
    TextView txtFullName;
    private SliderLayout mDemoSlider;
    static final float END_SCALE = 0.7f;
    ArrayList<Doctor> mArrayList = new ArrayList<>();

    FeaturedAdapter mAdapter;
    RecyclerView rvNearbydoctr;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_user_dashboard);
        navigationBar = findViewById(R.id.bottom_nav);


        navigationBar = findViewById(R.id.bottom_nav);
        navigationBar.setOnItemSelectedListener(this);

        drawer= findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuIcon = findViewById(R.id.menu_icon);
        contentView =findViewById(R.id.content);
        username = findViewById(R.id.username_navigation);
        //CAROUSEL
        // carousel();

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        //set Name for user
        //View headerView = navigationView.getHeaderView(0);
        loadFragment(new HomeFragment());



        navigationDrawer();

    }

    //NAVIAGATION MENU

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawer.isDrawerVisible(
                        GravityCompat.START
                )){
                    drawer.closeDrawer(
                            GravityCompat.START
                    );
                }else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        animateNavigationDrawer();
    }


    private void animateNavigationDrawer() {
        drawer.setScrimColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });
    }



    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    //navigation entre les menus
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            loadFragment(new ProfileFragment());
        } else if (id == R.id.nav_home) {
            loadFragment(new HomeFragment());

        } else if (id == R.id.nav_history) {
            loadFragment(new HistoryFragment());





        } else if (id == R.id.nav_signout) {
            SessionManager.userLogout(UserDashboard.this);
            startActivity(new Intent(UserDashboard.this, SignIn.class));




        }



        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    protected void onStop() {
        super.onStop();
        //mDemoSlider.stopAutoCycle();
    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater  inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bottom,menu);
        return true;
    }

    //FRAGMENT
    public void loadFragment(Fragment fragment) {

        if(fragment != null) {
            //REPLACE CONTENUE TA3 ACTIVITY B FRAGMENT ELYENTI 7alito
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit(); }
        else {
            Toast.makeText(this, "error fragment", Toast.LENGTH_SHORT).show();
        }
    }



    //KIF TE5tar fragment yijbo  fi blast contentu ta3 acitivity
    @Override
    public void onItemSelected(int i) {
        Fragment fragment = null;

        switch (i) {
            case R.id.home_section:
                fragment = new HomeFragment();

                break;
            case R.id.consulation_section:
                fragment  = new HistoryFragment();
                break;


            case R.id.profile_section:
                fragment  = new ProfileFragment();
                break;


            case R.id.settings_section:
                fragment  = new SettingFragment();
                break;


        }

        loadFragment(fragment);

    }


}