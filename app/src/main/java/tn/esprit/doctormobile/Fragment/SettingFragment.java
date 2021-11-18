package tn.esprit.doctormobile.Fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import tn.esprit.doctormobile.Helper.BroadCastRemind;
import tn.esprit.doctormobile.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {


    CardView reminderOn,reminderOff;

    public SettingFragment() {
        // Required empty public constructor
    }


    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        reminderOn= view.findViewById(R.id.reminder_on);
        reminderOff= view.findViewById(R.id.reminder_off);


        reminderOn();
        reminderOff();
        createNofificationChannel();
        return view;

    }

    //pour creer une notification qui se trouve dans broadcastremindingreceive
    private void createNofificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReminderBooking";
            String description = "Notification to remind about booking";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("book_notification", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    public void reminderOn(){

        reminderOn.setOnClickListener(view -> {
            Toast.makeText(getContext(), "REMINDER ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();//TIME
            Intent intent = new Intent(getContext(), BroadCastRemind.class);//INTENT

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);//PENDING intent bin
            //7ajtin fi background maghir matet3ada men actiivity l activity

            //bch norbot app bel alarm bch kol wa9t mou3ayn ysir declenechement
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getContext().ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 40, pendingIntent);

        });
    }

    public void reminderOff(){
        reminderOff.setOnClickListener(view -> {
            Toast.makeText(getContext(), "REMINDER OFF", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), BroadCastRemind.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
            if (alarmManager != null)
                alarmManager.cancel(pendingIntent);
        });


    }
}