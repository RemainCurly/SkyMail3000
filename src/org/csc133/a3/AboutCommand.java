package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command
{
    public AboutCommand()
    {
        super("About");
    }

    @Override
    /**
     * When this command is fired, it will display a Dialog box with the required information
     */
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("About command fired!");
        Dialog.show("About", "Michael Burns. Csc 133. Version 2.0\nWorks best on iPhone X view!", new Command("Ok"));
    }
}
