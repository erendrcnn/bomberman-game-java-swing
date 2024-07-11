package bombergame.varlik.nesne.ozellik;

import bombergame.Oyun;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Oyuncu;


/*
TURKUAZ ICON:   Oyuncu duvarı patlattıktan sonra bu iconu alırsa bomba koyduktan sonra
                istediği zaman bombayı patlatabilir (Klavyeden b tuşuna basarak patlatabilir). Normalde 3
                saniye sonra patlayan bomba artık oyuncu ne zaman isterse o zaman patlar.
 */
public class OzellikKumanda extends Ozellik {

    public OzellikKumanda(int x, int y, Model model) {
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
        Oyun.setKontrol(true);
    }
}