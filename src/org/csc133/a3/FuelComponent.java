package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;

public class FuelComponent extends Container
{
    private GameWorld g;
    private Image[] digits = new Image[10];
    private static final int numDigitsShowing = 4;
    private Image[] show = new Image[numDigitsShowing];
    private int ledColor;
    private final float scaleFactor;

    /**
     * Gets the GameWorld instance, initializes 7-segment digits,
     * shows all 0's, and sets default LED color
     * @param gw     Instance of GameWorld
     */
    public FuelComponent(GameWorld gw)
    {
        g = gw;
        scaleFactor = (float)0.7;

        try {
            digits[0] = Image.createImage("/LED_digit_0.png");
            digits[1] = Image.createImage("/LED_digit_1.png");
            digits[2] = Image.createImage("/LED_digit_2.png");
            digits[3] = Image.createImage("/LED_digit_3.png");
            digits[4] = Image.createImage("/LED_digit_4.png");
            digits[5] = Image.createImage("/LED_digit_5.png");
            digits[6] = Image.createImage("/LED_digit_6.png");
            digits[7] = Image.createImage("/LED_digit_7.png");
            digits[8] = Image.createImage("/LED_digit_8.png");
            digits[9] = Image.createImage("/LED_digit_9.png");
        } catch (IOException e){}

        for(int i = 0; i < numDigitsShowing; i++)
        {
            show[i] = digits[0];
        }

        ledColor = ColorUtil.GREEN;
    }

    /**
     * Visually shows the fuel level of the Player helicopter
     */
    private void setFuel()
    {
        int fuelLevel = g.getGoc().getHeli().getFuelLevel();

        show[0] = digits[0];
        show[1] = digits[fuelLevel/100];
        show[2] = digits[(fuelLevel/10)%10];
        show[3] = digits[fuelLevel%10];
    }

    public void start()
    {
        getComponentForm().registerAnimated(this);
    }

    public void stop()
    {
        getComponentForm().deregisterAnimated(this);
    }

    public void laidOut()
    {
        this.start();
    }

    public boolean animate()
    {
        setFuel();
        return true;
    }

    protected Dimension calcPreferredSize()
    {
        return new Dimension(digits[0].getWidth()*numDigitsShowing - 60, digits[0].getHeight() - 30);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = digits[0].getWidth();
        int digitHeight = digits[0].getHeight();

        int displayDigitWidth = (int)(scaleFactor*digitWidth);
        int displayDigitHeight = (int)(scaleFactor*digitHeight);
        int displayGaugeWidth = displayDigitWidth*numDigitsShowing;

        int displayX = getX() + (getWidth() - displayGaugeWidth)/2;
        int displayY = getY() + (getHeight() - displayDigitHeight)/2;

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.setColor(ledColor);
        g.fillRect(displayX + COLOR_PAD,
                displayY + COLOR_PAD,
                displayGaugeWidth - COLOR_PAD*2,
                displayDigitHeight-COLOR_PAD*2);

        for(int i = 0; i < numDigitsShowing; i++)
        {
            g.drawImage(show[i], displayX+i*displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);
        }
    }
}
