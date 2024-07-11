package bombergame.varlik.nesne.ozellik;

import bombergame.Oyun;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Oyuncu;

/*
MAVI ICON:  Oyuncu duvarı patlattıktan sonra bu iconu alırsa artık patlayabilir duvarların
            içinden geçebilmektedir. Yaşanabilecek ek durum: Eğer kullanıcı bombayı bir duvarı
            patlatmak üzere koyduysa ve kendisi o duvarın içindeyse, ateş ona temas edeceğinden
            yanar ve ölür.
 */
public class OzellikAtlama extends Ozellik {

    public OzellikAtlama(int x, int y, Model model) {
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
        Oyun.setAtlama(true);
    }
}
