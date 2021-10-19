package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    private final Timer m_timer = initTimer();
    public Robot[] robots;
    public List<Target> targets;
//    public Robot robot;
//    public Target target;
//    public Thread thread;
    
    private static Timer initTimer() 
    {
        return new Timer("events generator", true);
    }
    
    public GameVisualizer(int count)
    {
        this.robots = new Robot[count];
        this.targets = new ArrayList<>();
        for (int i=0; i<count; i++){
            robots[i] = new Robot();
            targets.add(new Target());
            robots[i].setTargetPosition(targets.get(i).getM_targetPositionX(),
                    targets.get(i).getM_targetPositionY());
        }
//        this.robot = new Robot();

//        this.thread = new Thread(robot);
//        this.thread.setDaemon(true);

//        this.target = new Target();

//        this.robot.setTargetPosition(target.getM_targetPositionX(), target.getM_targetPositionY());
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
                onModelUpdateEvent(count);
            }
        }, 0, 10);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
//                target.setPosition(e.getPoint());
//                robot.setTargetPosition(target.getM_targetPositionX(),
//                        target.getM_targetPositionY());
                targets.get(0).setPosition(e.getPoint());
                Target t = targets.get(0);
                targets.remove(0);
                targets.add(t);
                for (int j = 0; j < count; j++){
                    robots[j].setTargetPosition(targets.get(j).getM_targetPositionX(),
                            targets.get(j).getM_targetPositionY());
                }
                repaint();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                for (Robot robot: robots) {
                    robot.setSizeField(getWidth(), getHeight());
                }
                for (Target target: targets) {
                    target.comeBackToField(getWidth(), getHeight());
                }

//                robot.setSizeField(getWidth(), getHeight());
//                target.comeBackToField(getWidth(), getHeight());

//                repaint();
            }
        });
        setDoubleBuffered(true);

    }



    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    
    protected void onModelUpdateEvent(int count)
    {
        for (int i=0; i<count; i++) {
//            robots[i].setTargetPosition(targets.get(i).getM_targetPositionX(),
//                    targets.get(i).getM_targetPositionY());
            robots[i].start();
        }

//        this.robot.start();

//        Target t = targets.get(0);
//        targets.remove(targets.get(0));
//        targets.add(t);
    }

    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i<robots.length; i++) {
            Painter.drawRobot(g2d, Calculator.round(robots[i].getM_PositionX()),
                    Calculator.round(robots[i].getM_PositionY()), robots[i].getM_Direction());
            Painter.drawTarget(g2d, targets.get(i).getM_targetPositionX(),
                    targets.get(i).getM_targetPositionY());
    }

//        Painter.drawRobot(g2d, Calculator.round(robot.getM_PositionX()),
//                Calculator.round(robot.getM_PositionY()), robot.getM_Direction());
//        Painter.drawTarget(g2d, target.getM_targetPositionX(), target.getM_targetPositionY());
    }
}
