package com.quintus.labs.datingapp.Matched;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.quintus.labs.datingapp.R;
import com.quintus.labs.datingapp.Utils.TopNavigationViewHelper;
import com.quintus.labs.datingapp.Utils.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */

public class Matched_Activity extends AppCompatActivity implements SelectListener {

    private static final String TAG = "Matched_Activity";
    private static final int ACTIVITY_NUM = 2;
    List<Users> matchList = new ArrayList<>();
    List<User> copyList = new ArrayList<>();
    private Context mContext = Matched_Activity.this;
    private String userId, userSex, lookforSex;
    private double latitude = 37.349642;
    private double longtitude = -121.938987;
    private EditText search;
    private List<Users> usersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ListView mRecyclerView;
    private ActiveUserAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mMainView;
    private String mParam1;
    private String mParam2;

    //my variables
    private ArrayList<String> UsersId;
    private ArrayList <Friends>UsersArrayList;
    private ArrayList<Friends> UsersArrayList2;
    private ListView UserChatsListView;
    private ImageButton btnCheckInfoBeforeMatched;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String CurrentUId;
    private String FinalMessage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched);

        setupTopNavigationView();
        searchFunc();


        recyclerView = findViewById(R.id.active_recycler_view);
        UserChatsListView= findViewById(R.id.UserChats_ListView_id);

//        adapter = new ActiveUserAdapter(usersList, getApplicationContext());
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
//        prepareActiveData();

//        mAdapter = new MatchUserAdapter(matchList, getApplicationContext());
//        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());
//        mRecyclerView.setLayoutManager(mLayoutManager1);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.setAdapter(mAdapter);

        prepareMatchData();


    }

    private void prepareActiveData() {

//        Users users = new Users("1", "Swati Tripathy", 21, "https://im.idiva.com/author/2018/Jul/shivani_chhabra-_author_s_profile.jpg", "Simple and beautiful Girl", "Acting", 200);
//        usersList.add(users);
//        users = new Users("2", "Ananaya Pandy", 20, "https://i0.wp.com/profilepicturesdp.com/wp-content/uploads/2018/06/beautiful-indian-girl-image-for-profile-picture-8.jpg", "cool Minded Girl", "Dancing", 800);
//        usersList.add(users);
//        users = new Users("3", "Anjali Kasyap", 22, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "Simple and beautiful Girl", "Singing", 400);
//        usersList.add(users);
//        users = new Users("7", "Sudeshna Roy", 19, "https://talenthouse-res.cloudinary.com/image/upload/c_fill,f_auto,h_640,w_640/v1411380245/user-415406/submissions/hhb27pgtlp9akxjqlr5w.jpg", "Papa's Pari", "Art", 5000);
//        usersList.add(users);

//        adapter.notifyDataSetChanged();
    }

    private void prepareMatchData() {
//        Users users = new Users("1", "Swati Tripathy", 21, "https://im.idiva.com/author/2018/Jul/shivani_chhabra-_author_s_profile.jpg", "Simple and beautiful Girl", "Acting", 200);
//        matchList.add(users);
//        users = new Users("2", "Ananaya Pandy", 20, "https://i0.wp.com/profilepicturesdp.com/wp-content/uploads/2018/06/beautiful-indian-girl-image-for-profile-picture-8.jpg", "cool Minded Girl", "Dancing", 800);
//        matchList.add(users);
//        users = new Users("3", "Anjali Kasyap", 22, "https://pbs.twimg.com/profile_images/967542394898952192/_M_eHegh_400x400.jpg", "Simple and beautiful Girl", "Singing", 400);
//        matchList.add(users);
//        users = new Users("4", "Preety Deshmukh", 19, "http://profilepicturesdp.com/wp-content/uploads/2018/07/fb-real-girls-dp-3.jpg", "dashing girl", "swiming", 1308);
//        matchList.add(users);
//        users = new Users("5", "Srutimayee Sen", 20, "https://dp.profilepics.in/profile_pictures/selfie-girls-profile-pics-dp/selfie-pics-dp-for-whatsapp-facebook-profile-25.jpg", "chulbuli nautankibaj ", "Drawing", 1200);
//        matchList.add(users);
//        users = new Users("6", "Dikshya Agarawal", 21, "https://pbs.twimg.com/profile_images/485824669732200448/Wy__CJwU.jpeg", "Simple and beautiful Girl", "Sleeping", 700);
//        matchList.add(users);
//        users = new Users("7", "Sudeshna Roy", 19, "https://talenthouse-res.cloudinary.com/image/upload/c_fill,f_auto,h_640,w_640/v1411380245/user-415406/submissions/hhb27pgtlp9akxjqlr5w.jpg", "Papa's Pari", "Art", 5000);
//        matchList.add(users);
        //firebase
        mAuth= FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        CurrentUId=currentUser.getUid();


        //define array lists
        UsersId=new ArrayList<>();
        UsersArrayList=new ArrayList<>();
        //if the user click to any friend contact
        UserChatsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Friends friend= UsersArrayList.get(i);
                Intent intent = new Intent(Matched_Activity.this,FriendsChattingActivity.class);
                intent.putExtra("User Id",friend.getFriendId());
                intent.putExtra("User Name",friend.getFriendName());
                intent.putExtra("User Image",friend.getFriendImage());
                startActivity(intent);
            }
        });


        //check if the current user have friends or not
        DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        DatabaseReference m=root.child("Match").child(CurrentUId);
        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    UsersId.clear();
                    UsersArrayList.clear();
                    final FriendsAdapter adapter=new FriendsAdapter(Matched_Activity.this,UsersArrayList);
                    UserChatsListView.setAdapter(adapter);
                    checkTheFriends();

                }
                else{
                    //to not display any friend because the user doesn't have any friend
                    UsersId.clear();
                    UsersArrayList.clear();
                    final FriendsAdapter adapter=new FriendsAdapter(Matched_Activity.this,UsersArrayList);
                    UserChatsListView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        m.addListenerForSingleValueEvent(eventListener);
//        mAdapter.notifyDataSetChanged();
    }

    private void searchFunc() {
        search = findViewById(R.id.searchBar);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchText();
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchText();
            }
        });
    }

    private void searchText() {
        final FriendsAdapter adapter=new FriendsAdapter(Matched_Activity.this,UsersArrayList);
        UserChatsListView.setAdapter(adapter);
//        UsersArrayList2 = UsersArrayList;
         String text = search.getText().toString().toLowerCase(Locale.getDefault());
         if (text.length() != 0) {
             UsersArrayList = new ArrayList<>(UsersArrayList2);
//             UsersArrayList.addAll(UsersArrayList2);
//             UsersArrayList.stream().filter(s -> s.getFriendName().contains(text)).collect(Collectors.toList());
//             UserChatsListView.clearTextFilter();
             UsersArrayList.removeIf(s -> !s.getFriendName().toLowerCase().contains(text));

         } else {
             UsersArrayList = new ArrayList<>(UsersArrayList2);
//             UsersArrayList.stream().;
//             UserChatsListView.setFilterText(text);
         }

        adapter.notifyDataSetChanged();
     }

//     private boolean checkDup(User user) {
//         if (matchList.size() != 0) {
//             for (User u : matchList) {
//                 if (u.getUsername() == user.getUsername()) {
//                     return true;
//                 }
//             }
//         }
//
//         return false;
//     }

//     private void checkClickedItem(int position) {
//
//         User user = matchList.get(position);
//         //calculate distance
//         Intent intent = new Intent(this, ProfileCheckinMatched.class);
//         intent.putExtra("classUser", user);
//
//         startActivity(intent);
//     }
    private void checkTheFriends(){
        UsersId.clear();

        DatabaseReference root=FirebaseDatabase.getInstance().getReference();
        DatabaseReference m=root.child("Match").child(CurrentUId);
        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for( DataSnapshot Snapshot: dataSnapshot.getChildren()){
                        UsersId.add(Snapshot.getKey().toString());
                    }
                    sentUserDataToArrayAdapter();
                    prepareActiveData();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        m.addListenerForSingleValueEvent(eventListener);
    }

    private void sentUserDataToArrayAdapter(){
        UsersArrayList.clear();
        final FriendsAdapter adapter=new FriendsAdapter(Matched_Activity.this,UsersArrayList);
        final ActiveUserAdapter activeAdapter = new ActiveUserAdapter(usersList, getApplicationContext(), this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(activeAdapter);

        for(int i=0;i<UsersId.size();i++){
            getFinalMessage(UsersId.get(i));
            DatabaseReference root=FirebaseDatabase.getInstance().getReference();
            DatabaseReference m=root.child("users").child(UsersId.get(i));
            ValueEventListener eventListener= new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        String name = dataSnapshot.child("Name").getValue().toString();
                        String image = dataSnapshot.child("Image1").getValue().toString();
                        String OnLine = dataSnapshot.child("Online").getValue().toString();
                        if(OnLine.equals("true")){
                            UsersArrayList.add(new Friends(name,FinalMessage,image,dataSnapshot.getKey(),true));
                            Users user = new Users(dataSnapshot.getKey().toString(), name, image);
                            usersList.add(user);
                            activeAdapter.notifyDataSetChanged();
                        }
                        else UsersArrayList.add(new Friends(name,FinalMessage,image,dataSnapshot.getKey(),false));
                        FinalMessage="";
                        UsersArrayList2 = new ArrayList<>();
                        UsersArrayList2.addAll(UsersArrayList);
                        searchFunc();
                        adapter.notifyDataSetChanged();
//                        searchFunc();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            };
            m.addListenerForSingleValueEvent(eventListener);

        }

        UserChatsListView.setAdapter(adapter);


    }

    private void getFinalMessage(String UserId){
        FinalMessage="";
        DatabaseReference root= FirebaseDatabase.getInstance().getReference();
        DatabaseReference m=root.child("chats").child(CurrentUId).child(UserId);
        ValueEventListener eventListener= new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot Snapshot : dataSnapshot.getChildren()){
                        if(Snapshot.child("Message Type").getValue().equals("Image")) FinalMessage="Image";
                        else if(Snapshot.child("Message Type").getValue().equals("Record")) FinalMessage="Audio";
                        else FinalMessage=Snapshot.child("Message").getValue().toString().substring(1, Snapshot.child("Message").getValue().toString().length());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        m.addListenerForSingleValueEvent(eventListener);

    }

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
    public void onItemClicked(Users user) {
        Intent intent = new Intent(Matched_Activity.this,FriendsChattingActivity.class);
        intent.putExtra("User Id",user.getUserId());
        intent.putExtra("User Name",user.getName());
        intent.putExtra("User Image",user.getProfileImageUrl());
        startActivity(intent);
    }
}
