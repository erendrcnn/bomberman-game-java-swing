package bombergame.medya;

import java.net.URL;
import javax.sound.sampled.*;

public class Muzik {
    private Clip ses = null;
    private boolean kapali = false;
    private String dosyaYolu;

    public Muzik(String sesDosyasi) {
        dosyaYolu = sesDosyasi;
        AudioInputStream sesGirdi = null;

        try {
            URL url = getClass().getResource("/" + sesDosyasi);
            if (url != null) {
                sesGirdi = AudioSystem.getAudioInputStream(url);
            } else {
                System.err.println("Ses dosyasi bulunamadi: " + sesDosyasi);
            }
        } catch (Exception e) {
            System.err.println("Ses dosyasi okunurken hata: " + sesDosyasi);
            e.printStackTrace();
        }

        try {
            this.ses = AudioSystem.getClip();
            if (sesGirdi != null && this.ses != null) {
                this.ses.open(sesGirdi);
            }
        } catch (Exception e) {
            System.err.println("Ses sistemi baslatilamadi veya format desteklenmiyor: " + e.getMessage());
            this.ses = null;
        }
    }

    public void muzikCal(int _loop) {
        if (!this.kapali) {
            AudioInputStream sesGirdi = null;

            try {
                URL url = getClass().getResource("/" + dosyaYolu);
                if (url != null) {
                    sesGirdi = AudioSystem.getAudioInputStream(url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                this.ses = AudioSystem.getClip();
                if (sesGirdi != null && this.ses != null) {
                    this.ses.open(sesGirdi);
                    this.ses.start();
                    this.ses.loop(_loop);
                }
            } catch (Exception e) {
                System.err.println("Muzik calinirken hata: " + e.getMessage());
                this.ses = null;
            }
        }
    }

    public void muzikDurdur() {
        this.kapali = true;
        if (this.ses != null) {
            this.ses.stop();
        }
    }

    public void setKapali(boolean kapali) {
        this.kapali = kapali;
    }
}