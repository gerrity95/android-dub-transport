package com.example.gerri.dub_transport.Luas;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.gerri.dub_transport.MainActivity;
import com.example.gerri.dub_transport.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LuasHomeActivity extends AppCompatActivity {

    CardView mCardView;
    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luas_home);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));


        mCardView = findViewById(R.id.card_view);

        TranslateAnimation slideFromRight = new TranslateAnimation(getDisplayWidth(), 0, 0, 0);
        slideFromRight.setInterpolator(new DecelerateInterpolator());
        slideFromRight.setDuration(400);
        mCardView.startAnimation(slideFromRight);

        GetBitmapFromURLAsync getBitmapFromURLAsync = new GetBitmapFromURLAsync(); //download image and set as background
        getBitmapFromURLAsync.execute("https://images.unsplash.com/photo-1532534716609-075d657a64b3?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=dc93b6e23e2c5da88edb3a4c9eee9b57&auto=format&fit=crop&w=1350&q=80");


    }

    public int getDisplayWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        return displayMetrics.widthPixels;
    }

    public class GetBitmapFromURLAsync extends AsyncTask<String, Void, Bitmap> {

        Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                //e.printStackTrace();
                return null;
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return getBitmapFromURL(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            try {
                //  return the bitmap by doInBackground and store in result
                //mainActivity.setBackgroundAnimations(bitmap, R.id.backgroundImage);
                ImageView backgroundImageView = findViewById(R.id.backgroundImage);
                backgroundImageView.setImageBitmap(bitmap);
                AnimationSet animationSet = new AnimationSet(true);
                TranslateAnimation slide = new TranslateAnimation(-(bitmap.getWidth() / 3), (bitmap.getWidth() / 3), 0, 0);
                slide.setDuration(10000);
                slide.setFillAfter(true);
                slide.setInterpolator(new DecelerateInterpolator());
                Animation fadeIn = new AlphaAnimation(0, 1);
                fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                fadeIn.setDuration(2500);

                animationSet.addAnimation(slide);
                animationSet.addAnimation(fadeIn);
                animationSet.setFillAfter(true);
                backgroundImageView.startAnimation(animationSet);
            } catch (Exception e) {
                //do nothing, cause no internet
            }
        }
    }


}
