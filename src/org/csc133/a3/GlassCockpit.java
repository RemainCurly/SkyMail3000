package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.mig.Grid;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.table.TableLayout;

import javax.swing.*;

public class GlassCockpit extends Container
{
    private int digitColor;
    private GameClockComponent gc;

    /**
     * Sets our labels, as well as our active Game components. The components will behave based off
     * information from our given instance of GameWorld.
     * @param gw Our instance of GameWorld
     */
    public GlassCockpit(GameWorld gw)
    {
        digitColor = ColorUtil.rgb(254, 206, 38);
        setLayout(new TableLayout(2,1));

        Container labels = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container comps = new Container(new BoxLayout(BoxLayout.X_AXIS_NO_GROW));
        getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.CYAN));
        getAllStyles().setBgTransparency(255);
        getAllStyles().setBgColor(ColorUtil.GRAY);

        Label l = new Label("Time         Fuel        Dmg  Lives  Last   Head");
        l.getAllStyles().setFgColor(digitColor);
        l.setAutoSizeMode(true);
        l.setMaxAutoSize(3);
        labels.add(l);

        gc = new GameClockComponent();
        FuelComponent fc = new FuelComponent(gw);
        DamageComponent dc = new DamageComponent(gw);
        LivesComponent lc = new LivesComponent(gw);
        LastSSReachedComponent lsc = new LastSSReachedComponent(gw);
        HeadingComponent hc = new HeadingComponent(gw);
        comps.add(gc); comps.add(fc); comps.add(dc); comps.add(lc); comps.add(lsc); comps.add(hc);

        add(labels); add(comps);
    }
}
