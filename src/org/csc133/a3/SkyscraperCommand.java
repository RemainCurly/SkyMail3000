package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;

public class SkyscraperCommand extends Command
{
    private GameWorld g;

    public SkyscraperCommand(GameWorld gw)
    {
        super("Collide (Skyscraper)");
        g = gw;
    }

    @Override
    /**
     * When fired, asks Player what sequence they want to travel to. When they hit enter,
     * the Player helicopter will travel to that sequence (if it exists)
     */
    public void actionPerformed(ActionEvent e)
    {
        TextField tf = new TextField();
        int skyscraper;
        int limit = g.getGoc().getNumSkyscrapers();

        Dialog.show("Enter Skyscraper", tf, new Command("Okay"));

        if(!(tf.getText() == ""))
        {
            skyscraper = Integer.parseInt(tf.getText());
            if(skyscraper > limit || skyscraper < 1)
                Dialog.show("Error", "That sequence doesn't exist.", new Command("Okay"));
            else
                g.travel(skyscraper);
        }
        else
            Dialog.show("Error", "Nothing was entered", new Command("Okay"));

        System.out.println("Collide (Skyscraper) has fired!");
    }
}
