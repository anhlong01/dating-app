package com.quintus.labs.datingapp.Main;


import java.io.Serializable;


public class Cards implements Serializable {
    private String userId;
    private String name, profileImageUrl, bio, interest, image2, image3;
    private int age;
    private float distance;
    private String status;
    private String company;
    private String school;
    private String job;
    private boolean movie;
    private boolean fishing;
    private boolean travel;
    private boolean sport;
    private boolean music;


    public Cards(String userId, String name, int age, String profileImageUrl, String image2 , String image3,  String status, String company, String school, String job, boolean movie, boolean fishing, boolean travel, boolean sport, boolean music, float distance) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.image2 = image2;
        this.image3 = image3;
        this.bio = bio;
        this.status = status;
        this.company = company;
        this.school = school;
        this.job = job;
        this.movie = movie;
        this.fishing = fishing;
        this.music = music;
        this.travel = travel;
        this.sport = sport;
        this.distance = distance;
    }

    public Cards(String userId, String name, int age, String profileImageUrl, String bio, String interest, float distance) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.profileImageUrl = profileImageUrl;
        this.bio = bio;
        this.interest = interest;
        this.distance = distance;
    }

    public Cards(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public float getDistance() {
        return distance;
    }

    public String getBio() {
        return bio;
    }

    public String getInterest() {
        return interest;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public boolean isMovie() {
        return movie;
    }

    public void setMovie(boolean movie) {
        this.movie = movie;
    }

    public boolean isFishing() {
        return fishing;
    }

    public void setFishing(boolean fishing) {
        this.fishing = fishing;
    }

    public boolean isTravel() {
        return travel;
    }

    public void setTravel(boolean travel) {
        this.travel = travel;
    }

    public boolean isSport() {
        return sport;
    }

    public void setSport(boolean sport) {
        this.sport = sport;
    }

    public boolean isMusic() {
        return music;
    }

    public void setMusic(boolean music) {
        this.music = music;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }
}
