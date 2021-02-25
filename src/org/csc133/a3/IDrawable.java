package org.csc133.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import java.awt.*;

public interface IDrawable
{
    /**
     * Drawable objects will all need the method that lets them draw
     * @param g               The Graphics object
     * @param containerOrigin Gets our origin based off how our home component is set
     */
    public void draw(Graphics g, Point containerOrigin);
}
