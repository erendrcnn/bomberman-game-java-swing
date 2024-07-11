package bombergame.varlik.nesne.ozellik;

import bombergame.Oyun;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Oyuncu;

/*
YESIL ICON: Oyuncu duvarı patlattıktan sonra bu iconu alırsa bombasının ateşi sağa, sola,
            yukarı ve aşağı bir blok daha uzamaktadır. Görsel 4’te ateşin uzunluk artışı kırmızı bloklarla
            gösterilmiştir.
 */
public class OzellikMenzil extends Ozellik {

    public OzellikMenzil(int x, int y, Model model) {
        super(x, y, model);
    }

    @Override
    public boolean kesisme(Nesne e) {

        if (e instanceof Oyuncu) {
            ((Oyuncu) e).ozellikEkle(this);
            kaldir();
            return true;
        }

        return false;
    }

    @Override
    public void setDeger() {
        _aktif = true;
        Oyun.addBombaMenzil(1);
    }
}