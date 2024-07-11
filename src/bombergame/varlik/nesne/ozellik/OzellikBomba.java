package bombergame.varlik.nesne.ozellik;

import bombergame.Oyun;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Oyuncu;

/*
SARI ICON:  Oyuncu duvarı patlattıktan sonra bu iconu alırsa bombanın patlamasını
            beklemeden 2 adet bomba koyabilmektedir (Bombayı zaten bomba bulunan yere koyamaz,
            boş bi alana koyabilir).
 */
public class OzellikBomba extends Ozellik {

    public OzellikBomba(int x, int y, Model model) {
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
        Oyun.addBombaCephane(1);
    }


}
