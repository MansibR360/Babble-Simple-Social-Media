package com.mansibyasir.officialchat.welcome;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.mansibyasir.officialchat.Check;
import com.mansibyasir.officialchat.R;
import com.mansibyasir.officialchat.SharedPref;

import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        setTheme(R.style.Splash);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences settings=getSharedPreferences("prefs",0);
        boolean firstRun=settings.getBoolean("firstRun",false);
        if(!firstRun)//if running for first time
        //Splash will load for first time
        {
            SharedPreferences.Editor editor=settings.edit();
            editor.putBoolean("firstRun",true);
            editor.apply();
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), IntroActivity.class );
                startActivity(intent);
                finish();


            },3000);
        }
        else
        {
            new Handler().postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(), Check.class );
                startActivity(intent);
                finish();


            },3000);
        }
    }

}
