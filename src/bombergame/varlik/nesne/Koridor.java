package bombergame.varlik.nesne;

import bombergame.medya.Model;
import bombergame.varlik.Nesne;

public class Koridor extends Zemin {

    public Koridor(int x, int y, Model model) {
        super(x, y, model);
    }

    @Override
    public boolean kesisme(Nesne e) {
        return true;
    }
}
