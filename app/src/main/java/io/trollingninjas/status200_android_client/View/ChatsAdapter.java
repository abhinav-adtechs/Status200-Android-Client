package io.trollingninjas.status200_android_client.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.trollingninjas.status200_android_client.Model.ChatsPOJO;
import io.trollingninjas.status200_android_client.Model.Constants;
import io.trollingninjas.status200_android_client.R;


public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context ;
    private List<ChatsPOJO> chatsList;

    public ChatsAdapter(Context context, List<ChatsPOJO> chatsList) {
        this.context = context;
        this.chatsList = chatsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView ;

        if (viewType == Constants.LIST_TYPE_REQUEST){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rv_chats, parent, false) ;
            return new ChatsViewHolder(itemView);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rv_chat_response, parent, false) ;
            return new ChatResponseViewHolder(itemView) ;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == Constants.LIST_TYPE_REQUEST){
            ChatsViewHolder holderRequest = (ChatsViewHolder) holder ;
            holderRequest.tvChatRequest.setText(chatsList.get(position).getChatMessage());


        }else if (getItemViewType(position) == Constants.LIST_TYPE_RESPONSE){

            ChatResponseViewHolder holderResponse = (ChatResponseViewHolder) holder ;

            holderResponse.tvChatResponse.setText(chatsList.get(position).getChatMessage());
            holderResponse.tvChatResponse.setGravity(Gravity.LEFT);
        }
    }


    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvChatRequest ;

        public ChatsViewHolder(View itemView) {
            super(itemView);

            tvChatRequest = (TextView) itemView.findViewById(R.id.item_rv_chats_tv_chat_request) ;
        }
    }

    public class ChatResponseViewHolder extends RecyclerView.ViewHolder{

        private TextView tvChatResponse ;

        public ChatResponseViewHolder(View itemView) {
            super(itemView);

            tvChatResponse = (TextView) itemView.findViewById(R.id.item_rv_chat_response_tv_response) ;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return chatsList.get(position).getViewType() ;
    }
}
