package org.csc133.a3;

abstract class Movable extends GameObject
{
    private int heading;
    private int speed;

    //Getters and Setters
    public int getSpeed()
    {
        return this.speed;
    }
    public int getHeading()
    {
        return this.heading;
    }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setHeading(int heading) { this.heading = heading; }

    /**
     * Moves objects based off Math formula. Prevents all movable objects from going
     * out of bounds
     * @param mv The active MapView so we know the bounds we can work with
     */
    public void move(MapView mv)
    {
        super.setLocationX(Math.round(super.getLocationX() + (Math.cos(Math.toRadians(90 - heading)) * speed)));
        super.setLocationY(Math.round(super.getLocationY() + (Math.sin(Math.toRadians(90 - heading)) * speed)));

        if(getLocationX() <= 0)
        {
            setLocationX(0);
            setHeading(90);
        }
        else if(getLocationX() >= mv.getWidth())
        {
            setLocationX(mv.getWidth());
            setHeading(270);
        }

        if(getLocationY() <= 0)
        {
            setLocationY(0);
            setHeading(0);
        }
        else if(getLocationY() >= mv.getHeight())
        {
            setLocationY(mv.getHeight());
            setHeading(180);
        }
    }

    //Returns heading and speed, plus GameObject ToString variables
    public String toString()
    {
        String parentDesc = super.toString();
        String myDesc = " heading = " + heading +
                        " speed = " + speed;
        return parentDesc + myDesc;
    }
}
