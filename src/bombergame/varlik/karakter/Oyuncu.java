package bombergame.varlik.karakter;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.gui.Klavye;
import bombergame.harita.Koordinat;
import bombergame.medya.Ekran;
import bombergame.medya.Model;
import bombergame.varlik.KatmanliNesne;
import bombergame.varlik.Mesaj;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.dusman.Enemy;
import bombergame.varlik.nesne.ozellik.Ozellik;
import bombergame.varlik.saldiri.Bomba;
import bombergame.varlik.saldiri.PatlamaYayilim;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Oyuncu extends Karakter implements Runnable {
    private List<Bomba> _bombalar;
    protected Klavye _girdi;
    protected int _timeBetweenPutBombs = 0;
    public static List<Ozellik> _ozellikler = new ArrayList<>();

    private Thread oyuncuThread;

    public Oyuncu(int x, int y, OyunTahtasi oyunTahtasi) {
        super(x, y, oyunTahtasi);
        _bombalar = _oyunTahtasi.getBombalar();
        _girdi = _oyunTahtasi.getGirdi();
        _model = Model.oyuncu_sag;
        oyuncuThread = new Thread(this);
        oyuncuThread.start();
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
        if (oyuncuThread != null) {
            oyuncuThread.interrupt();
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Karakter Guncelleme ve Cizme
    |--------------------------------------------------------------------------
    */
    @Override
    public synchronized void guncelle() {
        bombaTemizlik();
        if (!_canli) {
            olumSonrasi();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--; // Zamanı çok büyümemesi için azalt

        animate();
        hareketHesapla();
        bombaTetikleyici();
    }

    @Override
    public void olustur(Ekran screen) {
        calculateXOffset();

        if (_canli)
            modelBelirle();
        else
            _model = Model.oyuncu_olum;

        screen.nesneyiCiz((int) _x, (int) _y - _model.UZUNLUK, this);
    }

    public void calculateXOffset() {
        int xScroll = Ekran.xKaydirmaHesapla(_oyunTahtasi, this);
        Ekran.kaydirmaAyarla(xScroll, 0);
    }

    /*
    |--------------------------------------------------------------------------
    | Bomba Yerlestirme
    |--------------------------------------------------------------------------
    */
    private void bombaTetikleyici() {
        if (_girdi.bomba && Oyun.getBombaCephane() > 0 && _timeBetweenPutBombs < 0) {
            int xt = Koordinat.pikseldenHuceye(_x + (double) _model.getSize() / 2);
            int yt = Koordinat.pikseldenHuceye((_y + _model.getSize() / 2) - _model.getSize()); // Oyuncunun yarı yüksekliğini çıkar

            bombaYerlestir(xt, yt);
            Oyun.addBombaCephane(-1);
            _timeBetweenPutBombs = 30;
        }
    }

    protected void bombaYerlestir(int x, int y) {
        Bomba b = new Bomba(x, y, _oyunTahtasi);
        Enemy.bombaSavun(x, y);
        _oyunTahtasi.addBomba(b);
        bombaYerlestirMuzik.muzikCal(0);
    }

    private void bombaTemizlik() {
        Iterator<Bomba> bs = _bombalar.iterator();
        while (bs.hasNext()) {
            Bomba b = bs.next();
            if (b.kaldirildiMi()) {
                bs.remove();
                Oyun.addBombaCephane(1);
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Karakter Olum
    |--------------------------------------------------------------------------
    */
    @Override
    public void oldur() {
        if (!_canli) return;

        _canli = false;
        olumMuzik.muzikCal(0);
        Mesaj msg = new Mesaj("Öldün...", getXMessage(), getYMessage(), 2, Color.white, 14);
        _oyunTahtasi.addMesaj(msg);
    }

    @Override
    protected void olumSonrasi() {
        if (_gecenZaman > 0) {
            _gecenZaman--;
        } else {
            if (_bombalar.size() == 0) {
                _oyunTahtasi.oyunBitir();
            }
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Karakter Hareketi
    |--------------------------------------------------------------------------
    */
    @Override
    protected void hareketHesapla() {
        int xa = 0, ya = 0;
        if (_girdi.yukariHareket) ya--;
        if (_girdi.asagiHareket) ya++;
        if (_girdi.solHareket) xa--;
        if (_girdi.sagHareket) xa++;
        if (xa != 0 || ya != 0) {
            hareket(xa * Oyun.getOyuncuHiz(), ya * Oyun.getOyuncuHiz());
            _hareketli = true;
        } else {
            _hareketli = false;
        }
    }

    @Override
    public boolean hareketEdebilirMi(double x, double y) {
        for (int c = 0; c < 4; c++) {
            double xt = ((_x + x) + c % 2 * 13) / Oyun.KARE_BOYUT;
            double yt = ((_y + y) + c / 2 * 12 - 13) / Oyun.KARE_BOYUT;

            Nesne a = _oyunTahtasi.getVarlik(xt, yt, this);
            if (!a.kesisme(this)) {
                if (!Oyun.getAtlama()) {
                    takilmaKontrol(a);
                }
                return a instanceof KatmanliNesne && Oyun.getAtlama();
            }
        }
        return true;
    }

    private void takilmaKontrol(Nesne a) {
        if (_girdi.sagHareket || _girdi.solHareket) {
            if ((a.getY() * Oyun.KARE_BOYUT + 14 > _y && a.getY() * Oyun.KARE_BOYUT < _y)) {
                _y -= 0.1;
            }
            if (((a.getY() + 1) * Oyun.KARE_BOYUT + 15 > _y && (a.getY() + 1) * Oyun.KARE_BOYUT < _y)) {
                _y += 0.1;
            }
        }

        if (_girdi.yukariHareket || _girdi.asagiHareket) {
            if ((a.getX() * Oyun.KARE_BOYUT + 16 > _x && a.getX() * Oyun.KARE_BOYUT + 6 < _x)) {
                _x += 0.1;
            }
            if (((a.getX() - 1) * Oyun.KARE_BOYUT + 15 > _x && (a.getX() - 1) * Oyun.KARE_BOYUT < _x)) {
                _x -= 0.1;
            }
        }
    }

    @Override
    public void hareket(double xa, double ya) {
        if (xa > 0) _yon = 1;
        if (xa < 0) _yon = 3;
        if (ya > 0) _yon = 2;
        if (ya < 0) _yon = 0;

        if (hareketEdebilirMi(0, ya)) {
            _y += ya;
        }

        if (hareketEdebilirMi(xa, 0)) {
            _x += xa;
        }
    }

    @Override
    public boolean kesisme(Nesne e) {
        if (e instanceof PatlamaYayilim) {
            oldur();
            return false;
        }

        if (e instanceof Enemy) {
            oldur();
            return true;
        }

        return true;
    }

    /*
    |--------------------------------------------------------------------------
    | Oyuncu Ozellikleri
    |--------------------------------------------------------------------------
    */
    public void ozellikEkle(Ozellik p) {
        if (p.kaldirildiMi()) return;

        _ozellikler.add(p);
        esyaAlmaMuzik.muzikCal(0);
        p.setDeger();
    }

    /*
    |--------------------------------------------------------------------------
    | Oyuncu Modeli
    |--------------------------------------------------------------------------
    */
    private void modelBelirle() {
        switch (_yon) {
            case 0:
                _model = Model.oyuncu_yukari;
                if (_hareketli) {
                    _model = Model.movingSprite(Model.oyuncu_yukari_1, Model.oyuncu_yukari_2, _animasyon, 20);
                }
                break;
            case 1:
                _model = Model.oyuncu_sag;
                if (_hareketli) {
                    _model = Model.movingSprite(Model.oyuncu_sag_1, Model.oyuncu_sag_2, _animasyon, 20);
                }
                break;
            case 2:
                _model = Model.oyuncu_asagi;
                if (_hareketli) {
                    _model = Model.movingSprite(Model.oyuncu_asagi_1, Model.oyuncu_asagi_2, _animasyon, 20);
                }
                break;
            case 3:
                _model = Model.oyuncu_sol;
                if (_hareketli) {
                    _model = Model.movingSprite(Model.oyuncu_sol_1, Model.oyuncu_sol_2, _animasyon, 20);
                }
                break;
            default:
                _model = Model.oyuncu_sag;
                if (_hareketli) {
                    _model = Model.movingSprite(Model.oyuncu_sag_1, Model.oyuncu_sag_2, _animasyon, 20);
                }
                break;
        }
    }
}
