package bombergame.medya;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.SabitDegiskenler;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Oyuncu;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Ekran implements SabitDegiskenler {
    protected int _genislik, _yukseklik;
    public int[] _pikseller;
    private final int _transparanRenk = 0xffff00ff; //pink with alpha channel (ff in the begining)
    private Font yaziTipi;

    private BufferedImage ayarlar = null;
    private Image ayarlarSabitleme = null;

    private BufferedImage arkaPlan = null;
    private Image arkaPlanSabitleme = null;

    private BufferedImage yeniOyunGorseli = null;
    private Image yeniOyunGorseliSabitleme = null;

    public static int xKaydirma = 0, yKaydirma = 0;

    public Ekran(int width, int height) {
        _genislik = width;
        _yukseklik = height;

        _pikseller = new int[width * height];

        try {
            ayarlar = ImageIO.read(new File("res/model/ayarlar-tablo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            arkaPlan = ImageIO.read(new File("res/model/anamenu.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        arkaPlanSabitleme = arkaPlan.getScaledInstance(Oyun.GENISLIK * Oyun.SCALE, Oyun.YUKSEKLIK * Oyun.SCALE, Image.SCALE_DEFAULT);

        try {
            yeniOyunGorseli = ImageIO.read(new File("res/model/yeni-oyun.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void temizle() {
        Arrays.fill(_pikseller, 0);
    }

    public void nesneyiCiz(int xp, int yp, Nesne nesne) { //save entity pixels
        xp -= xKaydirma;
        yp -= yKaydirma;
        for (int y = 0; y < nesne.getModel().getSize(); y++) {
            int ya = y + yp; //add offset
            for (int x = 0; x < nesne.getModel().getSize(); x++) {
                int xa = x + xp; //add offset
                if (xa < -nesne.getModel().getSize() || xa >= _genislik || ya < 0 || ya >= _yukseklik)
                    break; //fix black margins
                if (xa < 0) xa = 0; //start at 0 from left
                int color = nesne.getModel().getPixel(x + y * nesne.getModel().getSize());
                if (color != _transparanRenk) _pikseller[xa + ya * _genislik] = color;
            }
        }
    }

    public void altModelVeNesneyiCiz(int xp, int yp, Nesne nesne, Model below) {
        xp -= xKaydirma;
        yp -= yKaydirma;
        for (int y = 0; y < nesne.getModel().getSize(); y++) {
            int ya = y + yp;
            for (int x = 0; x < nesne.getModel().getSize(); x++) {
                int xa = x + xp;
                if (xa < -nesne.getModel().getSize() || xa >= _genislik || ya < 0 || ya >= _yukseklik)
                    break; //fix black margins
                if (xa < 0) xa = 0;
                int color = nesne.getModel().getPixel(x + y * nesne.getModel().getSize());
                if (color != _transparanRenk)
                    _pikseller[xa + ya * _genislik] = color;
                else
                    _pikseller[xa + ya * _genislik] = below.getPixel(x + y * below.getSize());
            }
        }
    }

    public static void kaydirmaAyarla(int xO, int yO) {
        xKaydirma = xO;
        yKaydirma = yO;
    }

    public static int xKaydirmaHesapla(OyunTahtasi oyunTahtasi, Oyuncu oyuncu) {
        if (oyuncu == null) return 0;
        int temp = xKaydirma;

        double playerX = oyuncu.getX() / 16;
        double complement = 0.5;
        int firstBreakpoint = oyunTahtasi.getGenislik() / 4;
        int lastBreakpoint = oyunTahtasi.getGenislik() - firstBreakpoint;

        if (playerX > firstBreakpoint + complement && playerX < lastBreakpoint - complement) {
            temp = (int) oyuncu.getX() - (Oyun.GENISLIK / 2);
        }

        return temp;
    }

    /*
    |--------------------------------------------------------------------------
    | Game Screens
    |--------------------------------------------------------------------------
     */

    public void yaziTipiYukle() {
        try {
            File fontFile = new File("res/font/VBRUSHTB.ttf");
            yaziTipi = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.PLAIN, 60);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(yaziTipi);
        } catch (IOException | FontFormatException e) {
            //Handle exception
        }
    }

    public void oyunBittiCiz(Graphics g, int points, int highscore) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/model/skor-tablo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int targetWidth = image.getWidth() * Oyun.SCALE / 4;
        int targetHeight = image.getHeight() * Oyun.SCALE / 4;
        Image scoreTable = image.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        g.setFont(yaziTipi.deriveFont(Font.PLAIN, 12 * Oyun.SCALE));
        g.setColor(Color.white);
        ortaGorselCiz(scoreTable, targetWidth, targetHeight, getGercekGenislik(), getGercekYukseklik(), g);
        ortaMetinCiz("Oyun Bitti", getGercekGenislik(), getGercekYukseklik() - targetHeight + 140 / Oyun.SCALE, g);
        ortaMetinCiz("Skorun: " + points, getGercekGenislik(), getGercekYukseklik() - targetHeight + 700 / Oyun.SCALE, g);
        ortaMetinCiz("Rekor: " + highscore, getGercekGenislik(), getGercekYukseklik() - targetHeight + 1000 / Oyun.SCALE, g);
        ortaMetinCiz("Tekrar Dene", getGercekGenislik() + 10, getGercekYukseklik() - targetHeight + 1524 / Oyun.SCALE, g);
    }

    public void oyunYuklemeCiz(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getGercekGenislik(), getGercekYukseklik());

        g.setFont(yaziTipi);
        g.setColor(Color.white);
        ortaMetinCiz("YUKLENIYOR...", getGercekGenislik(), getGercekYukseklik(), g);
    }

    public void oyunDuraklamaCiz(Graphics g) {
        g.setFont(yaziTipi);
        g.setColor(Color.white);
        ortaMetinCiz("DURAKLATILDI", getGercekGenislik(), getGercekYukseklik(), g);

    }

    public void menuCiz(Graphics g) {
        g.drawImage(arkaPlanSabitleme, 0, 0, null);
    }

    public void ayarlarCiz(Graphics g) {
        g.setFont(yaziTipi.deriveFont(Font.PLAIN, 12 * Oyun.SCALE));
        g.setColor(Color.white);

        int hedefGenislik = ayarlar.getWidth() * Oyun.SCALE / 4;
        int hedefYukseklik = ayarlar.getHeight() * Oyun.SCALE / 4;
        ayarlarSabitleme = ayarlar.getScaledInstance(hedefGenislik, hedefYukseklik, Image.SCALE_DEFAULT);
        ortaGorselCiz(ayarlarSabitleme, hedefGenislik, hedefYukseklik, getGercekGenislik(), getGercekYukseklik(), g);

        switch (OyunTahtasi.seciliOzellik) {
            case 'B': // OzellikBomba
                g.drawString("Sari", Oyun.GENISLIK + 140, Oyun.YUKSEKLIK + 65);
                break;
            case 'M': // OzellikMenzil
                g.drawString("Yesil", Oyun.GENISLIK + 140, Oyun.YUKSEKLIK + 65);
                break;
            case 'A': // OzellikAtlama
                g.drawString("Mavi", Oyun.GENISLIK + 140, Oyun.YUKSEKLIK + 65);
                break;
            case 'H': // OzellikHiz
                g.drawString("Turuncu", Oyun.GENISLIK + 140, Oyun.YUKSEKLIK + 65);
                break;
            case 'K': // OzellikKumanda
                g.drawString("Turkuaz", Oyun.GENISLIK + 140, Oyun.YUKSEKLIK + 65);
                break;
        }
    }

    public void yeniOyunCiz(Graphics g) {
        int targetWidth = yeniOyunGorseli.getWidth() * Oyun.SCALE / 4;
        int targetHeight = yeniOyunGorseli.getHeight() * Oyun.SCALE / 4;
        yeniOyunGorseliSabitleme = yeniOyunGorseli.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        ortaGorselCiz(yeniOyunGorseliSabitleme, targetWidth, targetHeight, getGercekGenislik(), getGercekYukseklik(), g);
    }

    public void ortaMetinCiz(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }

    public void ortaGorselCiz(Image image, int imageWidth, int imageHeight,
                              int gameWidth, int gameHeight, Graphics g) {
        int x = (gameWidth - imageWidth) / 2;
        int y = (gameHeight - imageHeight) / 2;
        g.drawImage(image, x, y, null);
    }

    public int getGenislik() {
        return _genislik;
    }

    public int getYukseklik() {
        return _yukseklik;
    }

    public int getGercekGenislik() {
        return _genislik * Oyun.SCALE;
    }

    public int getGercekYukseklik() {
        return _yukseklik * Oyun.SCALE;
    }
}