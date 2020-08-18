package com.mud.game.config.errorConfig;

public class Msg {

    /**
     * 返回码
     */
    private int resultCode;

    /**
     * 备注信息
     */
    private String info;

    /**
     * 空构造函数
     */
    public Msg() {
    }

    /**
     * 自定义消息
     *
     * @param resultCode resultCode
     * @param info       info
     */
    public Msg(int resultCode, String info) {
        this.resultCode = resultCode;
        this.info = info;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}