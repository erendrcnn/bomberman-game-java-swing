package bombergame.varlik.nesne.duvar;

import bombergame.OyunTahtasi;
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
            switch (OyunTahtasi.getSeciliTema()) {
                case 2:
                    _model = hareketliModel(kirilabilirDuvar_patla12, kirilabilirDuvar_patla22, kirilabilirDuvar_patla32);
                    break;
                case 3:
                    _model = hareketliModel(kirilabilirDuvar_patla13, kirilabilirDuvar_patla23, kirilabilirDuvar_patla33);
                    break;
                case 4:
                    _model = hareketliModel(kirilabilirDuvar_patla14, kirilabilirDuvar_patla24, kirilabilirDuvar_patla34);
                    break;
                default:
                    _model = hareketliModel(kirilabilirDuvar_patla1, kirilabilirDuvar_patla2, kirilabilirDuvar_patla3);
            }
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
