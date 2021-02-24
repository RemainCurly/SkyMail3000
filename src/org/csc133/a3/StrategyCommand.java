package org.csc133.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class StrategyCommand extends Command
{
    private GameWorld g;

    public StrategyCommand(GameWorld gw)
    {
        super("Change Strategies");
        g = gw;
    }

    @Override
    /**
     * When fired, swaps NPH commands. If they're all on "Win", they'll switch to "Attack", and vice versa
     */
    public void actionPerformed(ActionEvent e)
    {
        for(int i = 0; i < g.getGoc().getNumNPHs(); i++)
        {
            if (g.getGoc().getNPH(i).getStrategy() == "Win")
                g.getGoc().getNPH(i).setStrategy(new AttackStrategy());
            else if(g.getGoc().getNPH(i).getStrategy() == "Attack")
                g.getGoc().getNPH(i).setStrategy(new WinStrategy());
        }
    }
}
