package io.trollingninjas.status200_android_client.Model;


public class VolleyErrorEvent {

    private String errorMessage ;

    public VolleyErrorEvent(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
