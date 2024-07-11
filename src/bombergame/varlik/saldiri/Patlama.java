package bombergame.varlik.saldiri;

import bombergame.OyunTahtasi;
import bombergame.medya.Ekran;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Karakter;


public class Patlama extends Nesne {

    protected boolean _sonuncu = false;
    protected OyunTahtasi _oyunTahtasi;

    public Patlama(int x, int y, int direction, boolean sonMu, OyunTahtasi oyunTahtasi) {
        _x = x;
        _y = y;
        _sonuncu = sonMu;
        _oyunTahtasi = oyunTahtasi;

        switch (direction) {
            case 0:
                if (!_sonuncu) {
                    _model = Model.patlama_orta_dikey;
                } else {
                    _model = Model.patlama_sag_dikey;
                }
                break;
            case 1:
                if (!_sonuncu) {
                    _model = Model.patlama_orta_yatay;
                } else {
                    _model = Model.patlama_sag_yatay;
                }
                break;
            case 2:
                if (!_sonuncu) {
                    _model = Model.patlama_orta_dikey;
                } else {
                    _model = Model.patlama_sol_dikey;
                }
                break;
            case 3:
                if (!_sonuncu) {
                    _model = Model.patlama_orta_yatay;
                } else {
                    _model = Model.patlama_sol_yatay;
                }
                break;
        }
    }

    @Override
    public void olustur(Ekran screen) {
        int xt = (int) _x << 4;
        int yt = (int) _y << 4;

        screen.nesneyiCiz(xt, yt, this);
    }

    @Override
    public void guncelle() {
    }

    @Override
    public boolean kesisme(Nesne e) {

        if (e instanceof Karakter) {
            ((Karakter) e).oldur();
        }

        return true;
    }


}