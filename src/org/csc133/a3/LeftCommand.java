package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class LeftCommand extends Command
{
    private GameWorld g;

    public LeftCommand(GameWorld gw)
    {
        super("TurnL");
        g = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        g.turn('l');
        System.out.println("Left command fired!");
    }
}
