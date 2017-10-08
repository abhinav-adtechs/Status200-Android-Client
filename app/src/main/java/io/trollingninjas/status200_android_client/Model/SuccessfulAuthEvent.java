package io.trollingninjas.status200_android_client.Model;


public class SuccessfulAuthEvent {

    private boolean isSuccessful ;
    private String originalQuery ;

    public SuccessfulAuthEvent(boolean isSuccessful, String originalQuery) {
        this.isSuccessful = isSuccessful;
        this.originalQuery = originalQuery;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public String getOriginalQuery() {
        return originalQuery;
    }

    public void setOriginalQuery(String originalQuery) {
        this.originalQuery = originalQuery;
    }
}
