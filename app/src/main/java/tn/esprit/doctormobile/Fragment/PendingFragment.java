package tn.esprit.doctormobile.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import tn.esprit.doctormobile.Adapater.SBookingAdapter;
import tn.esprit.doctormobile.BStatusActivity;
import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.Model.Booking;
import tn.esprit.doctormobile.R;
import tn.esprit.doctormobile.Session.SessionManager;

public class PendingFragment extends Fragment {
    private RecyclerView recyclerView;

    // Arraylist for storing data
    private List<Booking> bookingArrayList;

    SBookingAdapter sBookingAdapter;

    MyDatabaseHelper databaseHelper;




    //SEARCH BAR
    MaterialSearchBar materialSearchBar;
    List<String>suggestList = new ArrayList<>();

    SwipeRefreshLayout mSwipeRefreshLayout;

    public PendingFragment() {
        // required empty public constructor.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        recyclerView = view.findViewById(R.id.pending_recycler);

        databaseHelper = MyDatabaseHelper.DatabaseInstance(getContext());

        getData();

        //REFRESH
       /* mSwipeRefreshLayout =  view.findViewById(R.id.swipeToRefresh);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                // we are initializing our adapter class and passing our arraylist to it.
                sBookingAdapter = new SBookingAdapter(getContext(), (ArrayList<Booking>) bookingArrayList);

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(sBookingAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                layoutAnimation(recyclerView);
                getData();
                 getActivity().recreate(); //Trigger the onCreate method in the activity

            }
        });


        */


        // we are initializing our adapter class and passing our arraylist to it.
        sBookingAdapter = new SBookingAdapter(getContext(), (ArrayList<Booking>) bookingArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sBookingAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutAnimation(recyclerView);

        ///SEARCH BAR
        materialSearchBar=view.findViewById(R.id.search_bar);
        //SETUP SEARCH
        materialSearchBar.setHint("Search a book");
        materialSearchBar.setCardViewElevation(10);

        //WHEN WRITE TEXT
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String>suggest= new ArrayList<>();
                for (String search:suggestList){
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                    materialSearchBar.setLastSuggestions(suggest);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //SEARCH wa9t elyenti ta3mlha ya3ml mise a jours lel recycerl
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){
                    recyclerView.setAdapter(sBookingAdapter);

                }
            }


            //kif necliki ok search
            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });

        //INIT ADAPTER DEFAULT RESULT
        sBookingAdapter = new SBookingAdapter(getContext(), (ArrayList<Booking>) databaseHelper.getBookingsByDoctor(SessionManager.getUser(getContext()).getUsername().trim()));



        return view;
    }

    private void startSearch(String text) {
        sBookingAdapter= new SBookingAdapter(getContext(),databaseHelper.getPendingBookingsByUsername(text
        ,SessionManager.getUser(getContext()).getUsername().trim()
        ));

        recyclerView.setAdapter(sBookingAdapter);

    }

    //liste des rendezvous
    public void getData() {
        // here we have created new array list and added data to it.
        bookingArrayList = new ArrayList<Booking>();
        List<Booking>bookings =databaseHelper.getBookingsByDoctor(
                SessionManager.getUser(getContext()).getUsername().trim()
        );
        for(int i = 0 ; i< bookings.size(); i++) {

            if(bookings.get(i).getStatus() ==0) {
                bookingArrayList.add(bookings.get(i));
            }

        }
        Toast.makeText(getContext(), "DATA=="+bookingArrayList.size(), Toast.LENGTH_SHORT).show();



    }

    public void layoutAnimation(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_fall_down);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.scheduleLayoutAnimation();
    }

}

