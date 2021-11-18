package tn.esprit.doctormobile.Fragment;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.HelperClasses.HomeAdapter.FeaturedAdapter;
import tn.esprit.doctormobile.HelperClasses.HomeAdapter.FeaturedHelperClass;
import tn.esprit.doctormobile.Model.User;
import tn.esprit.doctormobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    TextView txtFullName;
    private SliderLayout mDemoSlider;
    RatingBar myRatingBar;
    TextView myResult;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }


    static final float END_SCALE = 0.7f;
    RecyclerView featuredRecycler;
    RecyclerView.Adapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuIcon;
    LinearLayout contentView;
    TextView username;
    RatingBar myRating;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        featuredRecycler = rootView.findViewById(R.id.featured_recycler);
                mDemoSlider = rootView.findViewById(R.id.slider);



        carousel();
       //Functions will be executed automatically when this activity will be created
        featuredRecycler();

        return rootView;




    }
    
    //RECYCLER VIEW FOR LIST DOCTOR 
    private void featuredRecycler() {
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(
                getContext()
                ,LinearLayoutManager.HORIZONTAL,false));

        
        //GET DOCTOR LIST
        ArrayList<User> doctors= getListDoctors();
         adapter = new FeaturedAdapter(doctors);
        featuredRecycler.setAdapter(adapter);
        GradientDrawable gradient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,new int[]{0xF6E0FA,0xF6E0FA});

    }


    //get liste doctor endha 3ala9a bel recycler view w featured adapater ely fifh binding
    private ArrayList<User> getListDoctors() {
        ArrayList<User>doctorsDb = (ArrayList<User>) MyDatabaseHelper.DatabaseInstance(getContext()).getAllUser();
        ArrayList<User>doctorsList =new ArrayList<>();
        for(int i = 0 ; i< doctorsDb.size();i++){
            if(doctorsDb.get(i).getRole().equals("DOCTOR")){
                doctorsList.add(doctorsDb.get(i));
            }
        }
        return doctorsList;

    }


    //CAROUSEL SLIDER
    public void carousel() {


        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Search Nearby Doctor", R.drawable.carousel1);
        file_maps.put("Book place", R.drawable.book);
        file_maps.put("Realtime Call ", R.drawable.bg_img);
        file_maps.put("Covid Track", R.drawable.corona);

        for (String key : file_maps.keySet()) {

            final TextSliderView textSliderView = new TextSliderView(getContext());

            textSliderView.description(key)
                    .image(file_maps.get(key))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            //  Intent intent = new Intent(HomeFragment.this,Login.class);
                            // intent.putExtras(textSliderView.getBundle());
                            //startActivity(intent);
                        }
                    });

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("doctorid", "a");

            mDemoSlider.addSlider(textSliderView);

            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);

        }
    }
}