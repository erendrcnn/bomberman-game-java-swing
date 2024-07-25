package bombergame.varlik.nesne.ozellik;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.harita.Koordinat;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Oyuncu;
import bombergame.varlik.karakter.dusman.Sogan;
import bombergame.varlik.saldiri.PatlamaYayilim;

/*
YESIL ICON: Oyuncu duvarı patlattıktan sonra bu iconu alırsa bombasının ateşi sağa, sola,
            yukarı ve aşağı bir blok daha uzamaktadır. Görsel 4’te ateşin uzunluk artışı kırmızı bloklarla
            gösterilmiştir.
 */
public class OzellikMenzil extends Ozellik {

    public OzellikMenzil(int x, int y, OyunTahtasi _oyunTahtasi, Model model) { super(x, y, _oyunTahtasi, model); }

    @Override
    public boolean kesisme(Nesne e) {

        if (e instanceof Oyuncu) {
            ((Oyuncu) e).ozellikEkle(this);
            kaldir();
            return true;
        }

        if (e instanceof PatlamaYayilim) {
            kaldir();
            for (int i = 0; i < 6; i++) {
                _oyunTahtasi.addKarakter(new Sogan(Koordinat.hucredenPiksele(getX()), Koordinat.hucredenPiksele(getY()) + Oyun.KARE_BOYUT, _oyunTahtasi));
            }
            return true;
        }

        return false;
    }

    @Override
    public void setDeger() {
        _aktif = true;
        Oyun.addBombaMenzil(1);
    }

    @Override
    public String toString() {
        return "OzellikMenzil";
    }
}