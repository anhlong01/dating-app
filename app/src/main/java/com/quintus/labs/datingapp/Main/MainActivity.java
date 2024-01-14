package com.quintus.labs.datingapp.Main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.quintus.labs.datingapp.Introduction.IntroductionMain;
import com.quintus.labs.datingapp.R;
import com.quintus.labs.datingapp.Utils.PulsatorLayout;
import com.quintus.labs.datingapp.Utils.TopNavigationViewHelper;
import com.quintus.labs.datingapp.Utils.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 1;
    final private int MY_PERMISSIONS_REQUEST_LOCATION = 123;
    ListView listView;
    List<Cards> rowItems;
    FrameLayout cardFrame, moreFrame;
    private Context mContext = MainActivity.this;
    private NotificationHelper mNotificationHelper;
    private Cards cards_data[];
    private PhotoAdapter arrayAdapter;
    private FirebaseAuth mAuth;
    public static boolean isFinished;
    private ArrayList<User> userArrayList;
    private DatabaseReference mDatabase;
    private String currentUserId;
    private String sex;
    private FirebaseUser currentUser;
    private ArrayList<String> selectedUserId;
    private DatabaseReference selectedDatabase;
    private DatabaseReference matchDatabase;
    private User myUser;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
//    private SwipeFlingAdapterView flingContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        setupTopNavigationView();
        cardFrame = findViewById(R.id.card_frame);
        moreFrame = findViewById(R.id.more_frame);
        // start pulsator
        PulsatorLayout mPulsator = findViewById(R.id.pulsator);
        mPulsator.start();
        mNotificationHelper = new NotificationHelper(this);
        currentUser = mAuth.getCurrentUser();

        if(currentUser==null){
            Intent intent = new Intent(MainActivity.this, IntroductionMain.class);
            startActivity(intent);
            finish();
        }
        else{
//            flingContainer = findViewById(R.id.frame);
            currentUserId = currentUser.getUid();



//            updateLocation();
            swipeCard();
            String CurrentUID = currentUser.getUid();
            FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUID).child("Online").setValue("true");
            FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUID).child("Seen").setValue("online");


        }
    }
    @Override
    protected void onStop() {
        super.onStop();

            FirebaseAuth Auth= FirebaseAuth.getInstance();
            FirebaseUser currentUser = Auth.getCurrentUser();
            if (currentUser != null) {
                String CurrentUID = currentUser.getUid();
                FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUID).child("Online").setValue(ServerValue.TIMESTAMP);
                FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUID).child("Seen").setValue("offline");
            }


    }
//    @Override
//    protected void onStart() {
//        super.onStart();
////        isFinished = true;
//        // Check if user is signed in (non-null) and update UI accordingly.
//        currentUser = mAuth.getCurrentUser();
//
//        if(currentUser==null){
//            Intent intent = new Intent(MainActivity.this, IntroductionMain.class);
//            startActivity(intent);
//            finish();
//        }
//        else{
////            flingContainer = findViewById(R.id.frame);
//            swipeCard();
//            String CurrentUID = currentUser.getUid();
//            FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUID).child("Online").setValue("true");
//            FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUID).child("Seen").setValue("online");
//
//
//        }
//    }

    private void swipeCard(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        matchDatabase = FirebaseDatabase.getInstance().getReference("Match").child(currentUserId);
        selectedDatabase = FirebaseDatabase.getInstance().getReference().child("Selected").child(currentUserId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    userArrayList = new ArrayList<>();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        if(!snapshot.getKey().equals(currentUserId)) {
                            User user = new User();
                            user.setUser_id(snapshot.getKey());
                            user.setName(snapshot.child("Name").getValue().toString());
                            user.setAge(snapshot.child("Age").getValue(Integer.class));
                             user.setImage2(snapshot.child("Image2").getValue().toString());
                            user.setImage1(snapshot.child("Image1").getValue().toString());
                            user.setImage3(snapshot.child("Image3").getValue().toString());
                            user.setStatus(snapshot.child("Status").getValue().toString());
                            user.setJob(snapshot.child("Job").getValue().toString());
                            user.setSchool(snapshot.child("School").getValue().toString());
                            user.setSex(snapshot.child("sex").getValue().toString());
                            user.setCompany(snapshot.child("Company").getValue().toString());
                            user.setFishing(Boolean.parseBoolean(snapshot.child("Fishing").getValue().toString()));
                            user.setMovie(Boolean.parseBoolean(snapshot.child("Movie").getValue().toString()));
                            user.setMusic(Boolean.parseBoolean(snapshot.child("Music").getValue().toString()));
                            user.setSports(Boolean.parseBoolean(snapshot.child("Sports").getValue().toString()));
                            user.setGaming(Boolean.parseBoolean(snapshot.child("Gaming").getValue().toString()));
                            user.setTravel(Boolean.parseBoolean(snapshot.child("Travel").getValue().toString()));
                            user.setLatitude(snapshot.child("latitude").getValue(Double.class));
                            user.setLongtitude(snapshot.child("longitude").getValue(Double.class));
//                            user.setLatitude(20.979185);
//                            user.setLongtitude(105.7993016);
                            userArrayList.add(user);
                        }
                        else{
                            myUser = new User();
                            myUser.setSex(snapshot.child("sex").getValue().toString());
                            myUser.setAge(snapshot.child("Age").getValue(Integer.class));
                            myUser.setFishing(Boolean.parseBoolean(snapshot.child("Fishing").getValue().toString()));
                            myUser.setMovie(Boolean.parseBoolean(snapshot.child("Movie").getValue().toString()));
                            myUser.setMusic(Boolean.parseBoolean(snapshot.child("Music").getValue().toString()));
                            myUser.setSports(Boolean.parseBoolean(snapshot.child("Sports").getValue().toString()));
                            myUser.setGaming(Boolean.parseBoolean(snapshot.child("Gaming").getValue().toString()));
                            myUser.setTravel(Boolean.parseBoolean(snapshot.child("Travel").getValue().toString()));
                            myUser.setAgeFrom(snapshot.child("AgeFrom").getValue(Integer.class));
                            myUser.setAgeTo(snapshot.child("AgeTo").getValue(Integer.class));
                            myUser.setLongtitude(snapshot.child("longitude").getValue(Double.class));
                            myUser.setLatitude(snapshot.child("latitude").getValue(Double.class));
                            myUser.setDistance(snapshot.child("Distance").getValue(Integer.class));
                        }

                    }
                    getCards();
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        selectedDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedUserId = new ArrayList<>();
                if(snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        selectedUserId.add(dataSnapshot.getKey());
                    }
                    mDatabase.addValueEventListener(eventListener);
                }else{
                    mDatabase.addValueEventListener(eventListener);
                }
//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        mDatabase.addValueEventListener(eventListener);
//        arrayAdapter = new PhotoAdapter(this, R.layout.item, rowItems);

//        rowItems = new ArrayList<>();
//        Cards cards = new Cards("1", "Swati Tripathy", 21, "https://im.idiva.com/author/2018/Jul/shivani_chhabra-_author_s_profile.jpg", "Simple and beautiful Girl", "Acting", 200);
//        rowItems.add(cards);
//        cards = new Cards("2", "Ananaya Pandy", 20, "https://i0.wp.com/profilepicturesdp.com/wp-content/uploads/2018/06/beautiful-indian-girl-image-for-profile-picture-8.jpg", "cool Minded Girl", "Dancing", 800);
//        rowItems.add(cards);
//        cards = new Cards("3", "Anjali Kasyap", 22, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "Simple and beautiful Girl", "Singing", 400);
//        rowItems.add(cards);
//        cards = new Cards("4", "Preety Deshmukh", 19, "http://profilepicturesdp.com/wp-content/uploads/2018/07/fb-real-girls-dp-3.jpg", "dashing girl", "swiming", 1308);
//        rowItems.add(cards);
//        cards = new Cards("5", "Srutimayee Sen", 20, "https://dp.profilepics.in/profile_pictures/selfie-girls-profile-pics-dp/selfie-pics-dp-for-whatsapp-facebook-profile-25.jpg", "chulbuli nautankibaj ", "Drawing", 1200);
//        rowItems.add(cards);
//        cards = new Cards("6", "Dikshya Agarawal", 21, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "Simple and beautiful Girl", "Sleeping", 700);
//        rowItems.add(cards);
//        cards = new Cards("7", "Sudeshna Roy", 19, "https://talenthouse-res.cloudinary.com/image/upload/c_fill,f_auto,h_640,w_640/v1411380245/user-415406/submissions/hhb27pgtlp9akxjqlr5w.jpg", "Papa's Pari", "Art", 5000);
//        rowItems.add(cards);
//        arrayAdapter = new PhotoAdapter(this, R.layout.item, rowItems);
//        checkRowItem();
//        updateSwipeCard();

    }


    private void getCards(){
        for(String id: selectedUserId){
            userArrayList.removeIf(s -> s.getUser_id().equals(id));
        }
        rowItems = new ArrayList<>();
        arrayAdapter = new PhotoAdapter(this, R.layout.item, rowItems);
        for(User user: userArrayList){
            float[] dist = new float[1];
            Location.distanceBetween(user.getLatitude(), user.getLongtitude(), myUser.getLatitude(), myUser.getLongtitude(), dist);
            dist[0]/=1000;
            if((!user.getSex().equals(myUser.getSex())
                && user.getAge() <= myUser.getAgeTo()
                && user.getAge() >= myUser.getAgeFrom())
                && (
                        ((user.isMusic() && myUser.isMusic())
                      || (user.isSports() && myUser.isSports())
                      || (user.isGaming() && myUser.isGaming())
                      || (user.isMovie() && myUser.isMovie())
                      || (user.isTravel() && myUser.isTravel())
                      || (user.isFishing() && myUser.isFishing())
                    )
            ) && dist[0] < myUser.getDistance())
              {
                Cards cards = new Cards(user.getUser_id(),
                        user.getName(),
                        user.getAge(),
                        user.getImage1(),
                        user.getImage2(),
                        user.getImage3(),
                        user.getStatus(),
                        user.getCompany(),
                        user.getSchool(),
                        user.getJob(),
                        user.isMovie(),
                        user.isFishing(),
                        user.isTravel(),
                        user.isSports(),
                        user.isMusic(),
                        user.getDistance());
                        rowItems.add(cards);

            }
        }
        checkRowItem();
        updateSwipeCard();

    }


    private void checkRowItem() {
        if (rowItems.isEmpty()) {
            moreFrame.setVisibility(View.VISIBLE);
            cardFrame.setVisibility(View.GONE);
        }
    }

    private void updateLocation() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Location myLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (myLocation == null)
            {
                myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//                Log.d(TAG, "updateLocation: " + myLocation);
                FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId).child("longitude").setValue(myLocation.getLongitude());
                FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId).child("latitude").setValue(myLocation.getLatitude());

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        updateLocation();
                    } else {
                        Toast.makeText(MainActivity.this, "Location Permission Denied. You have to give permission inorder to know the user range ", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void updateSwipeCard() {
        final SwipeFlingAdapterView flingContainer = findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
//                selectedDatabase.child(rowItems.get(0).getUserId()).setValue(rowItems.get(0).getUserId());
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                selectedDatabase.child(obj.getUserId()).setValue(obj.getUserId());
                checkRowItem();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Cards obj = (Cards) dataObject;
                matchDatabase.child(obj.getUserId()).setValue(obj.getUserId());
                selectedDatabase.child(obj.getUserId()).setValue(obj.getUserId());
                //check matches
                checkRowItem();

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here


            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                View view = flingContainer.getSelectedView();
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Cards card_item = (Cards)dataObject;
//                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ProfileCheckinMain.class);
                intent.putExtra("card", card_item);
                mContext.startActivity(intent);
            }
        });


    }


    public void sendNotification() {
        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(mContext.getString(R.string.app_name), mContext.getString(R.string.match_notification));

        mNotificationHelper.getManager().notify(1, nb.build());
    }

    public void DislikeBtn(View v) {
        if (rowItems.size() != 0) {
            Cards card_item = rowItems.get(0);

            String userId = card_item.getUserId();

            rowItems.remove(0);
            arrayAdapter.notifyDataSetChanged();
            selectedDatabase.child(card_item.getUserId()).setValue(card_item.getUserId());
            Intent btnClick = new Intent(mContext, BtnDislikeActivity.class);
            btnClick.putExtra("url", card_item.getProfileImageUrl());
            startActivity(btnClick);
        }
    }

    public void LikeBtn(View v) {
        if (rowItems.size() != 0) {
            Cards card_item = rowItems.get(0);

            String userId = card_item.getUserId();

            //check matches

            rowItems.remove(0);
            arrayAdapter.notifyDataSetChanged();
            matchDatabase.child(card_item.getUserId()).setValue(card_item.getUserId());
            selectedDatabase.child(card_item.getUserId()).setValue(card_item.getUserId());
            Intent btnClick = new Intent(mContext, BtnLikeActivity.class);
            btnClick.putExtra("url", card_item.getProfileImageUrl());
            startActivity(btnClick);
        }
    }



    /**
     * setup top tool bar
     */
    private void setupTopNavigationView() {
        Log.d(TAG, "setupTopNavigationView: setting up TopNavigationView");
        BottomNavigationViewEx tvEx = findViewById(R.id.topNavViewBar);
        TopNavigationViewHelper.setupTopNavigationView(tvEx);
        TopNavigationViewHelper.enableNavigation(mContext, tvEx);
        Menu menu = tvEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    @Override
    public void onBackPressed() {

    }


}
