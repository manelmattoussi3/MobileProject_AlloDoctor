package tn.esprit.doctormobile.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tn.esprit.doctormobile.Adapater.SBookingAdapter;
import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.Model.Booking;
import tn.esprit.doctormobile.Model.Doctor;
import tn.esprit.doctormobile.R;
import tn.esprit.doctormobile.Session.SessionManager;

public class HistoryFragment extends Fragment {
    private RecyclerView courseRV;

    // Arraylist for storing data
    private List<Booking> bookingArrayList;

    SBookingAdapter sBookingAdapter;
    MyDatabaseHelper databaseHelper;

    public HistoryFragment() {
        // required empty public constructor.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        courseRV = view.findViewById(R.id.recylcerView);

        //SINGLETON
        databaseHelper = MyDatabaseHelper.DatabaseInstance(getContext());

        displayHistorique();



        // we are initializing our adapter class and passing our arraylist to it.
        sBookingAdapter = new SBookingAdapter(getContext(), (ArrayList<Booking>) bookingArrayList);

        courseRV.setLayoutManager(new LinearLayoutManager(getContext()));
        courseRV.setAdapter(sBookingAdapter);
        courseRV.setItemAnimator(new DefaultItemAnimator());
        layoutAnimation(courseRV);

        return view;
    }


    public void displayHistorique() {
        // here we have created new array list and added data to it.
        bookingArrayList = new ArrayList<Booking>();
        List<Booking> bookings =databaseHelper.getBookingsByUsername(SessionManager.getUser(getContext()).getUsername());
        for(int i = 0 ; i< bookings.size(); i++) {

                bookingArrayList.add(bookings.get(i));


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