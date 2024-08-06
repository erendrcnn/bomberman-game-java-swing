package bombergame.varlik;

import bombergame.medya.Ekran;
import bombergame.varlik.nesne.duvar.KirilganZemin;

import java.util.LinkedList;

public class KatmanliNesne extends Nesne {

    protected LinkedList<Nesne> _varliklar = new LinkedList<>();

    public KatmanliNesne(int x, int y, Nesne... nesneler) {
        _x = x;
        _y = y;

        for (int i = 0; i < nesneler.length; i++) {
            _varliklar.add(nesneler[i]);

            // Patlama sonrasinda patlayan nesnelerin altina gizlenen nesneleri ekler.
            if (i > 1) {
                if (nesneler[i] instanceof KirilganZemin)
                    ((KirilganZemin) nesneler[i]).altTabakayaEkle(nesneler[i - 1].getModel());
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
            /*
            System.out.println(_varliklar.getLast()._x + " " + _varliklar.getLast()._y );
            for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 33; j++) System.out.print(haritaMatrix[i][j] + " ");
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
