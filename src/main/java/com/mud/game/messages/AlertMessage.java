package com.mud.game.messages;


/**
 *  返回信息类： 弹窗提示信息
 *  <pre>
 *  返回示例：
 *  {
 *      "alert": "返回消息主体！"
 *  }
 *  </pre>
 *
 * */

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
