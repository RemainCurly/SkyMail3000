package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class NPHCommand extends Command
{
    private GameWorld g;

    public NPHCommand(GameWorld gw)
    {
        super("Collide (NPH)");
        g = gw;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        g.collide('h');
        System.out.println("Collide (NPH) fired!");
    }
}
