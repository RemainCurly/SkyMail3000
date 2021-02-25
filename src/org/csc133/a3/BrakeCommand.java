package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class BrakeCommand extends Command
{
    private GameWorld g;

    public BrakeCommand(GameWorld gw)
    {
        super("Brake");
        g = gw;
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        g.brake(1);
        System.out.println("Brake command fired!");
    }
}
