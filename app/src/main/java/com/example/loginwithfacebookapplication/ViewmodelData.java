package com.example.loginwithfacebookapplication;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ViewmodelData extends ViewModel {
    MutableLiveData<Modeldata>mutableLiveData = new MutableLiveData<>();


    public LiveData<Modeldata>getData(AccessToken accessToken)
    {

        GraphRequest graphRequest = new GraphRequest().newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                if (object!=null)
                {
                    try {
                        String firstname = object.getString("first_name");
                        String lastname = object.getString("last_name");
                        String email = object.getString("email");
                        String serid = object.getString("id");
                        String Image_url = "https://graph.facebook.com/"+serid+"/picture?type=normal";
                        Modeldata modeldata = new Modeldata(firstname,lastname,email,Image_url);
                        mutableLiveData.setValue(modeldata);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
        return mutableLiveData;
    }

}
