package bombergame.varlik.nesne.ozellik;

import bombergame.Oyun;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Oyuncu;

/*
TURUNCU ICON: Oyuncu duvarı patlattıktan sonra bu iconu alırsa koşma hızı artar.
 */
public class OzellikHiz extends Ozellik {

    public OzellikHiz(int x, int y, Model model) {
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
        Oyun.addOyuncuHiz(0.25);
    }

}
