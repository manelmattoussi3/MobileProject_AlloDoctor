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
import java.util.List;

import tn.esprit.doctormobile.Adapater.SBookingAdapter;
import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.Model.Booking;
import tn.esprit.doctormobile.R;
import tn.esprit.doctormobile.Session.SessionManager;

public class ConfirmFragment extends Fragment {
    private RecyclerView recyclerView;

    // Arraylist for storing data
    private List<Booking> bookingArrayList;

    SBookingAdapter sBookingAdapter;

    MyDatabaseHelper databaseHelper;
    public ConfirmFragment() {
        // required empty public constructor.
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        recyclerView = view.findViewById(R.id.confirm_recycler);

        databaseHelper = MyDatabaseHelper.DatabaseInstance(getContext());

        getData();

        // we are initializing our adapter class and passing our arraylist to it.
        sBookingAdapter = new SBookingAdapter(getContext(), (ArrayList<Booking>) bookingArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sBookingAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutAnimation(recyclerView);

        return view;
    }

    public void getData() {
        // here we have created new array list and added data to it.
        bookingArrayList = new ArrayList<Booking>();
        List<Booking>bookings =databaseHelper.getBookingsByDoctor(SessionManager.getUser(getContext())
        .getUsername().trim().toString());
        for(int i = 0 ; i< bookings.size(); i++) {

            if(bookings.get(i).getStatus() ==1) {
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

