package bombergame.varlik;

import bombergame.medya.Ekran;

import java.awt.*;

public class Mesaj extends Nesne {

    protected String _metin;
    protected int _sure;
    protected Color _renk;
    protected int _boyut;

    public Mesaj(String msg, double x, double y, int sure, Color renk, int boyut) {
        _x = x;
        _y = y;
        _metin = msg;
        _sure = sure * 60; //seconds
        _renk = renk;
        _boyut = boyut;
    }

    public int getSure() {
        return _sure;
    }

    public void setSure(int _duration) {
        this._sure = _duration;
    }

    public String getMesaj() {
        return _metin;
    }

    public Color getRenk() {
        return _renk;
    }

    public int getBoyut() {
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
