package io.trollingninjas.status200_android_client.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.trollingninjas.status200_android_client.Model.ChatsPOJO;
import io.trollingninjas.status200_android_client.Model.Constants;
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
    private LayoutAnimationController controller ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this) ;

        ivSend.setOnClickListener(this);
        ivMicrophone.setOnClickListener(this);
        ivSend.setClickable(false);
        setupRv() ;
        init() ;
    }

    private void init() {
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
        controller = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        rvChatData.setLayoutAnimation(controller);
        rvChatData.setItemAnimator(new DefaultItemAnimator());
        rvChatData.setAdapter(chatsAdapter);

        setData() ;
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

    private void getResponse(String query) {

        Collections.reverse(chatsList);
        chatsList.add(new ChatsPOJO("Thanks for asking me: " + query, Constants.LIST_TYPE_RESPONSE)) ;
        Collections.reverse(chatsList);
        chatsAdapter.notifyDataSetChanged();
    }
}
