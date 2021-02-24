package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Border;

import java.io.IOException;
import java.io.InputStream;

public class SouthButtons extends Container
{
    /**
     * In the South part of our Game, we create Arrow images and link commands to them so they work on click
     * @param gw The active GameWorld instance, since certain commands require them
     * @throws IOException If somehow we mess up on retrieving the arrow images
     */
    public SouthButtons(GameWorld gw) throws IOException
    {
        setLayout(new GridLayout(1,4));
        getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.CYAN));
        getAllStyles().setBgTransparency(255);
        getAllStyles().setBgColor(ColorUtil.GRAY);

        InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/arrow_down.png");
        Image down = Image.createImage(is);
        is = Display.getInstance().getResourceAsStream(getClass(), "/arrow_left.png");
        Image left = Image.createImage(is);
        is = Display.getInstance().getResourceAsStream(getClass(), "/arrow_right.png");
        Image right = Image.createImage(is);
        is = Display.getInstance().getResourceAsStream(getClass(), "/arrow_up.png");
        Image up = Image.createImage(is);

        Button bdown = new Button(down);
        Button bleft = new Button(left);
        Button bright = new Button(right);
        Button bup = new Button(up);

        bdown.addActionListener(new BrakeCommand(gw));
        bleft.addActionListener(new LeftCommand(gw));
        bright.addActionListener(new RightCommand(gw));
        bup.addActionListener(new AccelerateCommand(gw));

        add(bleft);
        add(bup);
        add(bdown);
        add(bright);
    }
}
