package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class SkyScraper extends Fixed
{
    private int sequenceNumber;
    private boolean isReached;
    private final int ACTIVE;
    private final int REACHED;

    /**
     * Sets color for unreached AND reached skyscraper, size (based off Player helicopter),
     * location, and if it's been reached yet or not
     * @param sequenceNumber  Sets sequential number of the skyscraper
     * @param size            Sets size (which is equal to Player helicopter's size)
     * @param locationX       Sets pre-determined X-coordinate location
     * @param locationY       Sets pre-determined Y-coordinate location
     */
    public SkyScraper(int sequenceNumber, int size, double locationX, double locationY)
    {
        ACTIVE = ColorUtil.rgb(51, 153, 255);
        REACHED = ColorUtil.rgb(51, 10, 255);
        setColor(ACTIVE);
        this.sequenceNumber = sequenceNumber;
        setSize(size);
        setLocationX(locationX);
        setLocationY(locationY);
        isReached = false;
    }

    /**
     * Draw the Skyscraper's color based off if it's been SEQUENTIALLY reached or not.
     * Draws rectangle and a String of its sequence number
     * @param g                  The Graphics object
     * @param containerOrigin    Gets our origin based off how our home component is set
     */
    public void draw(Graphics g, Point containerOrigin)
    {
        if(this.isReached)
            g.setColor(REACHED);
        else
            g.setColor(ACTIVE);

        g.drawRect(containerOrigin.getX() + (int)getLocationX(), containerOrigin.getY() + (int)getLocationY(), getSize(), getSize());
        g.fillRect(containerOrigin.getX() + (int)getLocationX(), containerOrigin.getY() + (int)getLocationY(), getSize(), getSize());

        g.setColor(ColorUtil.BLACK);
        g.drawString(Integer.toString(this.sequenceNumber), containerOrigin.getX()+(int)getLocationX() + (getSize()/2),
                containerOrigin.getY()+(int)getLocationY() + (getSize()/2));
    }

    @Override
    public boolean collidesWith(GameObject otherObject)
    {
        return false;
    }

    @Override
    public void handleCollision(GameObject otherObject)
    {

    }

    public int getSequenceNumber()
    {
        return sequenceNumber;
    }
    public void setReached(boolean r)
    {
        this.isReached = r;
    }
    public void setColor(int r, int g, int b){}

    //Displays sequence number, along with Fixed and GameObject toString variables
    public String toString()
    {
        String parentDesc = super.toString();
        String myDesc = " seqNum = " + sequenceNumber;
        return parentDesc + myDesc;
    }
}
