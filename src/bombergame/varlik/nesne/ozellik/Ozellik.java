package bombergame.varlik.nesne.ozellik;

import bombergame.OyunTahtasi;
import bombergame.medya.Model;
import bombergame.varlik.nesne.Zemin;

public abstract class Ozellik extends Zemin {

    protected OyunTahtasi _oyunTahtasi;

    protected boolean _aktif = false;

    public Ozellik(int x, int y, OyunTahtasi _oyunTahtasi, Model model) {
        super(x, y, model);
        this._oyunTahtasi = _oyunTahtasi;
    }

    public abstract void setDeger();

    public boolean isAktif() {
        return _aktif;
    }

    public void setAktif(boolean active) {
        this._aktif = active;
    }
}
