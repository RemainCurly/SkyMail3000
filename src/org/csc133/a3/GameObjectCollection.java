package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;

import java.util.Vector;

public class GameObjectCollection extends Vector<GameObject>
{
    /**
     * All GameObjects are stored into different vectors based off what kind of GameObject they are
     */
    private Vector<GameObject> birds;
    private Vector<GameObject> NPHs;
    private Vector<GameObject> skyScrapers;
    private Vector<GameObject> refuelBlimps;
    private Helicopter h;
    private GameWorld gw;
    private final int HELICOPTER_SIZE;

    /**
     * Initializes our vectors, creating and storing all the objects the Game needs
     * @param g
     */
    public GameObjectCollection(GameWorld g)
    {
        HELICOPTER_SIZE = 150;
        gw = g;
        birds = new Vector<GameObject>();
        NPHs = new Vector<GameObject>();
        skyScrapers = new Vector<GameObject>();
        refuelBlimps = new Vector<GameObject>();

        skyScrapers.add(new SkyScraper(1, HELICOPTER_SIZE, 500.0, 1500.0));
        skyScrapers.add(new SkyScraper(2, HELICOPTER_SIZE, 730.0, 1000.0));
        skyScrapers.add(new SkyScraper(3, HELICOPTER_SIZE, 100.0, 500.0));
        skyScrapers.add(new SkyScraper(4, HELICOPTER_SIZE, 450.0, 200.0));

        refuelBlimps.add(new RefuelingBlimp());
        refuelBlimps.add(new RefuelingBlimp());

        birds.add(new Bird(ColorUtil.rgb(255,153,51)));
        birds.add(new Bird(ColorUtil.rgb(255,153,51)));

        //Player helicopter is created and set to start at the 1st skyscraper
        h = Helicopter.getInstance();
        h.fetchGW(gw);
        h.setLocationX(skyScrapers.elementAt(0).getLocationX());
        h.setLocationY(skyScrapers.elementAt(0).getLocationY());

        NPHs.add(new NonPlayerHelicopter(h.getSize()));
        NPHs.add(new NonPlayerHelicopter(h.getSize()));
        NPHs.add(new NonPlayerHelicopter(h.getSize()));
        NonPlayerHelicopter nph = (NonPlayerHelicopter)NPHs.elementAt(0);
        nph.fetchGW(gw);
    }

    /**
     * Lines 59-109 all involve retrieving ceratin GameObjects or the quantity of them
     * @param sequence
     * @return
     */
    public SkyScraper getSkyScraper(int sequence)
    {
        return (SkyScraper)skyScrapers.elementAt(sequence);
    }
    public int getNumSkyscrapers()
    {
        return skyScrapers.size();
    }
    public Bird getBird(int sequence)
    {
        return (Bird)birds.elementAt(sequence);
    }
    public int getNumBirds()
    {
        return birds.size();
    }
    public RefuelingBlimp getRefuelingBlimp(int sequence)
    {
        if(sequence == -1)
        {
            for(int i = 0; i < getNumRefuelBlimps(); i++)
            {
                RefuelingBlimp b = (RefuelingBlimp)refuelBlimps.elementAt(i);
                if(b.getCapacity() != 0)
                    return b;
            }
        }

        return (RefuelingBlimp)refuelBlimps.elementAt(sequence);
    }
    public void addNewBlimp()
    {
        refuelBlimps.add(new RefuelingBlimp());
    }
    public int getNumRefuelBlimps()
    {
        return refuelBlimps.size();
    }
    public NonPlayerHelicopter getNPH(int sequence)
    {
        return (NonPlayerHelicopter)NPHs.elementAt(sequence);
    }
    public int getNumNPHs()
    {
        return NPHs.size();
    }
    public Helicopter getHeli()
    {
        return h;
    }

    /**
     * Here, we go through the Vectors of movable objects and tell them to move.
     * We also invoke strategies of the NPHs based off the current player-selected strategy
     * @param mv
     */
    public void moveMovables(MapView mv)
    {
        for(int i = 0; i < birds.size(); i++)
        {
            Bird b = (Bird)birds.elementAt(i);
            b.move(mv);
        }

        h.move(mv);
        for(int i = 0; i < birds.size(); i++)
        {
            if (h.collidesWith(birds.elementAt(i)))
                h.handleCollision(birds.elementAt(i));
        }
        for(int i = 0; i < getNumRefuelBlimps(); i++)
        {
            RefuelingBlimp rb = (RefuelingBlimp)refuelBlimps.elementAt(i);

            if(h.collidesWith(rb))
                h.handleCollision(rb);
        }

        for(int i = 0; i < getNumSkyscrapers(); i++)
        {
            SkyScraper s = (SkyScraper)skyScrapers.elementAt(i);

            if(h.collidesWith(s))
                h.handleCollision(s);
        }

        for(int i = 0; i < getNumNPHs(); i++)
        {
            NonPlayerHelicopter n = (NonPlayerHelicopter)NPHs.elementAt(i);
            n.move(mv);
            for(int k = 0; k < getNumRefuelBlimps(); k++)
            {
                RefuelingBlimp rb = (RefuelingBlimp)refuelBlimps.elementAt(k);
                if(n.collidesWith(rb))
                    rb.handleCollision(n);
            }
        }

        for (int i = 0; i < NPHs.size(); i++)
        {
            NonPlayerHelicopter n = (NonPlayerHelicopter) NPHs.elementAt(i);

            for(int k = 1; k < getNumSkyscrapers() -1; k++)
            {
                if (h.collidesWith(n))
                    h.handleCollision(n);

                if (n.collidesWith(skyScrapers.elementAt(k)))
                {
                    n.setLastSkyScraperReached(n.getLastSkyScraperReached() + 1);
                    isNPHVictory(getNumSkyscrapers());
                }

                n.invokeStrategy(gw);
            }
        }
    }

    /**
     * Determines if a NonPlayerHelicopter has beaten the Player in his objective
     * @param numSkyscrapers
     * @return true if an NPH has sequentially reached the last skyscraper. False otherwise
     */
    public boolean isNPHVictory(int numSkyscrapers)
    {
        for(int i = 0; i < NPHs.size(); i++)
        {
            NonPlayerHelicopter n = (NonPlayerHelicopter)NPHs.elementAt(i);
            if(n.wonGame(numSkyscrapers))
                return true;
        }

        return false;
    }
}
