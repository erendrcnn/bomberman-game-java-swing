package bombergame.varlik.saldiri;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.harita.Koordinat;
import bombergame.medya.Ekran;
import bombergame.medya.Model;
import bombergame.varlik.AnimasyonluNesne;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Karakter;
import bombergame.varlik.karakter.Oyuncu;
import bombergame.varlik.karakter.dusman.Canavar;

public class Bomba extends AnimasyonluNesne {

    protected double _patlamaSuresi = 120;
    protected int _kaldirilmaSuresi = 20;
    protected OyunTahtasi _oyunTahtasi;
    protected boolean _uzerindenAtlanabilir = true;
    protected PatlamaYayilim[] _alevler = null;
    protected boolean _patladi = false;

    public Bomba(int x, int y, OyunTahtasi oyunTahtasi) {
        _x = x;
        _y = y;
        _oyunTahtasi = oyunTahtasi;
        _model = Model.bomba_sekil1;
    }

    @Override
    public void guncelle() {
        if (_patlamaSuresi > 0)
            _patlamaSuresi--;
        else {
            if (!_patladi)
                alevlendir();
            else
                alevGuncelle();

            if (_kaldirilmaSuresi > 0)
                _kaldirilmaSuresi--;
            else
                kaldir();
        }

        animate();
    }

    @Override
    public void olustur(Ekran screen) {
        if (_patladi) {
            _model = Model.patlama_merkez;
            alevOlustur(screen);
        } else
            _model = Model.movingSprite(Model.bomba_sekil1, Model.bomba_sekil2, Model.bomba_sekil3, _animasyon, 60);

        int xt = (int) _x << 4;
        int yt = (int) _y << 4;

        screen.nesneyiCiz(xt, yt, this);
    }

    public void alevOlustur(Ekran screen) {
        for (PatlamaYayilim patlama : _alevler) {
            patlama.olustur(screen);
        }
    }

    public void alevGuncelle() {
        for (PatlamaYayilim patlama : _alevler) {
            patlama.guncelle();
        }
    }

    public void patlat() {
        _patlamaSuresi = 0;
    }

    protected void alevlendir() {
        _uzerindenAtlanabilir = true;
        _patladi = true;

        // Bomba patladiginda bombanin oldugu hucredeki karakteri oldurur.
        Karakter a = _oyunTahtasi.getKarakterKonum(_x, _y);
        if (a != null) {
            a.oldur();
        }

        _alevler = new PatlamaYayilim[4];
        for (int i = 0; i < _alevler.length; i++) {
            _alevler[i] = new PatlamaYayilim((int) _x, (int) _y, i, Oyun.getBombaAlan(), _oyunTahtasi);
            Canavar.bombaSavunSifirla((int) _x, (int) _y);
        }
        bombaPatlamaMuzik.muzikCal(0);
    }

    public Patlama patlamaKonum(int x, int y) {
        if (!_patladi) return null;

        for (PatlamaYayilim patlama : _alevler) {
            if (patlama == null) return null;
            Patlama e = patlama.patlamaKonum(x, y);
            if (e != null) return e;
        }

        return null;
    }

    @Override
    public boolean kesisme(Nesne e) {

        if (e instanceof Oyuncu) {
            double xFark = e.getX() - Koordinat.hucredenPiksele(getX());
            double yFark = e.getY() - Koordinat.hucredenPiksele(getY());

            if (!(xFark >= -13 && xFark < 16 && yFark >= 1 && yFark <= 30)) {
                // System.out.println(e.getX() + " " + Koordinat.hucredenPiksele(getX()) + " " + xFark);
                // System.out.println(e.getY() + " " + Koordinat.hucredenPiksele(getY()) + " " + yFark);
                _uzerindenAtlanabilir = false;
            }

            return _uzerindenAtlanabilir;
        }

        if (e instanceof PatlamaYayilim) {
            patlat();
            return true;
        }

        return false;
    }
}
