package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand extends Command
{
    public HelpCommand()
    {
        super("Help");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Help command fired!");
        Dialog.show("Help", "Press 'a' to accelerate!\n" +
                "Press 'b' to brake!\n" +
                "Press 'l' to turn left!\n" +
                "Press 'r' to turn right!\n" +
                "Press 'n' to collide with a non-Player helicopter!\n" +
                "Press 's' to collide with a Skyscraper!\n" +
                "Press 'e' to visit a refueling blimp!\n" +
                "Press 'g' to collide with a goony bird!\n" +
                "Press 'x' to exit the game!", new Command("Got it!"));
    }
}
