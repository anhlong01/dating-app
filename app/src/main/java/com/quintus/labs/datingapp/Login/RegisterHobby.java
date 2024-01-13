package com.quintus.labs.datingapp.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.quintus.labs.datingapp.Main.MainActivity;
import com.quintus.labs.datingapp.R;
import com.quintus.labs.datingapp.Utils.User;

import java.util.Date;
import java.util.HashMap;


/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class RegisterHobby extends AppCompatActivity {
    private static final String TAG = "RegisterHobby";

    //User Info
    User userInfo;
    String password;

    private Context mContext;
    private Button hobbiesContinueButton;
    private Button sportsSelectionButton;
    private Button travelSelectionButton;
    private Button musicSelectionButton;
    private Button fishingSelectionButton;
    private Button movieButton;
    private Button gameButton;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private int age;

    private String append = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_hobby);
        mContext = RegisterHobby.this;
        mAuth =FirebaseAuth.getInstance();

        Log.d(TAG, "onCreate: started");

        Intent intent = getIntent();
        userInfo = (User) intent.getSerializableExtra("classUser");
        password = intent.getStringExtra("password");

        initWidgets();

        init();
    }

    private void initWidgets() {
        sportsSelectionButton = findViewById(R.id.sportsSelectionButton);
        travelSelectionButton = findViewById(R.id.travelSelectionButton);
        musicSelectionButton = findViewById(R.id.musicSelectionButton);
        fishingSelectionButton = findViewById(R.id.fishingSelectionButton);
        hobbiesContinueButton = findViewById(R.id.hobbiesContinueButton);
        movieButton = findViewById(R.id.movieSelectionButton);
        gameButton = findViewById(R.id.gamingSelectionButton);

        // Initially all the buttons needs to be grayed out so this code is added, on selection we will enable it later
        sportsSelectionButton.setAlpha(.5f);
        sportsSelectionButton.setBackgroundColor(Color.GRAY);

        travelSelectionButton.setAlpha(.5f);
        travelSelectionButton.setBackgroundColor(Color.GRAY);

        musicSelectionButton.setAlpha(.5f);
        musicSelectionButton.setBackgroundColor(Color.GRAY);

        fishingSelectionButton.setAlpha(.5f);
        fishingSelectionButton.setBackgroundColor(Color.GRAY);

        gameButton.setAlpha(.5f);
        gameButton.setBackgroundColor(Color.GRAY);

        movieButton.setAlpha(.5f);
        movieButton.setBackgroundColor(Color.GRAY);

        sportsSelectionButton.setOnClickListener(v -> sportsButtonClicked());

        travelSelectionButton.setOnClickListener(v -> travelButtonClicked());

        musicSelectionButton.setOnClickListener(v -> musicButtonClicked());

        fishingSelectionButton.setOnClickListener(v -> fishingButtonClicked());

        movieButton.setOnClickListener(view -> movieButtonClicked());

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameButtonClicked();
            }
        });
        }

    private void movieButtonClicked() {
        if (movieButton.getAlpha() == 1.0f) {
            movieButton.setAlpha(.5f);
            movieButton.setBackgroundColor(Color.GRAY);
            userInfo.setMovie(false);
        } else {
            movieButton.setBackgroundColor(Color.parseColor("#FF4081"));
            movieButton.setAlpha(1.0f);
            userInfo.setMovie(true);
        }
    }

    private void gameButtonClicked() {
        if (gameButton.getAlpha() == 1.0f) {
            gameButton.setAlpha(.5f);
            gameButton.setBackgroundColor(Color.GRAY);
            userInfo.setGaming(false);
        } else {
            gameButton.setBackgroundColor(Color.parseColor("#FF4081"));
            gameButton.setAlpha(1.0f);
            userInfo.setGaming(true);

        }
    }

    public void sportsButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (sportsSelectionButton.getAlpha() == 1.0f) {
            sportsSelectionButton.setAlpha(.5f);
            sportsSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setSports(false);
        } else {
            sportsSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            sportsSelectionButton.setAlpha(1.0f);
            userInfo.setSports(true);
        }
    }

    public void travelButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (travelSelectionButton.getAlpha() == 1.0f) {
            travelSelectionButton.setAlpha(.5f);
            travelSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setTravel(false);
        } else {
            travelSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            travelSelectionButton.setAlpha(1.0f);
            userInfo.setTravel(true);

        }

    }

    public void musicButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (musicSelectionButton.getAlpha() == 1.0f) {
            musicSelectionButton.setAlpha(.5f);
            musicSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setMusic(false);
        } else {
            musicSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            musicSelectionButton.setAlpha(1.0f);
            userInfo.setMusic(true);

        }

    }

    public void fishingButtonClicked() {
        // this is to toggle between selection and non selection of button
        if (fishingSelectionButton.getAlpha() == 1.0f) {
            fishingSelectionButton.setAlpha(.5f);
            fishingSelectionButton.setBackgroundColor(Color.GRAY);
            userInfo.setFishing(false);
        } else {
            fishingSelectionButton.setBackgroundColor(Color.parseColor("#FF4081"));
            fishingSelectionButton.setAlpha(1.0f);
            userInfo.setFishing(true);

        }

    }

    public void init() {
        hobbiesContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  RegisterUser(userInfo, password);
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                finish();

            }
        });
    }

    public void RegisterUser(@NonNull User user, String password){
        mProgressDialog=new ProgressDialog(RegisterHobby.this);
        mProgressDialog.setTitle("Đăng ký người dùng");
        mProgressDialog.setMessage("\n" +
                "Đợi tí, sắp xong rồi");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                mProgressDialog.dismiss();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username", user.getUsername());
                hashMap.put("image", user.getProfileImageUrl());
                hashMap.put("sex", user.getSex());
                hashMap.put("Name",user.getUsername());
                hashMap.put("Job", "");
                hashMap.put("School", "");
                hashMap.put("Status", "");
                hashMap.put("Company", "");
                if (user.getSex().equals("male"))
                    hashMap.put("Image1", "https://anh.moe/view/rQFOA");
                else
                    hashMap.put("Image1", "https://cdn.anh.moe/c/riDlb.png?wp=1");
                hashMap.put("Image2", "");
                hashMap.put("Image3", "");
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String uId = currentUser.getUid();
                DatabaseReference z = FirebaseDatabase.getInstance().getReference().child("users");
                z.child(uId).setValue(hashMap);
//                HashMap<String, Boolean> hashMap2 = new HashMap<>();
                z.child(uId).child("Travel").setValue(user.isTravel());
                z.child(uId).child("Sports").setValue(user.isSports());
                z.child(uId).child("Music").setValue(user.isMusic());
                z.child(uId).child("Movie").setValue(user.isMovie());
                z.child(uId).child("Fishing").setValue(user.isFishing());
                z.child(uId).child("Gaming").setValue(user.isGaming());
//                z.child(uId).child("hobbies").setValue(hashMap2);
                z.child(uId).child("AgeFrom").setValue(12);
                z.child(uId).child("AgeTo").setValue(50);
                z.child(uId).child("AgeTo").setValue(50);
                z.child(uId).child("Distance").setValue(20);
                z.child(uId).child("Age").setValue(user.getAge());
                Toast.makeText(RegisterHobby.this, "\n" + "Đã đăng ký thành công", Toast.LENGTH_LONG ).show();
                Intent intent = new Intent(RegisterHobby.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                mProgressDialog.hide();
                Toast.makeText(RegisterHobby.this,"Đăng ký không thành công",Toast.LENGTH_LONG).show();
            }
        });

    }


    //----------------------------------------Firebase----------------------------------------


}
