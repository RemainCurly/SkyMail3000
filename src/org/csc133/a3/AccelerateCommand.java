package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class AccelerateCommand extends Command
{
    private GameWorld g;

    public AccelerateCommand(GameWorld gw)
    {
        super("Accelerate");
        g = gw;
    }

    @Override
    /**
     * When this command is fired, it will go into the GameWorld, find the Player helicopter, and increase its speed
     */
    public void actionPerformed(ActionEvent e)
    {
        g.accelerate(1);
        System.out.println("Accelerate command fired!");
    }
}
