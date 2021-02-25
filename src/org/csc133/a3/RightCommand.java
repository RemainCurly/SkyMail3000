package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class RightCommand extends Command
{
    private GameWorld g;

    public RightCommand(GameWorld gw)
    {
        super("TurnR");
        g = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        g.turn('r');
        System.out.println("Right command fired!");
    }
}
