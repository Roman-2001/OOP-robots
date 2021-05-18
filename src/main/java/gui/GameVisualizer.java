package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    private final Timer m_timer = initTimer();
    public Robot robot;
    public Target target;
    
    private static Timer initTimer() 
    {
        Timer timer = new Timer("events generator", true);
        return timer;
    }
    
    public GameVisualizer(int width, int height)
    {
        this.robot = new Robot(width, height);
        this.target = new Target();
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onModelUpdateEvent();
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                target.setPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);

    }

    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    
    protected void onModelUpdateEvent()
    {
        robot.moveTo(robot.getM_PositionX(), robot.getM_PositionY(),
                target.getM_targetPositionX(), target.getM_targetPositionY());
    }

    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        Painter.drawRobot(g2d, Calculator.round(robot.getM_PositionX()),
                Calculator.round(robot.getM_PositionY()), robot.getM_Direction());
        Painter.drawTarget(g2d, target.getM_targetPositionX(), target.getM_targetPositionY());
    }
}
