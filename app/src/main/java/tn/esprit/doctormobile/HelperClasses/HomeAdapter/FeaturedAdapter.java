package tn.esprit.doctormobile.HelperClasses.HomeAdapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tn.esprit.doctormobile.DetailedDoctorActivity;
import tn.esprit.doctormobile.Model.User;
import tn.esprit.doctormobile.R;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.FeaturedViewHolder> {
    ArrayList<User> featuredlocations;

    public FeaturedAdapter(ArrayList<User> featuredlocations) {
        this.featuredlocations = featuredlocations;
    }

    @NonNull
    @Override
    public FeaturedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card_design, parent, false);
        FeaturedViewHolder featuredViewHolder = new FeaturedViewHolder(view);
        return featuredViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedViewHolder holder, int position) {
        User user = featuredlocations.get(position);
        //KEN IMAGE NULL
        if (user.getImage() != null) {
            holder.image.setImageBitmap(BitmapFactory.decodeByteArray(user.getImage(),
                    0, user.getImage().length));
        } else {
            holder.image.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }
        holder.title.setText(user.getFullname());

        holder.myRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                holder.myResult.setText("Result"+ratingBar);
            }
        });

    }

    @Override
    public int getItemCount() {
        return featuredlocations.size();
    }

    public static class FeaturedViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView title, desc;
        Button btn_booking;
        RatingBar myRatingBar;
        TextView myResult;


        public FeaturedViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.featured_image);
            title = itemView.findViewById(R.id.featured_title);
            btn_booking = itemView.findViewById(R.id.btn_booking);
            myRatingBar  =itemView.findViewById(R.id.myRatingBar);
            myResult=itemView.findViewById(R.id.myResult);

            btn_booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailedDoctorActivity.class);

                    //SEND DOCTOR USERNAME
                    intent.putExtra("USERNAME_DOCTOR",title.getText().toString());
                    //SEND IMAGE
                    image.buildDrawingCache();
                    Bitmap bitmap = image.getDrawingCache();

                    intent.putExtra("PICTURE_DOCTOR", bitmap);
                    view.getContext().startActivity(intent);


                }
            });
        }
    }
}

