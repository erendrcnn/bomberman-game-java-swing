package bombergame.varlik.karakter.dusman;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.medya.Model;
import bombergame.varlik.karakter.dusman.algo.GucluAlgoritma;

public class Sogan extends Enemy {

    public Sogan(int x, int y, OyunTahtasi oyunTahtasi) {
        super(x, y, oyunTahtasi, Model.sogan_olum, Oyun.getOyuncuHiz() * 2 / 3, 200);
        _model = Model.sogan_sol_1;
        algoritma = new GucluAlgoritma(_oyunTahtasi.getOyuncu(), this);
        _yon = algoritma.yonBelirle();
        // Thread bir ust sinifta baslatiliyor. (Her sogan icin bir thread olusturulmasina gerek yok.)
    }

    @Override
    protected synchronized void modelBelirle() {
        switch (_yon) {
            case 0:
            case 1:
                if (_hareketli)
                    _model = Model.movingSprite(Model.sogan_sag_1, Model.sogan_sag_2, Model.sogan_sag_3, _animasyon, 60);
                else
                    _model = Model.sogan_sol_1;
                break;
            case 2:
            case 3:
                if (_hareketli)
                    _model = Model.movingSprite(Model.sogan_sol_1, Model.sogan_sol_2, Model.sogan_sol_3, _animasyon, 60);
                else
                    _model = Model.sogan_sol_1;
                break;
        }
    }
}
