package gui;

import java.awt.*;
import java.io.Serializable;

public class Target implements Serializable {
    private volatile int positionX = 150;
    private volatile int positionY = 100;

    public int getM_targetPositionX(){
        return positionX;
    }

    public int getM_targetPositionY(){
        return positionY;
    }

    public void setPosition(Point p)
    {
        positionX = p.x;
        positionY = p.y;
    }

    public void comeBackToField(int width, int height){
        positionX = (int) Calculator.applyLimits(positionX, 1, width-1);
        positionY = (int) Calculator.applyLimits(positionY, 1, height-1);
    }
}
