package bombergame.varlik.nesne.ozellik;

import bombergame.medya.Model;
import bombergame.varlik.nesne.Zemin;

public abstract class Ozellik extends Zemin {

    protected boolean _aktif = false;

    public Ozellik(int x, int y, Model model) {
        super(x, y, model);
    }

    public abstract void setDeger();

    public boolean isAktif() {
        return _aktif;
    }

    public void setAktif(boolean active) {
        this._aktif = active;
    }
}
