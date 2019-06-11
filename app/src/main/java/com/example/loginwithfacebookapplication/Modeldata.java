package com.example.loginwithfacebookapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

public class Modeldata {
    String firstname;
    String lastname;
    String useremail;
    String Imageurl;


    public Modeldata(String firstname, String lastname, String useremail, String Imageurl) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.useremail = useremail;
        this.Imageurl = Imageurl;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }


    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    @BindingAdapter("android:imageurldetail")
    public static void lOaadimage(View view , String Imageurl)
    {


        Context context = view.getContext();
        Glide.with(context).load(Imageurl).into((ImageView) view);

    }
}
