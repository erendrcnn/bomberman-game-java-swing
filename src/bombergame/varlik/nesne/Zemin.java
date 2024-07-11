package bombergame.varlik.nesne;

import bombergame.harita.Koordinat;
import bombergame.medya.Ekran;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;

public abstract class Zemin extends Nesne {

    public Zemin(int x, int y, Model model) {
        _x = x;
        _y = y;
        _model = model;
    }

    @Override
    public boolean kesisme(Nesne e) {
        return false;
    }

    @Override
    public void olustur(Ekran screen) {
        screen.nesneyiCiz(Koordinat.hucredenPiksele(_x), Koordinat.hucredenPiksele(_y), this);
    }

    @Override
    public void guncelle() {
    }
}
