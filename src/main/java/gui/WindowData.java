package gui;

import java.io.Serializable;

public class WindowData implements Serializable {
    private static final long serialVersionUID = 1L;

    public String name;
    public int width;
    public int height;
    public int positionX;
    public int positionY;
    public boolean isMinimized;
    public boolean isMaximized;

    public WindowData(String name, int width, int height, int positionX, int positionY){
        this.name = name;
        this.width = width;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;
    }
    public WindowData(){}
}
