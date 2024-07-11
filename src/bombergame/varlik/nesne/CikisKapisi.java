package bombergame.varlik.nesne;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.harita.Koordinat;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Oyuncu;
import bombergame.varlik.karakter.dusman.Sogan;
import bombergame.varlik.saldiri.PatlamaYayilim;

import java.util.Random;

public class CikisKapisi extends Zemin {

    protected OyunTahtasi _oyunTahtasi;

    public CikisKapisi(int x, int y, OyunTahtasi oyunTahtasi, Model model) {
        super(x, y, model);
        _oyunTahtasi = oyunTahtasi;
    }

    @Override
    public boolean kesisme(Nesne e) {
        // Eger patlama kapiya carparsa tetiklenir.
        // (Cikis kapisi koorinatlari etrafinda sag, sol, yukari ve asagi olmak uzere random 7 tane Oneal olusturulur.)
        if (e instanceof PatlamaYayilim) {
            Random r = new Random();
            for (int i = 0; i < 7; i++) {
                int offSetX, offSetY, newX, newY;

                do {
                    offSetX = r.nextInt(3) - 1;
                    offSetY = r.nextInt(3) - 1;
                    newX = (int) getX() + offSetX;
                    newY = (int) getY() + offSetY;
                } while (!(_oyunTahtasi.getVarlikKonum(newX, newY) instanceof Koridor));

                _oyunTahtasi.addKarakter(new Sogan(Koordinat.hucredenPiksele(getX()), Koordinat.hucredenPiksele(getY()) + Oyun.KARE_BOYUT, _oyunTahtasi));
            }
        }

        if (e instanceof Oyuncu) {

            if (_oyunTahtasi.dusmanlarTemizlendi() == false)
                return false;

            if (e.getXBoyut() == getX() && e.getYBoyut() == getY()) {
                if (_oyunTahtasi.dusmanlarTemizlendi())
                    _oyunTahtasi.oyunBitir();
            }

            return true;
        }

        return false;
    }
}
