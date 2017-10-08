package io.trollingninjas.status200_android_client.Firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;



public class CustomInstanceRegistration extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("TAG", "Refreshed token: " + refreshedToken);

        super.onTokenRefresh();


    }
}
