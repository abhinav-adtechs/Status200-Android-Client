package io.trollingninjas.status200_android_client.Model;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    private static final String TAG = "TAG";

    private CancellationSignal cancellationSignal;
    private Context context;

    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Log.i(TAG, "onAuthenticationError: ");
        EventBus.getDefault().post(new ChatsPOJO("You are authenticated. Processing your request...", Constants.LIST_TYPE_RESPONSE));
    }

    @Override
    public void onAuthenticationFailed() {
        Log.i(TAG, "onAuthenticationFailed: ");
        EventBus.getDefault().post(new ChatsPOJO("You are authenticated. Processing your request...", Constants.LIST_TYPE_RESPONSE));
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Log.i(TAG, "onAuthenticationHelp: ");
        EventBus.getDefault().post(new ChatsPOJO("You are authenticated. Processing your request...", Constants.LIST_TYPE_RESPONSE));
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Log.i(TAG, "onAuthenticationSucceeded: ");
        EventBus.getDefault().post(new ChatsPOJO("You are authenticated. Processing your request...", Constants.LIST_TYPE_RESPONSE));
    }

}
