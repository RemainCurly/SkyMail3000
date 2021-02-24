package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class Bird extends Movable
{
    //Initializes bird with random size, location, heading, speed.
    //The only thing that ISN'T random about Birds are color

    public Bird(int color)
    {
        setSize(getRand(30, 85));
        setLocationX(getRand(getX_BOUND()));
        setLocationY(getRand(getY_BOUND()));
        setHeading(getRand(0, 359));
        setSpeed(getRand(5, 5));
        setColor(color);
    }

    /**
     * Birds' headings get altered slightly and randomly as they move
     * @param mv
     */
    public void move(MapView mv)
    {
        super.move(mv);

        setHeading(getHeading() + getRand(-5, 10));
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

    //Overridden method. Can't change color of Bird once initialized
    public void setColor(int r, int g, int b)
    {}

    /**
     * Finds the Bird's location, draws an empty circle, and give it a header line to determine location
     * @param g
     * @param containerOrigin
     */
    public void draw(Graphics g, Point containerOrigin)
    {
        int linex = containerOrigin.getX() + (int)getLocationX();
        int liney = containerOrigin.getY() + (int)getLocationY();

        g.setColor(this.getColor());
        g.drawArc(containerOrigin.getX() + (int)getLocationX() - getSize()/2, containerOrigin.getY() + (int)getLocationY() - getSize()/2, this.getSize(), this.getSize(), 0, 360);

        g.setColor(ColorUtil.BLACK);
        g.drawLine(linex, liney, linex + (int)(Math.cos(Math.toRadians(90-getHeading())) * getSize()/2), liney + (int)(Math.sin(Math.toRadians(90-getHeading())) * getSize()/2));

    }
}
