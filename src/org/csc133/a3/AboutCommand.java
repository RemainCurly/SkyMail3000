package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

//Use of Command Design Pattern (seen in all ___Command.java files)
public class AboutCommand extends Command
{
    public AboutCommand()
    {
        super("About");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("About command fired!");
        Dialog.show("About", "Michael Burns. Csc 133. Version 2.0\nWorks best on iPhone X view!", new Command("Ok"));
    }
}
