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
    public int _gecenZaman = 80;

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

    public boolean isAlive() {
        return _canli;
    }

    public boolean isMoving() {
        return _hareketli;
    }

    public int getDirection() {
        return _yon;
    }

    protected double getXMessage() {
        return (_x * Oyun.OLCEK) + (_model.UZUNLUK / 2 * Oyun.OLCEK);
    }

    protected double getYMessage() {
        return (_y * Oyun.OLCEK) - (_model.UZUNLUK / 2 * Oyun.OLCEK);
    }

}
