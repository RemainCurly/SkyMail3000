package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BirdCommand extends Command
{
    private GameWorld g;

    public BirdCommand(GameWorld gw)
    {
        super("Collide (Bird)");
        g = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        g.collide('b');
        System.out.println("Collide (Bird) has fired!");
    }
}
