package io.trollingninjas.status200_android_client.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import io.trollingninjas.status200_android_client.Model.ChatsPOJO;
import io.trollingninjas.status200_android_client.Model.Constants;
import io.trollingninjas.status200_android_client.R;


public class ChatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context ;
    private List<ChatsPOJO> chatsList;
    private OnItemClickListener onItemClickListener ;
    private boolean flag = false ;

    public interface OnItemClickListener{
        void onClick(int pos) ;
    }

    public ChatsAdapter(Context context, List<ChatsPOJO> chatsList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.chatsList = chatsList;
        this.onItemClickListener = onItemClickListener;
        flag = true ;
    }

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
        }else if (viewType == Constants.LIST_TYPE_RESPONSE){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rv_chat_response, parent, false) ;
            return new ChatResponseViewHolder(itemView) ;
        } else if (viewType == Constants.LIST_TYPE_RESPONSE_IMAGE){
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rv_chat_response_image, parent, false) ;
            return new ChatResponseImageViewHolder(itemView) ;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == Constants.LIST_TYPE_REQUEST){
            ChatsViewHolder holderRequest = (ChatsViewHolder) holder ;
            holderRequest.tvChatRequest.setText(chatsList.get(position).getChatMessage());

            if (flag){

                holderRequest.flChatBody.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                holderRequest.bindListener(position);
            }


        }else if (getItemViewType(position) == Constants.LIST_TYPE_RESPONSE){

            ChatResponseViewHolder holderResponse = (ChatResponseViewHolder) holder ;

            holderResponse.tvChatResponse.setText(chatsList.get(position).getChatMessage());
            holderResponse.tvChatResponse.setGravity(Gravity.LEFT);

        }else if (getItemViewType(position) == Constants.LIST_TYPE_RESPONSE_IMAGE){

            ChatResponseImageViewHolder holderResponseImage = (ChatResponseImageViewHolder) holder ;
            holderResponseImage.ivImage.setImageResource(chatsList.get(position).getImageResource());
        }
    }


    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public class ChatsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvChatRequest ;
        private FrameLayout flChatBody ;

        public ChatsViewHolder(View itemView) {
            super(itemView);

            tvChatRequest = (TextView) itemView.findViewById(R.id.item_rv_chats_tv_chat_request) ;
            flChatBody = (FrameLayout) itemView.findViewById(R.id.item_rv_chats_frame_layout) ;
        }

        public void bindListener(final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(position);
                }
            });
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

    public class ChatResponseImageViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivImage ;

        public ChatResponseImageViewHolder(View itemView) {
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.item_rv_chat_response_image_iv) ;
        }
    }
}
