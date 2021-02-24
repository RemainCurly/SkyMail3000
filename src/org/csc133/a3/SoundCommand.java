package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SoundCommand extends Command
{
    private GameWorld g;

    public SoundCommand(GameWorld gw)
    {
        super("Toggle Sound");
        g = gw;
    }

    @Override
    /**
     * When fired, either turns sound off or on
     */
    public void actionPerformed(ActionEvent e)
    {
        g.toggleSound();
    }
}
