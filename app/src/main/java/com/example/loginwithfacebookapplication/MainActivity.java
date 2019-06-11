package com.example.loginwithfacebookapplication;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.loginwithfacebookapplication.R;
import com.example.loginwithfacebookapplication.databinding.ActivityMainBinding;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
     ActivityMainBinding activityMainBinding;
     CallbackManager callbackManager;
     ViewmodelData viewmodelData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setLifecycleOwner(this);
        viewmodelData = ViewModelProviders.of(this).get(ViewmodelData.class);
        callbackManager = CallbackManager.Factory.create();
        activityMainBinding.loginbutton.setReadPermissions(Arrays.asList("email","public_profile"));
        printhashkey();
        chechuserlogin();
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken==null)
                {
                    activityMainBinding.textView4.setText("");
                    activityMainBinding.textView5.setText("");
                    activityMainBinding.circleImageView2.setImageResource(R.drawable.com_facebook_button_icon);
                    Toast.makeText(MainActivity.this,"useer is logged out ",Toast.LENGTH_SHORT).show();

                }
                else {
                    LoadProfiledata(currentAccessToken);
                }

            }
        };

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this,"sucesss",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this,"cancelll",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void printhashkey(){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.loginwithfacebookapplication",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHashFacebookloginn:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }
    public void LoadProfiledata(AccessToken accessToken)
    {


        viewmodelData.getData(accessToken).observe(this, new Observer<Modeldata>() {
            @Override
            public void onChanged(Modeldata modeldata) {
                activityMainBinding.setModelddata(modeldata);
                //Toast.makeText(MainActivity.this,modeldata.getFirstname().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void chechuserlogin()
    {
        if (AccessToken.getCurrentAccessToken()!=null)
        {
            LoadProfiledata(AccessToken.getCurrentAccessToken());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);

    }

}


