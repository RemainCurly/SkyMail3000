package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command
{
    public ExitCommand()
    {
        super("Exit");
    }

    @Override
    /**
     * When fired, will prompt user for exit
     * On yes: Exits program
     * On no: Leaves dialog and does nothing
     */
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Exit command fired!");
        boolean wantsExit = Dialog.show("Confirm Quit", "Are you sure you want to quit the game?", "Yes", "No");
        if(wantsExit)
            Display.getInstance().exitApplication();
    }
}
