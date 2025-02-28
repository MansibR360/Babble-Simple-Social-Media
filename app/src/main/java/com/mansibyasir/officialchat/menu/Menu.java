package com.mansibyasir.officialchat.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mansibyasir.officialchat.R;
import com.mansibyasir.officialchat.SharedPref;
import com.mansibyasir.officialchat.user.MyFollowing;
import com.mansibyasir.officialchat.welcome.IntroLast;
import com.tapadoo.alerter.Alerter;

import java.util.Objects;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class Menu extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    ConstraintLayout logout,save,followers,following,invite,policy,delete,email,password,term;
    Switch aSwitch;
    SharedPref sharedPref;
    ImageView imageView3;
    String mEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState()){
           setTheme(R.style.DarkTheme);
       }else setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        logout = findViewById(R.id.logout);
        followers = findViewById(R.id.followers);
        following = findViewById(R.id.following);
        aSwitch = findViewById(R.id.mySwitch);
        policy = findViewById(R.id.policy);
        term = findViewById(R.id.terms);
        delete = findViewById(R.id.delete);
        imageView3 = findViewById(R.id.imageView3);
        imageView3.setOnClickListener(v -> onBackPressed());
        invite = findViewById(R.id.invite);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        followers.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, MyFollowing.class);
            intent.putExtra("title", "followers");
            startActivity(intent);
        });
        policy.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Policy.class);
            startActivity(intent);
        });
        term.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Terms.class);
            startActivity(intent);
        });
        following.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, MyFollowing.class);
            intent.putExtra("title", "following");
            startActivity(intent);
        });
        invite.setOnClickListener(v -> {
            String shareBody = "Babble" + " Download now on play store \nhttps://play.google.com/store/apps/details?id=com.mansibyasir.officialchat";
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/*");
            intent.putExtra(Intent.EXTRA_SUBJECT,"Subject Here");
            intent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(intent, "Share Via"));
        });
        if (sharedPref.loadNightModeState()){
            aSwitch.setChecked(true);
        }
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPref.setNightModeState(isChecked);
            restartApp();
        });
        save = findViewById(R.id.save);
        logout.setOnClickListener(v -> {
            mAuth.signOut();
            sharedPref.setNightModeState(false);
            checkUserStatus();
        });
        save.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Saved.class);
            startActivity(intent);
        });
        delete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
            builder.setTitle("Delete Account");
            builder.setMessage("You want to delete your account ? Action can't be undone!");
            builder.setPositiveButton("Delete", (dialog, which) -> firebaseUser.delete().addOnSuccessListener(aVoid -> {
                Intent intent = new Intent(Menu.this, IntroLast.class);
                sharedPref.setNightModeState(false);
                startActivity(intent);
                finish();
            })).setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
       DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseUser.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 mEmail = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                 if (mEmail.isEmpty()){
                     email.setVisibility(View.GONE);
                     password.setVisibility(View.GONE);
                 }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Alerter.create(Menu.this)
                        .setTitle("Error")
                        .setIcon(R.drawable.ic_error)
                        .setBackgroundColorRes(R.color.bluesugar)
                        .setDuration(10000)
                        .setTitleTypeface(Typeface.createFromAsset(getAssets(), "bold.ttf"))
                        .setTextTypeface(Typeface.createFromAsset(getAssets(), "med.ttf"))
                        .enableSwipeToDismiss()
                        .setText(databaseError.getMessage())
                        .show();

            }
        });

        email.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, ChangeEmail.class);
            startActivity(intent);
        });
        password.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, ChangePassword.class);
            startActivity(intent);
        });


    }


    private void restartApp() {
        Intent i = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        Objects.requireNonNull(i).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void checkUserStatus() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Intent intent = new Intent(Menu.this, IntroLast.class);
            startActivity(intent);
            finish();
        }
    }
}
