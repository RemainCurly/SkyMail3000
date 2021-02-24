package org.csc133.a3;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.UITimer;

import javax.swing.border.Border;
import java.io.IOException;
import java.lang.String;

public class Game extends Form implements Runnable
{
    private GameWorld gw;
    private MapView mv;
    private GlassCockpit gc;
    private UITimer timer;
    private long timerMillis;

    /**
     * Maps out the whole form. Creates instance of GameWorld, Glasscockpit, MapView, and SouthButtons
     * Commands are created here, as well as key listeners
     */
    public Game()
    {
        gw = new GameWorld();
        gc = new GlassCockpit(gw);
        mv = new MapView(gw);

        timerMillis = 20;
        timer = new UITimer(this);
        timer.schedule((int)timerMillis, true, this);

        setLayout(new BorderLayout());
        Label space = new Label(" ");

        //Commands
        AccelerateCommand ac = new AccelerateCommand(gw);
        BrakeCommand bc = new BrakeCommand(gw);
        LeftCommand lc = new LeftCommand(gw);
        RightCommand rc = new RightCommand(gw);
        NPHCommand nc = new NPHCommand(gw);
        SkyscraperCommand sc = new SkyscraperCommand(gw);
        RefuelCommand rec = new RefuelCommand(gw);
        //BirdCommand bic = new BirdCommand(gw);
        ExitCommand ec = new ExitCommand();
        SoundCommand sndc = new SoundCommand(gw);
        StrategyCommand stc = new StrategyCommand(gw);
        AboutCommand abc = new AboutCommand();
        HelpCommand hc = new HelpCommand();

        //Toolbar
        Toolbar t = new Toolbar();
        setToolbar(t);
        setTitle("SkyMail 3000!");
        t.setOnTopSideMenu(true);
        t.addComponentToLeftSideMenu(space);
        t.addCommandToSideMenu(stc);
        t.addCommandToSideMenu(sndc);
        t.addCommandToSideMenu(abc);
        t.addCommandToSideMenu(hc);
        t.addCommandToSideMenu(ec);

        //Key Listeners
        addKeyListener('a', ac);
        addKeyListener('b', bc);
        addKeyListener('l', lc);
        addKeyListener('r', rc);
        addKeyListener('n', nc);
        //addKeyListener('s', sc);
        addKeyListener('e', rec);
        //addKeyListener('g', bic);
        addKeyListener('x', ec);

        add(BorderLayout.NORTH, gc);
        add(BorderLayout.CENTER, mv);
        try {
            add(BorderLayout.SOUTH, new SouthButtons(gw));
        } catch (IOException e){}

        show();
    }

    @Override
    /**
     * Implementing runnable, the UITimer will constantly call run(), which moves the game along
     */
    public void run()
    {
        gw.tick(mv);
    }
}
