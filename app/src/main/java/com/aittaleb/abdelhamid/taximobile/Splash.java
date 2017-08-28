package com.aittaleb.abdelhamid.taximobile;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 5000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        final Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        image.setAnimation(animation);
        ImageView image2 = (ImageView) findViewById(R.id.taxitapp);
        final Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.splash_animation2);
        image2.setAnimation(animation2);
      // Typewriter writer = (Typewriter) findViewById(R.id.taxiMobile);
       // writer.animateText();

        ImageView image3 = (ImageView) findViewById(R.id.googlemaps);
        final Animation animation3 = AnimationUtils.loadAnimation(this,R.anim.splash_animation2);
        image3.setAnimation(animation2);


        //Add a character every 150ms


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,LoginActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
