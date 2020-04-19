package com.mud.game.structs;


/*
* 位置信息的元数据类型
* x表示横坐标，y表示纵坐标，z表示高度
* x,y在游戏配置中的表现则是房间的二维平面坐标
* */
public class GamePosition {
    private int x;
    private int y;
    private int z;

    public GamePosition(){}

    public GamePosition(String positionStr){
        positionStr = positionStr.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] positionXY = positionStr.split(",");
        this.x = Integer.parseInt(positionXY[0].trim());
        this.y = Integer.parseInt(positionXY[1].trim());
        this.z = 0;
    }

    public GamePosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
