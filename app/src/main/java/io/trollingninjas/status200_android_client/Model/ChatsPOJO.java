package io.trollingninjas.status200_android_client.Model;



public class ChatsPOJO {


    private String chatMessage ;
    private Integer viewType ;
    private int imageResource ;

    public ChatsPOJO(String chatMessage, Integer viewType) {
        this.chatMessage = chatMessage;
        this.viewType = viewType;
    }

    public ChatsPOJO(int imageResource, Integer viewType) {
        this.viewType = viewType;
        this.imageResource = imageResource;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
