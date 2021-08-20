package gui;

import java.awt.*;

import javax.swing.*;

public class GameWindow extends JInternalFrame
{
    public GameVisualizer m_visualizer;

    public GameWindow() 
    {
        super("Игровое поле", true, true, true, true);
        m_visualizer = new GameVisualizer();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void setSize(int width, int height) {
        m_visualizer.robot.setSizeField(width, height);
        super.setSize(width, height);
    }
}
