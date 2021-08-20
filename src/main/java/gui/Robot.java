package gui;


import java.io.Serializable;

public class Robot implements Serializable {
    private volatile double m_PositionX = 100;
    private volatile double m_PositionY = 100;
    private volatile double m_Direction = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    private int fieldHeight;
    private int fieldWidth;

    Robot(){
        setSizeField(400, 400);
    }

    public void setSizeField(int width, int height){
        this.fieldHeight = height;
        this.fieldWidth = width;
    }

    public double getM_PositionX(){
        return m_PositionX;
    }

    public double getM_PositionY(){
        return m_PositionY;
    }

    public double getM_Direction(){
        return m_Direction;
    }

    public void updateCoordinates(double velocity, double angularVelocity)
    {

        double duration = 10;
        velocity = Calculator.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = Calculator.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_PositionX + velocity / angularVelocity *
                (Math.sin(m_Direction  + angularVelocity * duration) -
                        Math.sin(m_Direction));
        if (!Double.isFinite(newX))
        {
            newX = m_PositionX + velocity * duration * Math.cos(m_Direction);
        }
        double newY = m_PositionY - velocity / angularVelocity *
                (Math.cos(m_Direction  + angularVelocity * duration) -
                        Math.cos(m_Direction));
        if (!Double.isFinite(newY))
        {
            newY = m_PositionY + velocity * duration * Math.sin(m_Direction);
        }
        m_PositionX = (newX + fieldWidth) % fieldWidth;
        m_PositionY = (newY + fieldHeight) % fieldHeight;
        double newDirection = Calculator.asNormalizedRadians(m_Direction + angularVelocity * duration);
        m_Direction = newDirection;
    }

    public void moveTo(double robotX, double robotY, double targetX, double targetY)
    {
        double distance = Calculator.distance(targetX, targetY, robotX, robotY);
        if (distance < 0.5)
        {
            return;
        }
        double angleToTarget = Calculator.angleTo(robotX, robotY, targetX, targetY);
        double angularVelocity = 0;
        if (angleToTarget > m_Direction)
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < m_Direction)
        {
            angularVelocity = -maxAngularVelocity;
        }
        updateCoordinates(maxVelocity, angularVelocity);
    }
}
