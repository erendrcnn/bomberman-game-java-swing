package bombergame.varlik.saldiri;

import bombergame.OyunTahtasi;
import bombergame.medya.Ekran;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Karakter;

public class PatlamaYayilim extends Nesne {

    protected OyunTahtasi _oyunTahtasi;
    protected int _yon;
    private int _alan;
    protected int xMerkez, yMerkez;
    protected Patlama[] _alevler;

    public PatlamaYayilim(int x, int y, int direction, int alan, OyunTahtasi oyunTahtasi) {
        xMerkez = x;
        yMerkez = y;
        _x = x;
        _y = y;
        _yon = direction;
        _alan = alan;
        _oyunTahtasi = oyunTahtasi;

        _alevler = new Patlama[kisitliAlanHesapla()];
        _patlamaOlustur();
    }

    private void _patlamaOlustur() {
        boolean sonuncuMu;

        int x = (int) _x;
        int y = (int) _y;
        for (int i = 0; i < _alevler.length; i++) {
            sonuncuMu = i == _alevler.length - 1 ? true : false;

            switch (_yon) {
                case 0:
                    y--;
                    break;
                case 1:
                    x++;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    x--;
                    break;
            }
            _alevler[i] = new Patlama(x, y, _yon, sonuncuMu, _oyunTahtasi);
        }
    }

    private int kisitliAlanHesapla() {
        int radius = 0;
        int x = (int) _x;
        int y = (int) _y;
        while (radius < _alan) {
            if (_yon == 0) y--;
            if (_yon == 1) x++;
            if (_yon == 2) y++;
            if (_yon == 3) x--;

            Nesne a = _oyunTahtasi.getVarlik(x, y, null);

            if (a instanceof Karakter) ++radius;    // Patlama karakter ile karsilasirsa devam eder

            if (a.kesisme(this) == false)       // Patlama bir nesne ile karsilasirsa durur
                break;

            ++radius;
        }
        return radius;
    }

    public Patlama patlamaKonum(int x, int y) {
        for (int i = 0; i < _alevler.length; i++) {
            if (_alevler[i].getX() == x && _alevler[i].getY() == y)
                return _alevler[i];
        }
        return null;
    }

    @Override
    public void guncelle() {
    }

    @Override
    public void olustur(Ekran screen) {

        for (int i = 0; i < _alevler.length; i++) {
            _alevler[i].olustur(screen);
        }
    }

    @Override
    public boolean kesisme(Nesne e) {
        return true;
    }
}
