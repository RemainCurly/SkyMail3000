package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;

import java.util.Random;
import java.util.Vector;

abstract class GameObject implements IDrawable, ICollider
{
    private int size;
    private int color;
    private double locationX;
    private double locationY;
    private final double X_BOUND;
    private final double Y_BOUND;
    private Random ran = new Random();
    private Vector<GameObject> collidingObjects = new Vector<GameObject>();

    /**
     * X and Y bounds are set to the size of the map
     * Most GameObjects start off at random locations
     */
    public GameObject()
    {
        X_BOUND = 1125.0;
        Y_BOUND = 1723.0;
        size = 0;

        locationX = getRand(X_BOUND);
        locationY = getRand(Y_BOUND);
    }


    //Return a random int between bounds (x, x+y)
    public int getRand(int x, int y)
    {
        return x + ran.nextInt(y++);
    }

    //Returns a random double between bounds (0, y)
    public double getRand(double y)
    {
        return Math.round(y++ * ran.nextDouble());
    }

    //Getters & Setters. "setLocation" methods are overridden under Fixed objects
    public Vector<GameObject> getCollidingObjects()
    {
        return collidingObjects;
    }
    public double getX_BOUND()
    {
        return X_BOUND;
    }
    public double getY_BOUND()
    {
        return Y_BOUND;
    }
    public int getSize()
    {
        return this.size;
    }
    public void setSize(int size) { this.size = size; }
    public double getLocationX()
    {
        return this.locationX;
    }
    public double getLocationY()
    {
        return this.locationY;
    }
    public void setLocationX(double locationX)
    {
        this.locationX = locationX;
    }
    public void setLocationY(double locationY)
    {
        this.locationY = locationY;
    }
    public int getColor()
    {
        return this.color;
    }
    public void setColor(int color)
    {
        this.color = color;
    }

    //Lists GameObject's location, color, and size
    public String toString()
    {
        String desc = "loc = " + locationX + "," + locationY + " " +
                "color = [" + ColorUtil.red(color) + "," + ColorUtil.green(color) + "," + ColorUtil.blue(color) + "] " +
                "size = " + size;
        return desc;
    }
}
