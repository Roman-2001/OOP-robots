package gui;

import java.awt.*;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;

    public GameWindow(int count)
    {
        super("Игровое поле", true, true, true, true);
//        Dimension size = super.getSize();
        m_visualizer = new GameVisualizer(count);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        super.setName("game");
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void setSize(int width, int height) {
        for (Robot robot: m_visualizer.robots) {
            robot.setSizeField(width, height);
        }
//        m_visualizer.robot.setSizeField(width, height);
        super.setSize(width, height);
    }
}
