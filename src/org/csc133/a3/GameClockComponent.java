package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;
import java.io.IOException;
import java.util.Calendar;

public class GameClockComponent extends Component
{
    private Image[] digitImages = new Image[10];
    private Image[] dotDigitImages = new Image[10];
    private Image colonImage;

    private long startTime;
    private int ledColor;
    private long pauseFactor;
    private int subLedColor;
    private static int HM_COLON_IDX = 2;
    private static final int numDigitsShowing = 6;
    private Image[] clockDigits = new Image[numDigitsShowing];

    /**
     * Sets the beginning of our elapsed time, initializes the 7-segment digits,
     * shows all 0's at the beginning, and sets default LED color
     */
    public GameClockComponent()
    {
        startTime = System.currentTimeMillis();
        pauseFactor = 0;

        try
        {
            digitImages[0] = Image.createImage("/LED_digit_0.png");
            digitImages[1] = Image.createImage("/LED_digit_1.png");
            digitImages[2] = Image.createImage("/LED_digit_2.png");
            digitImages[3] = Image.createImage("/LED_digit_3.png");
            digitImages[4] = Image.createImage("/LED_digit_4.png");
            digitImages[5] = Image.createImage("/LED_digit_5.png");
            digitImages[6] = Image.createImage("/LED_digit_6.png");
            digitImages[7] = Image.createImage("/LED_digit_7.png");
            digitImages[8] = Image.createImage("/LED_digit_8.png");
            digitImages[9] = Image.createImage("/LED_digit_9.png");
            dotDigitImages[0] = Image.createImage("/LED_digit_0_with_dot.png");
            dotDigitImages[1] = Image.createImage("/LED_digit_1_with_dot.png");
            dotDigitImages[2] = Image.createImage("/LED_digit_2_with_dot.png");
            dotDigitImages[3] = Image.createImage("/LED_digit_3_with_dot.png");
            dotDigitImages[4] = Image.createImage("/LED_digit_4_with_dot.png");
            dotDigitImages[5] = Image.createImage("/LED_digit_5_with_dot.png");
            dotDigitImages[6] = Image.createImage("/LED_digit_6_with_dot.png");
            dotDigitImages[7] = Image.createImage("/LED_digit_7_with_dot.png");
            dotDigitImages[8] = Image.createImage("/LED_digit_8_with_dot.png");
            dotDigitImages[9] = Image.createImage("/LED_digit_9_with_dot.png");

            colonImage = Image.createImage("/LED_colon.png");
        } catch (IOException e){}

        for(int i = 0; i < numDigitsShowing; i++)
        {
            clockDigits[i] = digitImages[0];
        }

        clockDigits[HM_COLON_IDX] = colonImage;

        ledColor = ColorUtil.CYAN;
        subLedColor = ColorUtil.BLUE;
    }

    private long getElapsedTime()
    {
        return System.currentTimeMillis() - startTime ;
    }

    public void startElapsedTime()
    {
        startTime += pauseFactor;
        pauseFactor = 0;
        getElapsedTime();
    }

    public void stopElapsedTime()
    {
        pauseFactor = getElapsedTime();
    }

    /**
     * Visually displays elapsed time based off minute/second/tenth second conversions
     * @param m
     * @param s
     * @param ms
     */
    private void setTime(int m, int s, int ms)
    {
        clockDigits[0] = digitImages[m/10];
        clockDigits[1] = digitImages[m%10];
        clockDigits[3] = digitImages[s/10];
        clockDigits[4] = dotDigitImages[s%10];
        clockDigits[5] = digitImages[ms%10];
    }

    /**
     * Gets the milliseconds and does all the conversions to get seconds, minutes, and tenth seconds
     * If conversions are greater than 10 minutes, we go change the clock's colors
     */
    private void setCurrentTime()
    {
        long time = getElapsedTime();
        double seconds = ((double)time/1000);
        int minutes = (int)((time/60000)%100);
        double tenths = (seconds*10)%10;

        alterColors(minutes);
        setTime(minutes, (int)seconds%60, (int)tenths);
    }

    /**
     * If we're overtime, clock becomes red
     * Otherwise, clock is default colored
     * @param minutes
     */
    private void alterColors(int minutes)
    {
        if(minutes >= 10)
        {
            ledColor = ColorUtil.rgb(255,0,0);
            subLedColor = ColorUtil.rgb(86, 0, 0);
        }
        else
        {
            ledColor = ColorUtil.CYAN;
            subLedColor = ColorUtil.BLUE;
        }
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
        setCurrentTime();
        return true;
    }

    protected Dimension calcPreferredSize()
    {
        return new Dimension((colonImage.getWidth()*numDigitsShowing - digitImages[0].getWidth()*2)-40, colonImage.getHeight() - digitImages[0].getHeight()/10 + 11);
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = clockDigits[0].getWidth();
        int digitHeight = clockDigits[0].getHeight();
        int clockWidth = numDigitsShowing*digitWidth;

        float scaleFactor = Math.min(
                getInnerHeight()/(float)digitHeight,
                getInnerWidth()/(float)clockWidth);

        int displayDigitWidth = (int)(scaleFactor*digitWidth);
        int displayDigitHeight = (int)(scaleFactor*digitHeight);
        int displayClockWidth = displayDigitWidth*numDigitsShowing;


        int displayX = getX() + (getWidth()-displayClockWidth)/2;
        int displayY = getY() + (getHeight()-displayDigitHeight)/2;

        g.setColor(ColorUtil.BLACK);
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        g.setColor(ledColor);
        g.fillRect(displayX+COLOR_PAD,
                displayY+COLOR_PAD,
                displayClockWidth-COLOR_PAD*2,
                displayDigitHeight-COLOR_PAD*2);

        for(int digitIndex = 0; digitIndex < numDigitsShowing - 1; digitIndex++)
        {
            g.drawImage(clockDigits[digitIndex], displayX+digitIndex*displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);
        }

        //Here, we do the darker color for the tenth digit
        g.setColor(subLedColor);
        g.fillRect(displayX+(displayDigitWidth*5), displayY, displayDigitWidth, displayDigitHeight);
        g.drawImage(clockDigits[numDigitsShowing-1], displayX+5*displayDigitWidth, displayY, displayDigitWidth, displayDigitHeight);
    }
}
