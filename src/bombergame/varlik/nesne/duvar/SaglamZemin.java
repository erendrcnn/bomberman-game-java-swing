package bombergame.varlik.nesne.duvar;

import bombergame.harita.Koordinat;
import bombergame.medya.Ekran;
import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.saldiri.PatlamaYayilim;

public class SaglamZemin extends KirilganZemin {
    public SaglamZemin(int x, int y, Model model) {
        super(x, y, model);
    }

    @Override
    public void guncelle() {
        super.guncelle();
    }

    @Override
    public void olustur(Ekran screen) {
        int x = Koordinat.hucredenPiksele(_x);
        int y = Koordinat.hucredenPiksele(_y);

        if (_parcalandi) {
            _model = movingSprite(kirilabilirDuvar_patla1, kirilabilirDuvar_patla2, kirilabilirDuvar_patla3);
            screen.altModelVeNesneyiCiz(x, y, this, _altTabaka);
        } else {
            screen.nesneyiCiz(x, y, this);
        }
    }

    @Override
    public boolean kesisme(Nesne e) {
        if (e instanceof PatlamaYayilim) {
            destroy();
            duvarKirmaMuzik.muzikCal(0);
        }

        return false;
    }
}
