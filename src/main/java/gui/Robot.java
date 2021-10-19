package gui;


public class Robot implements Runnable{
    private volatile double m_PositionX = 100;
    private volatile double m_PositionY = 100;
    private volatile double m_Direction = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    private int fieldHeight;
    private int fieldWidth;
    
    private double targetPositionX;
    private double targetPositionY;

    private Thread thread;

    Robot(){
        setSizeField((int) (Math.random()*400), (int) (Math.random() * 400));
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
    
    public void setTargetPosition(double x, double y){
        this.targetPositionX = x;
        this.targetPositionY = y;
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

    public void moveTo()
    {
        double robotX = getM_PositionX();
        double robotY = getM_PositionY();
        double distance = Calculator.distance(targetPositionX, targetPositionY, robotX, robotY);
        if (distance < 0.5)
        {
            return;
        }
        double angleToTarget = Calculator.angleTo(robotX, robotY,
                targetPositionX, targetPositionY);
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
//        System.out.println("go to " + targetPositionX + " " + targetPositionY);
    }

    @Override
    public void run() {
        moveTo();
//        System.out.println("go to " + targetPositionX + " " + targetPositionY);
    }

    public void start(){
        thread = new Thread(this);
        thread.start();
    }
}
