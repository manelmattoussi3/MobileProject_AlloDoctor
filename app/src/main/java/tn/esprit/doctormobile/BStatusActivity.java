package tn.esprit.doctormobile;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

import tn.esprit.doctormobile.Adapater.ViewPagerAdapter;
import tn.esprit.doctormobile.Fragment.ConfirmFragment;
import tn.esprit.doctormobile.Fragment.PendingFragment;
import tn.esprit.doctormobile.Fragment.RejectFragment;

public class BStatusActivity extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bstatus);

        viewPager = findViewById(R.id.viewpager);

        // setting up the adapter
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        // add the fragments
        viewPagerAdapter.add(new PendingFragment(), "Pending");
        viewPagerAdapter.add(new ConfirmFragment(), "Confirm");
        viewPagerAdapter.add(new RejectFragment(), "Reject");

        // Set the adapter
        viewPager.setAdapter(viewPagerAdapter);

        // The Page (fragment) titles will be displayed in the
        // tabLayout hence we need to set the page viewer
        // we use the setupWithViewPager().
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
