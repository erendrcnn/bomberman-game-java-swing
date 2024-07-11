package bombergame.varlik;

import bombergame.medya.Ekran;

import java.awt.*;

public class Mesaj extends Nesne {

    protected String _metin;
    protected int _sure;
    protected Color _renk;
    protected int _boyut;

    public Mesaj(String msg, double x, double y, int sure, Color color, int boyut) {
        _x = x;
        _y = y;
        _metin = msg;
        _sure = sure * 60; //seconds
        _renk = color;
        _boyut = boyut;
    }

    public int getDuration() {
        return _sure;
    }

    public void setDuration(int _duration) {
        this._sure = _duration;
    }

    public String getMessage() {
        return _metin;
    }

    public Color getColor() {
        return _renk;
    }

    public int getSize() {
        return _boyut;
    }

    @Override
    public void guncelle() {
    }

    @Override
    public void olustur(Ekran screen) {
    }

    @Override
    public boolean kesisme(Nesne e) {
        return true;
    }
}
