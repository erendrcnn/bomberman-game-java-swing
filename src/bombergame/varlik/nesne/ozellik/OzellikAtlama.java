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
MAVI ICON:  Oyuncu duvarı patlattıktan sonra bu iconu alırsa artık patlayabilir duvarların
            içinden geçebilmektedir. Yaşanabilecek ek durum: Eğer kullanıcı bombayı bir duvarı
            patlatmak üzere koyduysa ve kendisi o duvarın içindeyse, ateş ona temas edeceğinden
            yanar ve ölür.
 */
public class OzellikAtlama extends Ozellik {

    public OzellikAtlama(int x, int y, OyunTahtasi _oyunTahtasi, Model model) {
        super(x, y, _oyunTahtasi, model);
    }

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
        Oyun.setAtlama(true);
    }

    @Override
    public String toString() {
        return "OzellikAtlama";
    }
}
