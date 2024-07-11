package bombergame.medya;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Muzik {
    private Clip ses = null;
    private boolean kapali = false;
    private String dosyaYolu;

    public Muzik(String soundFile) {
        dosyaYolu = soundFile;
        File f = new File("./" + soundFile);
        AudioInputStream audioIn = null;

        try {
            audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        } catch (IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        try {
            this.ses = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try {
            this.ses.open(audioIn);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void muzikCal(int _loop) {
        if (!this.kapali) {
            File f = new File("./" + dosyaYolu);
            AudioInputStream audioIn = null;

            try {
                audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
            } catch (IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }

            try {
                this.ses = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }

            try {
                this.ses.open(audioIn);
            } catch (IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

            this.ses.start();
            this.ses.loop(_loop);
        }
    }

    public void muzikDurdur() {
        this.kapali = true;
        this.ses.stop();
    }

    public void setKapali(boolean kapali) {
        this.kapali = kapali;
    }
}