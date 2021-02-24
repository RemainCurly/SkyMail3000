package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class RefuelingBlimp extends Fixed
{
    private int capacity;

    /**
     * Sets random blimp size, and matches the capacity to the size
     * Also sets it to a random location on the map
     */
    public RefuelingBlimp()
    {
        setSize(getRand(50, 125));
        setColor(ColorUtil.rgb(57, 230, 0));
        capacity = (this.getSize() * 2);
        setLocationX(getRand(getX_BOUND()));
        setLocationY(getRand(getY_BOUND()));
    }

    @Override
    public boolean collidesWith(GameObject otherObject)
    {
        if((this.getLocationX() <= otherObject.getLocationX() + otherObject.getSize() &&
                this.getLocationY() <= otherObject.getLocationY() + otherObject.getSize() &&
                this.getLocationX() >= otherObject.getLocationX() - otherObject.getSize() &&
                this.getLocationY() >= otherObject.getLocationY() - otherObject.getSize()) &&
                getCollidingObjects().isEmpty())
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
        if(otherObject instanceof Helicopter || otherObject instanceof NonPlayerHelicopter)
        {
            this.setCapacity(0);
            setColor(ColorUtil.rgb(19, 77, 0));
        }
    }

    public int getCapacity() { return this.capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public boolean isEmpty()
    {
        return this.capacity <= 0;
    }

    //Displays capacity, along with Fixed and GameObject toString variables
    @Override
    public String toString()
    {
        String parentDesc = super.toString();
        String myDesc = " capacity = " + capacity;
        return parentDesc + myDesc;
    }

    /**
     * Draws an ellipse, with a changeable color and a changeable string that indicates the capacity
     * @param g The Graphics object. I like thinking of it like a paintbrush, since it does the drawing
     * @param containerOrigin Gets our origin based off how our home component is set
     */
    public void draw(Graphics g, Point containerOrigin)
    {
        g.setColor(this.getColor());

        g.drawArc(containerOrigin.getX() + (int)getLocationX(), containerOrigin.getY() + (int)getLocationY(), getSize()*2, getSize(), 0, 360);
        g.fillArc(containerOrigin.getX() + (int)getLocationX(), containerOrigin.getY() + (int)getLocationY(), getSize()*2, getSize(), 0, 360);

        g.setColor(ColorUtil.BLACK);
        g.drawString(Integer.toString(this.capacity), containerOrigin.getX()+(int)getLocationX() + (getSize()/2), containerOrigin.getY()+(int)getLocationY() + (getSize()/3));
    }

    //Overridden methods
    @Override
    public void setLocationX(double locationX) {}
    @Override
    public void setLocationY(double locationY) {}
}
