package io.trollingninjas.status200_android_client.View;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.eyalbira.loadingdots.LoadingDots;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.trollingninjas.status200_android_client.Model.ChatsPOJO;
import io.trollingninjas.status200_android_client.Model.Constants;
import io.trollingninjas.status200_android_client.Model.FingerprintHandler;
import io.trollingninjas.status200_android_client.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.activity_main_rv_chat)
    RecyclerView rvChatData ;

    @BindView(R.id.activity_main_et_query)
    AppCompatEditText etQueryText ;

    @BindView(R.id.activity_main_iv_microphone)
    ImageView ivMicrophone ;

    @BindView(R.id.activity_main_iv_send)
    ImageView ivSend ;

    private List<ChatsPOJO> chatsList = new ArrayList<>();
    private ChatsAdapter chatsAdapter ;

    private static final String KEY_NAME = "keyy";
    LoadingDots loadingDots ;


    /**SUGGESTIONSSSSUGGESTIONSSSSUGGESTIONSSSSUGGESTIONSSS
     * */
    @BindView(R.id.activity_main_rv_suggestion)
    RecyclerView rvSuggestion ;

    private List<ChatsPOJO> suggestionsList = new ArrayList<>() ;
    private ChatsAdapter suggestionsAdapter ;

    /**SUGGESTIONSSSSUGGESTIONSSSSUGGESTIONSSSSUGGESTIONSSS
     * */

    KeyguardManager keyguardManager ;
    FingerprintManager fingerprintManager ;
    FingerprintManager.CryptoObject cryptoObject ;
    KeyStore keyStore ;
    KeyGenerator keyGenerator ;
    Cipher cipher ;

    @BindView(R.id.activity_main_toolbar)
    Toolbar toolbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this) ;
        EventBus.getDefault().register(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        /*getSupportActionBar().setLogo(R.drawable.globe);*/


        ivSend.setOnClickListener(this);
        ivMicrophone.setOnClickListener(this);
        ivSend.setClickable(false);
        setupRv() ;
        init() ;
    }

    private void init() {
        loadingDots = (LoadingDots) findViewById(R.id.activity_main_loading_dots) ;

        etQueryText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0){
                    ivMicrophone.setImageResource(R.drawable.ic_microphone_disabled);
                    ivMicrophone.setClickable(false);
                    ivSend.setImageResource(R.drawable.ic_send);
                    ivSend.setClickable(true);

                }else {
                    ivMicrophone.setImageResource(R.drawable.ic_microphone_2);
                    ivMicrophone.setClickable(true);
                    ivSend.setImageResource(R.drawable.ic_send_disabled);
                    ivSend.setClickable(false);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setupRv() {
        chatsAdapter = new ChatsAdapter(this, chatsList) ;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,  true) ;
        rvChatData.setLayoutManager(layoutManager);
        rvChatData.setItemAnimator(new DefaultItemAnimator());
        rvChatData.setAdapter(chatsAdapter);
        setData() ;



        suggestionsAdapter = new ChatsAdapter(this, suggestionsList, new ChatsAdapter.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Log.i("TAG", "onClick: ");
                postMessage(new ChatsPOJO(suggestionsList.get(pos).getChatMessage(), Constants.LIST_TYPE_REQUEST));
                getResponse(suggestionsList.get(pos).getChatMessage());
            }
        }) ;
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false) ;
        rvSuggestion.setLayoutManager(layoutManager1);
        rvSuggestion.setAdapter(suggestionsAdapter);
        setSuggestionData() ;

    }

    private void setSuggestionData() {
        suggestionsList.add(new ChatsPOJO("Pull changes to server", Constants.LIST_TYPE_REQUEST)) ;
        suggestionsList.add(new ChatsPOJO("Show status", Constants.LIST_TYPE_REQUEST)) ;
        suggestionsList.add(new ChatsPOJO("Set a reminder", Constants.LIST_TYPE_REQUEST)) ;
        suggestionsList.add(new ChatsPOJO("Show Weekly Report", Constants.LIST_TYPE_REQUEST)) ;

        suggestionsAdapter.notifyDataSetChanged();
    }

    private void setData() {
        chatsList.add(new ChatsPOJO("Request 1", Constants.LIST_TYPE_REQUEST)) ;
        chatsList.add(new ChatsPOJO("Yes, one one one one \n one one one", Constants.LIST_TYPE_RESPONSE)) ;
        chatsList.add(new ChatsPOJO("Request 2 two two ", Constants.LIST_TYPE_REQUEST)) ;
        chatsList.add(new ChatsPOJO("No, two", Constants.LIST_TYPE_RESPONSE)) ;
        Collections.reverse(chatsList);

        chatsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.activity_main_iv_send:
                String query = etQueryText.getText().toString() ;
                Log.i("TAG", "onClick: " + query);
                Collections.reverse(chatsList);
                chatsList.add(new ChatsPOJO(query, Constants.LIST_TYPE_REQUEST)) ;
                Collections.reverse(chatsList);
                chatsAdapter.notifyDataSetChanged();
                getResponse(query) ;
                etQueryText.setText("");
                break;
            case R.id.activity_main_iv_microphone:
                break;
        }
    }

    private void getResponse(final String query) {
        rvSuggestion.setVisibility(View.INVISIBLE);
        loadingDots.setAutoPlay(true);
        loadingDots.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDots.setAutoPlay(false);
                loadingDots.setVisibility(View.GONE);
                rvSuggestion.setVisibility(View.VISIBLE);

                Collections.reverse(chatsList);
                chatsList.add(new ChatsPOJO("Thanks for asking me: " + query, Constants.LIST_TYPE_RESPONSE)) ;
                Collections.reverse(chatsList);
                if (query.contains("Login")){
                    try {
                        getFingerprintAuth();
                    } catch (java.security.cert.CertificateException e) {
                        e.printStackTrace();
                    }
                }
                chatsAdapter.notifyDataSetChanged();
            }
        }, 3000);


    }

    private void getFingerprintAuth() throws java.security.cert.CertificateException {
        Collections.reverse(chatsList);
        chatsList.add(new ChatsPOJO("Please authenticate by placing your finger on the fingerprint sensor.", Constants.LIST_TYPE_RESPONSE)) ;
        chatsList.add(new ChatsPOJO(R.drawable.ic_fingerprint, Constants.LIST_TYPE_RESPONSE_IMAGE)) ;
        Collections.reverse(chatsList);
        chatsAdapter.notifyDataSetChanged();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            if (!fingerprintManager.isHardwareDetected()) {
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {
            }
            if (!keyguardManager.isKeyguardSecure()) {
            } else {
                try {
                    generateKey();
                } catch (FingerprintException e) {
                    e.printStackTrace();
                }
                if (initCipher()) {
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }
            }
        }
    }




    public class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void postMessage(ChatsPOJO chatsPOJO){
        Collections.reverse(chatsList);
        chatsList.add(chatsPOJO) ;
        Collections.reverse(chatsList);
        chatsAdapter.notifyDataSetChanged();
    }




    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean initCipher() throws java.security.cert.CertificateException {
        try {
            //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //Return true if the cipher has been initialized successfully//
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {

            //Return false if cipher initialization failed//
            return false;
        } catch (KeyStoreException | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void generateKey() throws MainActivity.FingerprintException {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());

            //Generate the key//
            keyGenerator.generateKey();

        } catch (KeyStoreException | NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | IOException | java.security.cert.CertificateException exc) {
            exc.printStackTrace();
        }
    }


}

