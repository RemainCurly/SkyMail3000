package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RefuelCommand extends Command
{
    private GameWorld g;

    public RefuelCommand(GameWorld gw)
    {
        super("Collide (Blimp)");
        g = gw;
    }

    @Override
    /**
     * When fired, performs the refuel action in the GameWorld class
     */
    public void actionPerformed(ActionEvent e)
    {
        g.refuel();
        System.out.println("Collide (Blimp) has fired!");
    }
}
