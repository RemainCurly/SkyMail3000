package org.csc133.a3;

import com.codename1.util.MathUtil;

//Use of Strategy Design Pattern
public class AttackStrategy implements IStrategy
{
    @Override
    /**
     * This strategy makes the NPHs contstantly target/pursue the Player helicopter
     */
    public void executeStrategy(GameWorld gw, NonPlayerHelicopter nph)
    {
        double nphX = nph.getLocationX();
        double nphY = nph.getLocationY();
        double heliX = gw.getGoc().getHeli().getLocationX();
        double heliY = gw.getGoc().getHeli().getLocationY();
        double rangeX = heliX - nphX;
        double rangeY = heliY - nphY;
        double angle = 90 - Math.toDegrees(MathUtil.atan2(rangeY, rangeX));

        nph.setHeading((int)angle);
    }
}
