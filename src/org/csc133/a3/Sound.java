package org.csc133.a3;

import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

/**
 * =====|Sound credits|======================================================================================
 * Background music: https://soundimage.org/city-urban/
 * Crash.wav:        http://www.simphonics.com/library/WaveFiles/Production%20Wavefiles/FS-98/SNCRASH1.WAV
 * lostLife.wav:     http://ricostardeluxe.free.fr/biggun2.wav
 * refuel.wav:       http://www.oxeyegames.com/files/dm_examples/simplebreak/bounce.wav
 * ==========================================================================================================
 */
public class Sound
{
    private Media m;

    public Sound(String fileName)
    {
        while(m == null)
        {
            try {
                InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
                m = MediaManager.createMedia(is, "audio/wav");
            } catch (Exception e) {}
        }
    }

    public void play()
    {
        m.setVolume(20);
        m.setTime(0);
        m.play();
    }
}
