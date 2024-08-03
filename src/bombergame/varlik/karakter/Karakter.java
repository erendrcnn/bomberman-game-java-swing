package bombergame.varlik.karakter;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.medya.Ekran;
import bombergame.varlik.AnimasyonluNesne;

public abstract class Karakter extends AnimasyonluNesne {

    protected OyunTahtasi _oyunTahtasi;
    protected int _yon = -1;
    protected boolean _canli = true;
    protected boolean _hareketli = false;
    protected int _gecenZaman = 80;

    public Karakter(int x, int y, OyunTahtasi oyunTahtasi) {
        _x = x;
        _y = y;
        _oyunTahtasi = oyunTahtasi;
    }

    @Override
    public abstract void guncelle();

    @Override
    public abstract void olustur(Ekran screen);

    protected abstract void hareketHesapla();

    protected abstract void hareket(double xa, double ya);

    public abstract void oldur();

    protected abstract void olumSonrasi();

    protected abstract boolean hareketEdebilirMi(double x, double y);

    public boolean isCanli() {
        return _canli;
    }

    public boolean isHareketli() {
        return _hareketli;
    }

    public int getYon() {
        return _yon;
    }

    protected double getXMesaj() {
        return (_x * Oyun.OLCEK) + (_model.UZUNLUK / 2 * Oyun.OLCEK);
    }

    protected double getYMesaj() {
        return (_y * Oyun.OLCEK) - (_model.UZUNLUK / 2 * Oyun.OLCEK);
    }

}
