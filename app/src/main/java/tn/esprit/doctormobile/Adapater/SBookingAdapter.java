package tn.esprit.doctormobile.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;

import tn.esprit.doctormobile.Database.MyDatabaseHelper;
import tn.esprit.doctormobile.Model.Booking;
import tn.esprit.doctormobile.R;
import tn.esprit.doctormobile.Session.SessionManager;

public class SBookingAdapter extends RecyclerView.Adapter<SBookingAdapter.Viewholder> {

    private Context context;
    private ArrayList<Booking> bookingArrayList;

    // Constructor
    public SBookingAdapter(Context context, ArrayList<Booking> bookingArrayList) {
        this.context = context;
        this.bookingArrayList = bookingArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_book_status, parent, false);
        return new Viewholder(view);
    }



    //SET ITEMS B DES VALUES
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        Booking model = bookingArrayList.get(position);
        //BCH NTASTI BIHOM SI PATIENT WALE
        //IF PATIENT DONC MATWRICH LES BUTTON
        String roleUserCurrent = MyDatabaseHelper.DatabaseInstance(context.getApplicationContext()).getRoleByUsername(
                SessionManager.getUser(context.getApplicationContext()).getUsername()).toString().trim();



        //CONFIRM BUTTON/REJECT BUTTON yafichihom ken le l doctor
        if(roleUserCurrent.equals("PATIENT")){
            holder.btnReject.setVisibility(View.GONE);
            holder.idbooking.setText("");
            holder.btnConfirm.setVisibility(View.GONE);
            holder.patient.setText("your request was sent ");

        }
        //BCH T3abi item b data ely jebthom mel db
        if(model.getStatus()==0){
            holder.statusTv.setText("PENDING");

        }
        else if(model.getStatus()==1){
            holder.statusTv.setText("CONFIRMED");

        }
        else if(model.getStatus()==-1){
            holder.statusTv.setText("REJECTED");

        }


        holder.dateTimeTv.setText(model.getDate()+"-"+model.getTime());


        if(roleUserCurrent.equals("DOCTOR"))
        holder.patient.setText("Patient  :"+model.getUsername().toString());


        holder.idbooking.setText(String.valueOf(model.getId()));
        Toast.makeText(holder.itemView.getContext(), "STATUS="+holder.statusTv.getText(), Toast.LENGTH_SHORT).show();

        if(holder.statusTv.getText().equals("CONFIRMED")){
            holder.statusTv.setTextColor(holder.itemView.getResources().getColor(R.color.button_green_focused));
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
        }
        else if (holder.statusTv.getText().equals("REJECTED")){
            holder.statusTv.setTextColor(holder.itemView.getResources().getColor(R.color.red_300));
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnReject.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return bookingArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    //RECUPERER LES WIDGETS MEN XML
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView dateTimeTv, statusTv,patient,idbooking;
        private Button btnConfirm,btnReject;
        private ImageView doctorImg;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            dateTimeTv = itemView.findViewById(R.id.date_time_book);
            idbooking = itemView.findViewById(R.id.id_booking);
            statusTv = itemView.findViewById(R.id.status_book);
            patient = itemView.findViewById(R.id.username_patient);
            btnConfirm = itemView.findViewById(R.id.confirm_btn);
            btnReject = itemView.findViewById(R.id.reject_btn);
       //     doctorImg = itemView.findViewById(R.id.img_doctor);
            if(statusTv.getText().equals("0")){
                statusTv.setTextColor(itemView.getResources().getColor(R.color.yellowfresh));
            }



            //CONFIRM BOOKIN BUTTON
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //SHOW SWEET ALERt
                    new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Book patient will be confirmed!")
                            .setConfirmText("Yes,confirm it!")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    //kif yconfirmi y3at lel function ta3 confirm mel sqlidatabse helper
                                    MyDatabaseHelper.DatabaseInstance(view.getContext())
                                                    .confirmBooking(Integer.parseInt(idbooking.getText().toString()));
                                    sDialog
                                            .setTitleText("Confirmed!")
                                            .setContentText("You confirmed patient request for appointment.")
                                            .setConfirmText("OK")

                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();
                }
            });

            //REJECT BOOKIN BUTTON
            btnReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //SHOW SWEET ALERt
                    new SweetAlertDialog(view.getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Book patient will be rejected!")
                            .setConfirmText("Yes,reject it!")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    //kif yconfirmi y3at lel function ta3 confirm mel sqlidatabse helper
                                    MyDatabaseHelper.DatabaseInstance(view.getContext())
                                            .rejectBooking(Integer.parseInt(idbooking.getText().toString()));
                                    sDialog
                                            .setTitleText("Reject!")
                                            .setContentText("You reject patient request for appointment.")
                                            .setConfirmText("OK")

                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();
                }
            });



        }

    }
}