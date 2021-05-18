package gui;

import java.awt.*;

public class Target {
    private volatile int m_targetPositionX = 150;
    private volatile int m_targetPositionY = 100;

    public int getM_targetPositionX(){
        return m_targetPositionX;
    }

    public int getM_targetPositionY(){
        return m_targetPositionY;
    }

    public void setPosition(Point p)
    {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }
}
