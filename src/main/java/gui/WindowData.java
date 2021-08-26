package gui;

import java.io.Serializable;

public class WindowData implements Serializable {
    private static final long serialVersionID = 1L;
    public String name;
    public int width;
    public int height;
    public int positionX;
    public int positionY;
    public boolean isMax;
    public boolean isMin;

    public WindowData(String name, int width, int height,
                      int posX, int posY, boolean isMax, boolean isMin){
        this.name = name;
        this.height = height;
        this.width = width;
        this.positionY = posY;
        this.positionX = posX;
        this.isMax = isMax;
        this.isMin = isMin;
    }

    private WindowData(){

    };
}
