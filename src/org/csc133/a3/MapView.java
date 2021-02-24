package org.csc133.a3;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public class MapView extends Container
{
    private GameObjectCollection goc;
    private Point p;

    /**
     * Retrieves our GameObjects from GameObjectCollection
     * @param gw The active GameWorld, so we can get the GameObjectCollection
     */
    public MapView(GameWorld gw)
    {
        goc = gw.getGoc();
    }

    /**
     * Repaints the canvas as we animate
     */
    public void update()
    {
        repaint();
    }

    @Override
    public boolean animate()
    {
        return true;
    }

    @Override
    /**
     * Sets our Point, and then proceeds to draw every GameObject onto the canvas
     */
    public void paint(Graphics g)
    {
        super.paint(g);
        p = new Point(this.getX(), this.getY());

        for(int i = 0; i < goc.getNumSkyscrapers(); i++)
        {
            goc.getSkyScraper(i).draw(g, p);
        }

        for(int i = 0; i < goc.getNumRefuelBlimps(); i++)
        {
            goc.getRefuelingBlimp(i).draw(g, p);
        }

        for(int i = 0; i < goc.getNumBirds(); i++)
        {
            goc.getBird(i).draw(g, p);
        }

        for(int i = 0; i < goc.getNumNPHs(); i++)
        {
            goc.getNPH(i).draw(g, p);
        }

        goc.getHeli().draw(g, p);

        update();
    }
}
