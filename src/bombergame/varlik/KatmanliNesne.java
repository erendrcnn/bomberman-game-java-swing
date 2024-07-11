package bombergame.varlik;

import bombergame.medya.Ekran;
import bombergame.varlik.nesne.duvar.KirilganZemin;

import java.util.LinkedList;

public class KatmanliNesne extends Nesne {

    protected LinkedList<Nesne> _varliklar = new LinkedList<>();

    public KatmanliNesne(int x, int y, Nesne... entities) {
        _x = x;
        _y = y;

        for (int i = 0; i < entities.length; i++) {
            _varliklar.add(entities[i]);

            if (i > 1) { //Add to destroyable tiles the bellow model for rendering in explosion
                if (entities[i] instanceof KirilganZemin)
                    ((KirilganZemin) entities[i]).addBelowSprite(entities[i - 1].getModel());
            }
        }
    }

    @Override
    public void guncelle() {
        temizle();
        getBasVarlik().guncelle();
    }

    @Override
    public void olustur(Ekran screen) {
        getBasVarlik().olustur(screen);
    }

    public Nesne getBasVarlik() {
        return _varliklar.getLast();
    }

    private void temizle() {
        Nesne top = getBasVarlik();

        if (top.kaldirildiMi()) {
            haritaMatrix[(int) _varliklar.getLast()._y][(int) _varliklar.getLast()._x] = 1;
            /* DEBUG
            System.out.println(_entities.getLast()._x + " " + _entities.getLast()._y );
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 33; j++) System.out.print(matrix[i][j] + " ");
                System.out.println();
            }
            */
            _varliklar.removeLast();
        }
    }

    public void addBeforeTop(Nesne e) {
        _varliklar.add(_varliklar.size() - 1, e);
    }

    @Override
    public boolean kesisme(Nesne e) {
        return getBasVarlik().kesisme(e);
    }

}
