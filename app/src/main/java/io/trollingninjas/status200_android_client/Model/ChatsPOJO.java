package io.trollingninjas.status200_android_client.Model;


import java.util.List;

public class ChatsPOJO {


    private String chatMessage ;
    private Integer viewType ;
    private int imageResource ;

    private boolean isDo ;
    private boolean isGithub ;

    private List<String> suggestionsList ;

    public ChatsPOJO(String chatMessage, Integer viewType) {
        this.chatMessage = chatMessage;
        this.viewType = viewType;
    }

    public ChatsPOJO(String chatMessage, Integer viewType, List<String> suggestionsList) {
        this.chatMessage = chatMessage;
        this.viewType = viewType;
        this.suggestionsList = suggestionsList;
    }

    public ChatsPOJO(int imageResource, Integer viewType) {
        this.viewType = viewType;
        this.imageResource = imageResource;
    }

    public ChatsPOJO(Integer viewType, int imageResource, boolean isDo, boolean isGithub) {
        this.viewType = viewType;
        this.imageResource = imageResource;
        this.isDo = isDo;
        this.isGithub = isGithub;
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

    public boolean isDo() {
        return isDo;
    }

    public void setDo(boolean aDo) {
        isDo = aDo;
    }

    public boolean isGithub() {
        return isGithub;
    }

    public void setGithub(boolean github) {
        isGithub = github;
    }

    public List<String> getSuggestionsList() {
        return suggestionsList;
    }

    public void setSuggestionsList(List<String> suggestionsList) {
        this.suggestionsList = suggestionsList;
    }
}
