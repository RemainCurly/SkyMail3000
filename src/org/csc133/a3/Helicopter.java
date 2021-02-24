package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Helicopter extends Movable implements ISteerable
{
    private static Helicopter h;

    private int stickAngle;  //limit +-40
    private int maxSpeed;
    private int fuelLevel;
    private int fuelConsumptionRate;
    private int maxFuel;
    private int damageLevel;
    private int lastSkyScraperReached;
    private GameWorld gw;

    /**
     * Initializes a Singleton of our Player helicopter
     * @param size Our determined Player helicopter size. Will ALWAYS equal Skyscraper's size
     */
    protected Helicopter(int size)
    {
        setSize(size);            //Helicopter size ALWAYS equals SkyScraper size
        setColor(ColorUtil.rgb(242, 179, 179));
        stickAngle = 0;
        setSpeed(2);
        setHeading(180);
        damageLevel = 0;
        maxSpeed = 10;       fuelLevel = 700;
        maxFuel = fuelLevel;
        fuelConsumptionRate = 1;
        lastSkyScraperReached = 1;
    }

    /**
     * If Player helicopter loses a life, this method starts the Game over with necessary changes to the helicopter
     */
    public void reset()
    {
        setSize(150);
        setColor(ColorUtil.rgb(242, 179, 179));
        stickAngle = 0;
        setSpeed(2);
        setHeading(0);
        damageLevel = 0;
        maxSpeed = 10;       fuelLevel = 700;
        maxFuel = fuelLevel;
        fuelConsumptionRate = 1;
    }

    @Override
    /**
     * Draws a filled circle at the Player helicopter's location, and sets its heading line
     */
    public void draw(Graphics g, Point containerOrigin)
    {
        int linex = containerOrigin.getX() + (int)getLocationX();
        int liney = containerOrigin.getY() + (int)getLocationY();

        g.setColor(this.getColor());
        g.fillArc(containerOrigin.getX() + (int)getLocationX() - getSize()/2, containerOrigin.getY() + (int)getLocationY() - getSize()/2, this.getSize(), this.getSize(), 0, 360);

        g.setColor(ColorUtil.BLACK);
        g.drawLine(linex, liney, linex + (int)(Math.cos(Math.toRadians(90-getHeading())) * getSize()/2), liney + (int)(Math.sin(Math.toRadians(90-getHeading())) * getSize()/2));
    }

    @Override
    public boolean collidesWith(GameObject otherObject)
    {
        if((this.getLocationX() <= otherObject.getLocationX() + otherObject.getSize() &&
            this.getLocationY() <= otherObject.getLocationY() + otherObject.getSize() &&
            this.getLocationX() >= otherObject.getLocationX() - otherObject.getSize() &&
            this.getLocationY() >= otherObject.getLocationY() - otherObject.getSize()) &&
            !isBroken() && getCollidingObjects().isEmpty())
        {
            getCollidingObjects().add(otherObject);
            return true;
        }
        else if((this.getLocationX() <= otherObject.getLocationX() + otherObject.getSize() &&
                 this.getLocationY() <= otherObject.getLocationY() + otherObject.getSize() &&
                 this.getLocationX() >= otherObject.getLocationX() - otherObject.getSize() &&
                 this.getLocationY() >= otherObject.getLocationY() - otherObject.getSize()) &&
                getCollidingObjects().contains(otherObject))
        {
            return false;
        }
        else
        {
            getCollidingObjects().remove(otherObject);
            return false;
        }
    }

    @Override
    public void handleCollision(GameObject otherObject)
    {
        if(otherObject instanceof Bird)
            gw.collide('b');
        if(otherObject instanceof RefuelingBlimp)
        {
            RefuelingBlimp rb = (RefuelingBlimp)otherObject;
            if(!rb.isEmpty())
            {
                gw.refuel();
                rb.handleCollision(h);
            }
        }
        if(otherObject instanceof SkyScraper)
        {
            SkyScraper s = (SkyScraper)otherObject;
            gw.travel(s.getSequenceNumber());
        }
        if(otherObject instanceof NonPlayerHelicopter)
        {
            NonPlayerHelicopter n = (NonPlayerHelicopter)otherObject;
            n.collision(30);
            gw.collide('h');
        }
    }

    /**
     * Singleton strategy. If no Player helicopter exists, create one. Return the instance of the Player helicopter
     * instead of allowing other Player helicopters to be created
     * @return Instance of Player helicopter
     */
    public static synchronized Helicopter getInstance()
    {
        if(h == null)
            h = new Helicopter(150);

        return h;
    }

    //True if out of fuel. False otherwise
    public boolean outtaGas()
    {
        if(fuelLevel <= 0)
            return true;
        return false;
    }

    //True if max damage has been reached. False otherwise
    public boolean isBroken()
    {
        if(damageLevel >= 100)
            return true;
        return false;
    }

    //Alters stickAngle to be tilted in negative direction. Gives message if already fully tilted left
    public void tiltStickLeft(int amount)
    {
        int result = stickAngle - amount;

        if(result <= -40)
        {
            stickAngle = -40;
            System.out.println("stickAngle is fully tilted left!");
        }
        else
        {
            stickAngle = result;
            System.out.println("stickAngle adjusted 5 degrees left!");
        }
    }

    public void fetchGW(GameWorld g)
    {
        gw = g;
    }

    //Alters stickAngle to be tilted in positive direction. Gives message if already fully tilted right
    public void tiltStickRight(int amount)
    {
        int result = stickAngle + amount;

        if(result >= 40) {
            stickAngle = 40;
            System.out.println("stickAngle is fully tilted right!");
        }
        else
        {
            stickAngle = result;
            System.out.println("stickAngle adjusted 5 degrees right!");
        }
    }

    //Takes damage (with amount provided in param). Fades color based off current level of damage
    public void collision(int damage)
    {
        damageLevel += damage;

        if(damageLevel > 0 && damageLevel < 30)
        {
            setColor(ColorUtil.rgb(204, 147, 159));
            maxSpeed = 8;
        }
        else if(damageLevel >= 30 && damageLevel < 60)
        {
            setColor(ColorUtil.rgb(255, 102, 102));
            maxSpeed = 5;
        }
        else if(damageLevel >= 60 && damageLevel < 99)
        {
            setColor(ColorUtil.rgb(179, 0, 0));
            maxSpeed = 2;
        }
        else
            setColor(ColorUtil.rgb(77, 0, 0));
    }

    public void consumeFuel()
    {
        fuelLevel -= fuelConsumptionRate;
    }

    //stickAngle alters heading one clock tick at a time. Heading is constrained to 0-359 range
    //    After changing heading based on stickAngle, follows traditional move() method
    @Override
    public void move(MapView mv)
    {
        if(stickAngle <= -5)
        {
            stickAngle += 5;
            setHeading(getHeading() - 5);
            if(getHeading() < 0)
                setHeading(360 + getHeading());
        }
        else if(stickAngle >= 5)
        {
            stickAngle -= 5;
            setHeading(getHeading() + 5);
            if(getHeading() > 359)
                setHeading(getHeading() - 360);
        }

        if(!outtaGas() && !isBroken())
            super.move(mv);
    }

    //Getters and Setters
    public int getDamageLevel()
    {
        return this.damageLevel;
    }
    public int getLastSkyScraperReached() { return this.lastSkyScraperReached; }
    public int getFuelLevel() { return this.fuelLevel; }
    public int getMaxFuel() { return this.maxFuel; }
    public int getMaxSpeed() { return this.maxSpeed; }
    public void setLastSkyScraperReached(int newSkyScraper) { this.lastSkyScraperReached = newSkyScraper; }
    public void setFuelLevel(int fuel) { this.fuelLevel = fuel; }
    public void setMaxSpeed(int maxSpeed) { this.maxSpeed = maxSpeed; }

    //Displays maxSpeed, stickAngle, fuelLevel, and DamageLevel (along with parent toString variables)
    @Override
    public String toString()
    {
        String parentDesc = super.toString();
        String myDesc = " maxSpeed = " + maxSpeed +
                " stickAngle = " + stickAngle +
                " fuelLevel = " + fuelLevel +
                " damageLevel = " + damageLevel;
        return parentDesc + myDesc;
    }

}
