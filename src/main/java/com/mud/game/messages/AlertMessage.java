package com.mud.game.messages;

public class AlertMessage {
    private String alert;

    public AlertMessage(String alert){
        this.alert = alert;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }
}
