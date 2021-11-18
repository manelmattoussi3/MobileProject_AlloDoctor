package tn.esprit.doctormobile.Helper;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import tn.esprit.doctormobile.R;


public class SliderAdapter  extends PagerAdapter {


    Context context;
    LayoutInflater layoutInflater;

    int images[] = {
            R.drawable.slide0,
            R.drawable.patientslide,
            R.drawable.calendarslide,

    };

    int []headings = new int[] {
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title,
    };


    int []description = new int[] {
            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc,
    };


    public SliderAdapter(Context context) {

        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout)object; // parent of the of views
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.from(context).inflate(R.layout.slide_layout,container,false);// call slide layout  xml


        ImageView imageView =  view.findViewById(R.id.slider_image);

        TextView textHeadingView =  view.findViewById(R.id.slider_heading);
        TextView textDescView =  view.findViewById(R.id.slider_desc);

        Log.d("postition","ppp="+position);
        imageView.setImageResource(images[position]);
        textHeadingView.setText(headings[position]);
        textDescView.setText(description[position]);

        container.addView(view);
        return view;
    }


    //Destory yfasa5 mel memoire app l view w y7otha fi mempoire tel bch tab9a app rapide
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
