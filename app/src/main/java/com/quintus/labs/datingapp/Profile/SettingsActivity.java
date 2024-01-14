package com.quintus.labs.datingapp.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.quintus.labs.datingapp.Introduction.IntroductionMain;
import com.quintus.labs.datingapp.Login.ForgotPassword;
import com.quintus.labs.datingapp.Login.Login;
import com.quintus.labs.datingapp.R;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.util.EventListener;

/**
 * Grocery App
 * https://github.com/quintuslabs/GroceryStore
 * Created on 18-Feb-2019.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class SettingsActivity extends AppCompatActivity {
    private static final String TAG = "SettingsActivity";
    private Context mContext;
    SeekBar distance;
    RangeSeekBar rangeSeekBar;
    TextView gender, distance_text, age_rnge;
    private TextView logoutBtn, saveBtn, shareBtn, changePasswordBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mContext = SettingsActivity.this;

        mAuth = FirebaseAuth.getInstance();
        String curentUserId = mAuth.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(curentUserId);

        TextView toolbar = findViewById(R.id.toolbartag);
        toolbar.setText("Profile");
        ImageButton back = findViewById(R.id.back);
        logoutBtn = findViewById(R.id.logoutBtn);
        saveBtn = findViewById(R.id.saveSettingsBtn);
        distance = findViewById(R.id.distance);
        distance_text = findViewById(R.id.distance_text);
        age_rnge = findViewById(R.id.age_range);
        rangeSeekBar = findViewById(R.id.rangeSeekbar);
        shareBtn = findViewById(R.id.shareBtn);
        changePasswordBtn = findViewById(R.id.btnChangePassword);



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ageFrom = snapshot.child("AgeFrom").getValue().toString();
                String ageTo = snapshot.child("AgeTo").getValue().toString();
                String distanceTxt = snapshot.child("Distance").getValue().toString();
                int distanceNum = Integer.parseInt(distanceTxt);
                int ageFromNum = Integer.parseInt(ageFrom);
                int ageToNum = Integer.parseInt(ageTo);
                distance.setProgress(distanceNum);
                rangeSeekBar.setSelectedMinValue(ageFromNum);
                rangeSeekBar.setSelectedMaxValue(ageToNum);
                age_rnge.setText(ageFrom + "-" + ageTo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distance_text.setText(progress + " Km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                age_rnge.setText(minValue + "-" + maxValue);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to change password screen");
                Intent intent = new Intent(SettingsActivity.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        changePasswordBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btnHover(event, changePasswordBtn);
                return false;
            }
        });

        shareBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btnHover(event, shareBtn);
                return false;
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
            }
        });
    }



    private void Logout(){
        AlertDialog.Builder checkAlert = new AlertDialog.Builder(SettingsActivity.this);
        checkAlert.setMessage("Bạn có muốn Đăng xuất không?")
                .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        String CurrentUID = currentUser.getUid();
                        FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUID).child("Online").setValue(ServerValue.TIMESTAMP);
                        FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUID).child("Seen").setValue("offline");

                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(SettingsActivity.this,IntroductionMain.class);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = checkAlert.create();
        alert.setTitle("Log Out");
        alert.show();
    }

    private void save(){
        mProgressBar = new ProgressDialog(SettingsActivity.this);
        mProgressBar.setTitle("\n" +
                "Lưu thay đổi");
        mProgressBar.setMessage("vui lòng đợi trong khi chúng tôi thay đổi cài đặt của bạn");
        mProgressBar.show();

        mDatabase.child("AgeFrom").setValue(rangeSeekBar.getSelectedMinValue());
        mDatabase.child("AgeTo").setValue(rangeSeekBar.getSelectedMaxValue());
        mDatabase.child("Distance").setValue(distance.getProgress()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    mProgressBar.dismiss();
                    finish();
                } else {
                    mProgressBar.hide();
                }
            }
        });
    }


    private void shareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this cool app!\\nDownload the app here: https://drive.google.com/drive/folders/11KbfjGylSBaSUz0kEZ5H0aMMaK76-vwf?usp=drive_link");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }


    //Đổi màu button từ trong ra ngoài
    private void btnHover(MotionEvent event, TextView btn) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Sự kiện khi bắt đầu chạm
                btn.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            case MotionEvent.ACTION_UP:
                // Sự kiện khi ngừng chạm
                btn.setBackgroundColor(getResources().getColor(R.color.white));
                break;
            case MotionEvent.ACTION_MOVE:
                btn.setBackgroundColor(getResources().getColor(R.color.red));
                break;
            default:
                btn.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

//    private void animateButtonColor(final Button button) {
//        int colorFrom = getResources().getColor(R.color.startColor);
//        int colorTo = getResources().getColor(R.color.endColor);
//
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.setDuration(1000); // Thời gian của hiệu ứng (milliseconds)
//
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                button.setBackgroundColor((int) animator.getAnimatedValue());
//            }
//        });
//
//        colorAnimation.start();
//    }
}
