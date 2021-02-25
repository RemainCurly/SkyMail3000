package org.csc133.a3;

import com.codename1.util.MathUtil;

public class WinStrategy implements IStrategy
{
    @Override
    /**
     * This strategy will cause all NPHs to reach the next Sequential skyscraper and remain there
     * @param gw      The GameWorld instance, so we know about Skyscraper locations
     * @param nph     The instance of the NPH we're executing the strategy of
     */
    public void executeStrategy(GameWorld gw, NonPlayerHelicopter nph)
    {
        double nphX = nph.getLocationX();
        double nphY = nph.getLocationY();
        double ssX = gw.getGoc().getSkyScraper(nph.getLastSkyScraperReached()).getLocationX();
        double ssY = gw.getGoc().getSkyScraper(nph.getLastSkyScraperReached()).getLocationY();
        double rangeX = ssX - nphX;
        double rangeY = ssY - nphY;
        double angle = 90 - Math.toDegrees(MathUtil.atan2(rangeY, rangeX));

        nph.setHeading((int)angle);
    }
}
