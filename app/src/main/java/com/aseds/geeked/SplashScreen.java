package com.aseds.geeked;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.okhttp.internal.tls.RealTrustRootIndex;


public class SplashScreen extends AppCompatActivity {
ImageView backgroundImage;
TextView poweredByLine;
Animation sideAnim, bottomAnim;


private static final int SPLASH_TIMER=4000;//duration of animation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Above code to remove status bar
        setContentView(R.layout.splash_screen);
        //Hooks
        backgroundImage=findViewById(R.id.background_image);
        poweredByLine=findViewById(R.id.powered_by_line);
        //Animations
        sideAnim= AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim=AnimationUtils.loadAnimation(this,R.anim.bottom_anim);
        //set animations on elements
        backgroundImage.setAnimation(sideAnim);
        backgroundImage.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    startActivity(new Intent(SplashScreen.this , MainActivity.class));
                    finish();
                }else {
                    Intent intent=new Intent(SplashScreen.this, RetailerStartUpScreen.class);
                    startActivity(intent);
                    finish();
            }
            }
        },SPLASH_TIMER);
    }
}
