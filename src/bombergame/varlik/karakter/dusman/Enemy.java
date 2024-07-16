package bombergame.varlik.karakter.dusman;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.harita.Koordinat;
import bombergame.medya.Ekran;
import bombergame.medya.Model;
import bombergame.varlik.Mesaj;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Karakter;
import bombergame.varlik.karakter.Oyuncu;
import bombergame.varlik.karakter.dusman.algo.Algoritma;
import bombergame.varlik.saldiri.PatlamaYayilim;

import java.awt.*;

public abstract class Enemy extends Karakter implements Runnable {
    protected int puanDegeri;
    protected double hizSabiti;
    protected Algoritma algoritma;

    // Hareket düzeltme için gerekli değişkenler
    protected final double MAX_ADIM;
    protected final double kalanSayi; // bölümün kalan kısmı
    protected double adimSayi;

    protected int finalAnaimasyon = 30;
    protected Model olumModeli;

    private Thread enemyThread;

    public Enemy(int x, int y, OyunTahtasi oyunTahtasi, Model dead, double hizSabiti, int puanDegeri) {
        super(x, y, oyunTahtasi);
        this.puanDegeri = puanDegeri;
        this.hizSabiti = hizSabiti;

        MAX_ADIM = Oyun.KARE_BOYUT / hizSabiti;
        kalanSayi = (MAX_ADIM - (int) MAX_ADIM) / MAX_ADIM;
        adimSayi = MAX_ADIM;

        _gecenZaman = 20;
        olumModeli = dead;

        enemyThread = new Thread(this);
        enemyThread.start();
    }

    @Override
    public void run() {
        while (_canli) {
            this.guncelle();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public synchronized void stop() {
        _canli = false;
        if (enemyThread != null) {
            enemyThread.interrupt();
        }
    }

    public static void bombaSavun(int x, int y) {
        haritaMatrix[y][x] = 0;
        // Yukarı
        if (y - 1 >= 0 && haritaMatrix[y - 1][x] == 1) {
            haritaMatrix[y - 1][x] = -1;
        }
        // Sağ
        if (x + 1 < HARITA_GENISLIK && haritaMatrix[y][x + 1] == 1) {
            haritaMatrix[y][x + 1] = -1;
        }
        // Aşağı
        if (y + 1 < HARITA_YUKSEKLIK && haritaMatrix[y + 1][x] == 1) {
            haritaMatrix[y + 1][x] = -1;
        }
        // Sol
        if (x - 1 >= 0 && haritaMatrix[y][x - 1] == 1) {
            haritaMatrix[y][x - 1] = -1;
        }
    }

    public static void bombaSavunSifirla(int x, int y) {
        haritaMatrix[y][x] = 1;
        // Yukarı
        if (y - 1 >= 0 && haritaMatrix[y - 1][x] == -1) {
            haritaMatrix[y - 1][x] = 1;
        }
        // Sağ
        if (x + 1 < HARITA_GENISLIK && haritaMatrix[y][x + 1] == -1) {
            haritaMatrix[y][x + 1] = 1;
        }
        // Aşağı
        if (y + 1 < HARITA_YUKSEKLIK && haritaMatrix[y + 1][x] == -1) {
            haritaMatrix[y + 1][x] = 1;
        }
        // Sol
        if (x - 1 >= 0 && haritaMatrix[y][x - 1] == -1) {
            haritaMatrix[y][x - 1] = 1;
        }
    }

    @Override
    public void guncelle() {
        animate();

        if (!_canli) {
            olumSonrasi();
            return;
        }

        hareketHesapla();
    }

    @Override
    public void olustur(Ekran screen) {
        if (_canli) {
            modelBelirle();
        } else {
            if (_gecenZaman > 0) {
                _model = olumModeli;
                _animasyon = 0;
            } else {
                _model = Model.movingSprite(Model.canavar_olum_1, Model.canavar_olum_2, Model.canavar_olum_3, _animasyon, 60);
            }
        }

        screen.nesneyiCiz((int) _x, (int) _y - _model.UZUNLUK, this);
    }

    @Override
    public synchronized void hareketHesapla() {
        int xa = 0, ya = 0;
        if (adimSayi <= 0) {
            _yon = algoritma.yonBelirle();
            adimSayi = MAX_ADIM;
        }

        switch (_yon) {
            case 0 -> ya--;
            case 2 -> ya++;
            case 3 -> xa--;
            case 1 -> xa++;
        }

        if (hareketEdebilirMi(xa, ya)) {
            adimSayi -= 1 + kalanSayi;
            hareket(xa * hizSabiti, ya * hizSabiti);
            _hareketli = true;
        } else {
            adimSayi = 0;
            _hareketli = false;
        }
    }

    @Override
    public void hareket(double xa, double ya) {
        if (!_canli) {
            return;
        }
        _y += ya;
        _x += xa;
    }

    @Override
    public boolean hareketEdebilirMi(double x, double y) {
        double xr = _x, yr = _y - 16; // Daha doğru sonuçlar için y'yi çıkar

        // Hareket yönüne göre kontrol noktalarını belirle
        switch (_yon) {
            case 0 -> {
                yr += _model.getSize() - 1;
                xr += _model.getSize() / 2;
            }
            case 1 -> {
                yr += _model.getSize() / 2;
                xr += 1;
            }
            case 2 -> {
                xr += _model.getSize() / 2;
                yr += 1;
            }
            case 3 -> {
                xr += _model.getSize() - 1;
                yr += _model.getSize() / 2;
            }
        }

        int xx = Koordinat.pikseldenHuceye(xr) + (int) x;
        int yy = Koordinat.pikseldenHuceye(yr) + (int) y;

        Nesne a = _oyunTahtasi.getVarlik(xx, yy, this); // Hareket etmek istediğimiz konumdaki nesne

        if (a == null) {
            return false;
        }

        return a.kesisme(this);
    }

    @Override
    public boolean kesisme(Nesne e) {
        if (e instanceof PatlamaYayilim) {
            oldur();
            return false;
        }

        if (e instanceof Oyuncu) {
            ((Oyuncu) e).oldur();
            return false;
        }

        return true;
    }

    @Override
    public void oldur() {
        if (!_canli) {
            return;
        }
        _canli = false;
        _oyunTahtasi.addPuanlar(puanDegeri);
        Mesaj msg = new Mesaj("+" + puanDegeri, getXMessage(), getYMessage(), 2, Color.white, 14);
        _oyunTahtasi.addMesaj(msg);
    }

    @Override
    protected void olumSonrasi() {
        if (_gecenZaman > 0) {
            _gecenZaman--;
        } else {
            if (finalAnaimasyon > 0) {
                --finalAnaimasyon;
            } else {
                kaldir();
            }
        }
    }

    // Alt sınıflarda belirli düşman modelini belirlemek için kullanılacak
    protected abstract void modelBelirle();
}
