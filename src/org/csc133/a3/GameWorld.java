package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;

import java.sql.Ref;
import java.util.Vector;

public class GameWorld
{
    private int clock;
    private int numLives;
    private boolean reinit;
    private boolean soundOff;
    private GameObjectCollection goc;
    private final int NUM_SKYSCRAPERS;
    private int emptyBlimps;
    private BGSound bgs;
    private Sound crash;
    private Sound lostLife;
    private Sound refuel;

    /**
     * Initializes all our GameObjects, gets the amount of skyscrapers, and
     * starts us off with 3 lives
     */
    public GameWorld()
    {
        bgs = new BGSound("bgMusic.wav");
        goc = new GameObjectCollection(this);
        goc.getSkyScraper(0).setReached(true);
        NUM_SKYSCRAPERS = goc.getNumSkyscrapers();
        emptyBlimps = 0;
        numLives = 3;
        lostLife = new Sound("lostLife.wav");
        clock = 0;
        reinit = false;
        bgs.play();
        soundOff = false;
    }

    /**
     * The Player helicopter is reset after losing a life, and set back at the 1st skyscraper
     */
    public void reinit()
    {
        //Lostlife sound
        if(!soundOff)
            lostLife.play();

        Helicopter h = goc.getHeli();
        h.reset();

        h.setLocationX(goc.getSkyScraper(0).getLocationX());
        h.setLocationY(goc.getSkyScraper(0).getLocationY());

        setReinit(true);
    }

    /**
     * @return An instance of our GameObjects for retrieval in other classes
     */
    public GameObjectCollection getGoc()
    {
        return goc;
    }

    public boolean isGameOver()
    {
        return numLives <= 0;
    }

    //If the Helicopter is capable, increase its speed by the given amount
    //    Otherwise, tell User it's already at max speed
    public void accelerate(int amount)
    {
        Helicopter h = goc.getHeli();

        if(!h.outtaGas() && !h.isBroken() && (h.getSpeed() < h.getMaxSpeed()))
        {
            h.setSpeed(h.getSpeed() + amount);
            if(h.getSpeed() > h.getMaxSpeed())
                h.setSpeed(h.getMaxSpeed());
            System.out.println("Speed increased!");
        }
        else
            System.out.println("Already at max speed of " + h.getMaxSpeed() + "!");
    }

    //Decrease the Helicopter's speed by the given amount
    //    If stationary, tell User it's already at 0 speed
    public void brake(int amount)
    {
        Helicopter h = goc.getHeli();

        if(h.getSpeed() > 0)
        {
            h.setSpeed(h.getSpeed() - amount);
            System.out.println("Speed decreased!");
            if (h.getSpeed() < 0)
                h.setSpeed(0);
        }
        else
        {
            System.out.println("Already at 0 speed!");
        }
    }

    //Helicopter takes damage based off entity chosen
    public void collide(char entity)
    {
        Helicopter h = goc.getHeli();
        if(entity == 'h')
        {
            h.collision(20);

            //Helicopter crash sound
            if(crash == null)
                crash = new Sound("crash.wav");

            if(!soundOff)
                crash.play();

//            int rand = NonPlayerHelicopter.getInstance().getRand(0, goc.getNumNPHs()-1);
//            System.out.println("Player Heli has taken damage!");
//
//            goc.getNPH(rand).collision(30);
//            System.out.println("NPH at sequence " + rand + " has taken damage!");
        }
        else if(entity == 'b')
        {
            Bird b = goc.getBird(1);

            h.collision(5);
        }
        else
            System.out.println("ERROR: Not a damaging collision-based object");

        if(h.getSpeed() < 0)
            h.setSpeed(0);
        if(h.getMaxSpeed() < 0)
            h.setMaxSpeed(0);
        if(h.getSpeed() > h.getMaxSpeed())
            h.setSpeed(h.getMaxSpeed());

        //If maxDamage is reached, User loses a life and the game is reinitialized
        if(h.isBroken())
        {
            System.out.println("Max damage! Lost a life");
            numLives--;
            if (isGameOver())
                exit();
            reinit();
        }
    }

    /**
     * Refuels Player helicopter at first available refueling blimp. That blimp is emptied, darkened in color,
     * and a new one is created. Further calls of this method will refuel at the new blimp created last time
     */
    public void refuel()
    {
        Helicopter h = goc.getHeli();
        RefuelingBlimp rb = goc.getRefuelingBlimp(emptyBlimps);
        emptyBlimps++;

        //Refuel noise
        if(refuel == null)
            refuel = new Sound("refuel.wav");

        if(!soundOff)
            refuel.play();

        h.setFuelLevel(h.getFuelLevel() + rb.getCapacity());
//        rb.setCapacity(0);
//        rb.setColor(ColorUtil.rgb(19, 77, 0));

        System.out.println("Helicopter refueled at 1st available refuelingBlimp!");

        if(h.getFuelLevel() > h.getMaxFuel())
            h.setFuelLevel(h.getMaxFuel());

        goc.addNewBlimp();
    }

    //Increases game clock, tells all Movable objectCollection to move, and consumes Helicopter fuel
    public void tick(MapView mv)
    {
        Helicopter h = goc.getHeli();
        clock++;

        goc.moveMovables(mv);

        h.consumeFuel();

        //If fuel is 0, User loses a life and game is reinitialized
        if(h.outtaGas())
        {
            h.setSpeed(0);
            System.out.println("Out of gas! Lose a life");
            numLives--;
            reinit();
        }

        if(getGoc().isNPHVictory(NUM_SKYSCRAPERS))
        {
            System.out.println("NPH Victory!");
            exit();
        }

        if(isGameOver())
            exit();
    }

    /**
     * If the sound effects are on, turn them off. Otherwise, turn them on
     */
    public void toggleSound()
    {
        if(soundOff)
        {
            soundOff = false;
            bgs.play();
        }
        else
        {
            soundOff = true;
            bgs.pause();
        }
    }

    /**
     * Tells Game that the Player helicopter has travelled to a Skyscraper at a sequence
     * @param sequence The sequential number of the Skyscraper we're travelling to
     */
    public void travel(int sequence)
    {
        Helicopter h = goc.getHeli();
        SkyScraper s = goc.getSkyScraper(sequence - 1);

        if(sequence > NUM_SKYSCRAPERS)
            sequence = NUM_SKYSCRAPERS;

        if(s != null)
        {
            if((h.getLastSkyScraperReached() + 1) == sequence)
            {
                h.setLastSkyScraperReached(sequence);
                System.out.println("Helicopter has reached next sequential skyscraper! (" + sequence + ")");
                s.setReached(true);
                if(h.getLastSkyScraperReached() == NUM_SKYSCRAPERS)
                    exit();
            }
            else
                System.out.println("Helicopter has reached skyscraper at sequence " + sequence + ", but out of order");
        }
    }

    public void travelNPH(NonPlayerHelicopter nph, int sequence)
    {
        SkyScraper s = goc.getSkyScraper(sequence - 1);

        if(sequence > NUM_SKYSCRAPERS)
            sequence = NUM_SKYSCRAPERS;

        if(s != null)
        {
            if((nph.getLastSkyScraperReached() + 1) == sequence)
            {
                nph.setLastSkyScraperReached(sequence);
                System.out.println("NPH has reached next sequential skyscraper! (" + sequence + ")");
                s.setReached(true);
                if(nph.getLastSkyScraperReached() == NUM_SKYSCRAPERS)
                    exit();
            }
        }
    }
    //Turns Helicopter left or right, depending on direction provided
    public void turn(char direction)
    {
        Helicopter h = goc.getHeli();

        if(direction == 'l')
            h.tiltStickLeft(5);
        else if(direction == 'r')
            h.tiltStickRight(5);
        else
            System.out.println("ERROR: Not a left/right direction");
    }

    //If all SkyScrapers sequentially reached, good ending! Otherwise, bad ending
    public void exit()
    {
        if(goc.getHeli().getLastSkyScraperReached() == NUM_SKYSCRAPERS)
            System.out.println("Game over, you win! Total time: " + clock);
        else
            System.out.println("Game over, better luck next time!");

        System.exit(0);
    }

    //Getters/Setters
    public int getNumLives()
    {
        return numLives;
    }
    public boolean isReinit()
    {
        return reinit;
    }
    public void setReinit(boolean b)
    {
        this.reinit = b;
    }
}
