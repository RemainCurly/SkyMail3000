package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class NonPlayerHelicopter extends Helicopter
{
    private int stickAngle;
    private int damageLevel;
    private int lastSkyScraperReached;
    IStrategy strat;

    /**
     * Sets a lot of initial characteristics, similar to Player helicopter.
     * Except NPHs have strategy and look at the next skyScraper
     * @param size      Determines how big NPHs are. Should be equal to Player helicopter's size
     */
    public NonPlayerHelicopter(int size)
    {
        super(size);
        setColor(ColorUtil.rgb(2, 50, 135));
        stickAngle = 0;
        setSpeed(1);
        setHeading(getRand(15, 300));
        setStrategy(new WinStrategy());
        setMaxSpeed(2);
        damageLevel = 0;
        lastSkyScraperReached = 1;
    }

    /**
     * Checks if the NPH has finished the Player helicopter's task
     * @param num_skyscrapers    To check the required number of skyscrapers to sequentially reach
     * @return                   True if the NPH has sequentially reached all the skyscrapers, false otherwise
     */
    public boolean wonGame(int num_skyscrapers)
    {
        return lastSkyScraperReached == num_skyscrapers;
    }

    @Override
    /**
     * Keeps NPHs at infinite fuel, draws them, and sets their heading lines
     * @param g                  The Graphics object
     * @param containerOrigin    The origin coordinates of CN1's Frame
     */
    public void draw(Graphics g, Point containerOrigin)
    {
        int linex = containerOrigin.getX() + (int)getLocationX();
        int liney = containerOrigin.getY() + (int)getLocationY();

        setFuelLevel(1000);

        g.setColor(this.getColor());
        g.fillArc(containerOrigin.getX() + (int)getLocationX() - getSize()/2, containerOrigin.getY() + (int)getLocationY() - getSize()/2, this.getSize(), this.getSize(), 0, 360);

        g.setColor(ColorUtil.BLACK);
        g.drawLine(linex, liney, linex + (int)(Math.cos(Math.toRadians(90-getHeading())) * getSize()/2), liney + (int)(Math.sin(Math.toRadians(90-getHeading())) * getSize()/2));
    }

    @Override
    /**
     * Similar to Player helicopter collision
     * @param damage       the added damage. Helps determine setColor
     */
    public void collision(int damage)
    {
        damageLevel += damage;
        System.out.println("This NonPlayerHelicopter took " + damage + " damage!");

        if(damageLevel > 0 && damageLevel < 30)
            setColor(ColorUtil.rgb(115, 50, 135));
        else if(damageLevel >= 30 && damageLevel < 60)
            setColor(ColorUtil.rgb(183, 50, 135));
        else if(damageLevel >= 60 && damageLevel < 99)
            setColor(ColorUtil.rgb(240, 50, 135));
        else
            setColor(ColorUtil.rgb(77, 0, 0));
    }

    /**
     * @return The name of the strategy the NPH is using
     */
    public String getStrategy()
    {
        if(strat instanceof WinStrategy)
            return "Win";
        else if(strat instanceof AttackStrategy)
            return "Attack";
        else
            return "ERRORNoStratSelected";
    }

    /**
     * Changes the strategy of the NPH
     * @param strat     the new NPH strategy
     */
    public void setStrategy(IStrategy strat)
    {
        this.strat = strat;
    }

    /**
     * Executes the current strategy
     * @param gw        An instance of GameWorld to help out our strategies
     */
    public void invokeStrategy(GameWorld gw)
    {
        strat.executeStrategy(gw, this);
    }

    @Override
    public String toString()
    {
        String parentDesc = super.toString();
        String thisString = "| NPH: StickAngle = " + this.stickAngle + ", DamageLevel = " + this.damageLevel + ", lastSkyScraperReached = " + this.lastSkyScraperReached +
                ", Strategy = " + this.strat;
        return parentDesc + thisString;
    }
}
