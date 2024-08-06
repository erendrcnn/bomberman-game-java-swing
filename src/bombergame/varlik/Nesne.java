package bombergame.varlik;

import bombergame.SabitDegiskenler;
import bombergame.harita.Koordinat;
import bombergame.medya.Ekran;
import bombergame.medya.Guncelleme;
import bombergame.medya.Model;

public abstract class Nesne implements Guncelleme, SabitDegiskenler {

    protected double _x, _y;
    protected boolean _kaldirildi = false;
    protected Model _model;

    @Override
    public abstract void guncelle();

    @Override
    public abstract void olustur(Ekran screen);

    public void kaldir() {
        _kaldirildi = true;
    }

    public boolean kaldirildiMi() {
        return _kaldirildi;
    }

    public Model getModel() {
        return _model;
    }

    public abstract boolean kesisme(Nesne e);

    public double getX() {
        return _x;
    }

    public double getY() {
        return _y;
    }

    public int getXBoyut() {
        return Koordinat.pikseldenHucreye(_x + _model.UZUNLUK / 2); // UZUNLUK degerinin yarisi eklenerak tahmin edilen merkez bulunur.
    }

    public int getYBoyut() {
        return Koordinat.pikseldenHucreye(_y - _model.UZUNLUK / 2); // UZUNLUK degerinin yarisi cikarilarak tahmin edilen merkez bulunur.
    }
}
