package com.aseds.geeked;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstScreen extends AppCompatActivity {
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_screen);
        signup=findViewById(R.id.signup_btn);

    }
    public void callSignUp1(View view){
    Intent intent = new Intent(FirstScreen.this, RegisterActivity.class);
    startActivity(intent);
    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(FirstScreen.this, LoginActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.login_btn), "transition_login");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(FirstScreen.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            Log.d("test","This is test");
            startActivity(intent);
        }
    }
}




