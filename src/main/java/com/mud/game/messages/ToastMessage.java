package com.mud.game.messages;

public class ToastMessage {
    private String toast;

    public ToastMessage(String toast) {
        this.toast = toast;
    }

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }
}
