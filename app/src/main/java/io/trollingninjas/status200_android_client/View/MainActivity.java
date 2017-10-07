package io.trollingninjas.status200_android_client.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.trollingninjas.status200_android_client.Model.ChatsPOJO;
import io.trollingninjas.status200_android_client.Model.Constants;
import io.trollingninjas.status200_android_client.R;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.activity_main_rv_chat)
    RecyclerView rvChatData ;

    private List<ChatsPOJO> chatsList = new ArrayList<>();
    private ChatsAdapter chatsAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this) ;

        setupRv() ;
    }

    private void setupRv() {
        chatsAdapter = new ChatsAdapter(this, chatsList) ;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,  true) ;
        rvChatData.setLayoutManager(layoutManager);
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

}
