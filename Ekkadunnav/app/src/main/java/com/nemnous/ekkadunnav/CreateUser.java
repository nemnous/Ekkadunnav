package com.nemnous.ekkadunnav;

import java.util.HashMap;

public class CreateUser {
    public  String userid, name, email, password, code, isSharing, lat, lon, imageUrl;

    HashMap<String, HashMap<String, String>> myCircle;

    public CreateUser() {
        //unused constructor
    }


    public CreateUser(String userid, String name, String email, String password, String code, String isSharing, String lat, String lon, String imageUrl, HashMap<String, HashMap<String, String>> myCircle) {

        this.myCircle = myCircle;
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.isSharing = isSharing;
        this.lat = lat;
        this.lon = lon;
        this.imageUrl = imageUrl;
        this.userid = userid;
    }
}
