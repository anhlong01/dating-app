package com.quintus.labs.datingapp.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quintus.labs.datingapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * DatingApp
 * https://github.com/quintuslabs/DatingApp
 * Created on 25-sept-2018.
 * Created by : Santosh Kumar Dash:- http://santoshdash.epizy.com
 */
public class ProfileCheckinMain extends AppCompatActivity {

    private Context mContext;
    private Cards card;
    private TextView name, status, school, job, company, hobbies;
    private ImageButton back;
    private String favorite;
    ImageView imageView1, imageView2, imageView3;
    String profileImageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_checkin_main);

        mContext = ProfileCheckinMain.this;

//       /* ImageButton back = findViewById(R.id.back);

//*/
        ;
        name =  findViewById(R.id.nameText);
        status =  findViewById(R.id.statusText);
        job =  findViewById(R.id.jobText);
        school =  findViewById(R.id.schoolText);
        company =  findViewById(R.id.companyText);
        imageView1 = findViewById(R.id.image_view_1);
        imageView2 = findViewById(R.id.image_view_2);
        imageView3 = findViewById(R.id.image_view_3);
        hobbies = findViewById(R.id.hobbieslText);
        back = findViewById(R.id.back);

        Intent intent = getIntent();
        card = (Cards) intent.getSerializableExtra("card");
        String cardName = card.getName();
        String jobTxt = card.getJob();
        String companyTxt = card.getCompany();
        String schoolTxt = card.getSchool();
        String statusTxt = card.getStatus();
        String image1 = card.getProfileImageUrl();
        String image2 = card.getImage2();
        String image3 = card.getImage3();

//        profileDistance.setText(distance + " " + append);
        name.setText(cardName);
        company.setText(companyTxt);
        school.setText(schoolTxt);
        status.setText(statusTxt);
        job.setText(jobTxt);
        favorite = "";
        if(card.isFishing())
            favorite += "Câu cá";
        if(card.isSport())
            favorite += "\nThể thao";
        if(card.isMovie())
            favorite += "\nXem phim";
        if(card.isTravel())
            favorite += "\nDu lịch";
        if(card.isMusic())
            favorite += "\nNghe nhạc";
        hobbies.setText(favorite);
        if(!image1.equals("")) {
            //in case of offline image load quickly
            Picasso.get().load(image1).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.monkey).into(imageView1, new Callback() {
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image1).placeholder(R.drawable.monkey).into(imageView1);
                        }
                    });
        }
        if(!image2.equals("")) {
            //in case of offline image load quickly
            Picasso.get().load(image2).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.monkey).into(imageView2, new Callback() {
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image2).placeholder(R.drawable.monkey).into(imageView2);
                        }
                    });
        }
        if(!image3.equals("")) {
            //in case of offline image load quickly
            Picasso.get().load(image3).networkPolicy(NetworkPolicy.OFFLINE)
                    .placeholder(R.drawable.monkey).into(imageView3, new Callback() {
                        @Override
                        public void onSuccess() {
                        }
                        @Override
                        public void onError(Exception e) {
                            Picasso.get().load(image3).placeholder(R.drawable.monkey).into(imageView3);
                        }
                    });
        }
//                Glide.with(mContext).load(R.drawable.default_man).into(profileImage);


    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    });


    }

}
