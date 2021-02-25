package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;

public class LivesComponent extends Component
{
    private GameWorld g;
    private Image[] digits = new Image[10];
    private static final int numDigitsShowing = 1;
    private Image[] show = new Image[numDigitsShowing];
    private int ledColor;

    /**
     * Gets the GameWorld, initializes the 7-segment displays, begins by showing 0, and sets default LED color
     * @param gw     Instance of GameWorld
     */
    public LivesComponent(GameWorld gw)
    {
        g = gw;

        try
        {
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
            show[i] = digits[0];

        ledColor = ColorUtil.CYAN;
    }

    /**
     * Visually shows us how many lives the Player helicopter has
     */
    private void setLives()
    {
        int lives = g.getNumLives();

        show[0] = digits[lives%10];
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
        setLives();
        return true;
    }

    protected Dimension calcPreferredSize()
    {
        return new Dimension(digits[0].getWidth()*numDigitsShowing*2, digits[0].getHeight());
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = digits[0].getWidth();
        int digitHeight = digits[0].getHeight();
        int gaugeWidth = numDigitsShowing*digitWidth;

        float scaleFactor = Math.min(
                getInnerHeight()/(float)digitHeight,
                getInnerWidth()/(float)gaugeWidth);

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
