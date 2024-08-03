package bombergame.medya;

import bombergame.SabitDegiskenler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ModelYapisi implements SabitDegiskenler {
    public int[] pikseller;
    public int UZUNLUK;
    private String dosyaYolu;

    public ModelYapisi(String dosyaYolu, int uzunluk) {
        this.dosyaYolu = dosyaYolu;
        UZUNLUK = uzunluk;
        pikseller = new int[UZUNLUK * UZUNLUK];
        load();
    }

    private void load() {
        try {
            URL url = ModelYapisi.class.getResource(dosyaYolu);
            assert url != null;
            BufferedImage image = ImageIO.read(url);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pikseller, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void modifySpriteSheet(String p, int s) {
        this.dosyaYolu = p;
        this.UZUNLUK = s;
        pikseller = new int[UZUNLUK * UZUNLUK];
        load();
    }

    public String getDosyaYolu() {
        return dosyaYolu;
    }

    public int getUZUNLUK() {
        return UZUNLUK;
    }
}
