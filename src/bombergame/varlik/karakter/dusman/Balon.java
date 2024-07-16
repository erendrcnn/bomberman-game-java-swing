package bombergame.varlik.karakter.dusman;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.medya.Model;
import bombergame.varlik.karakter.dusman.algo.StandartAlgo;

public class Balon extends Canavar {

    public Balon(int x, int y, OyunTahtasi oyunTahtasi) {
        super(x, y, oyunTahtasi, Model.balon_olum, Oyun.getOyuncuHiz() / 2, 100);

        _model = Model.balon_sol_1;
        algoritma = new StandartAlgo();
        _yon = algoritma.yonBelirle();
        // Thread bir ust sinifta baslatiliyor. (Her balon icin bir thread olusturulmasina gerek yok.)
    }

    @Override
    protected synchronized void modelBelirle() {
        switch (_yon) {
            case 0:
            case 1:
                _model = Model.movingSprite(Model.balon_sag_1, Model.balon_sag_2, Model.balon_sag_3, _animasyon, 60);
                break;
            case 2:
            case 3:
                _model = Model.movingSprite(Model.balon_sol_1, Model.balon_sol_2, Model.balon_sol_3, _animasyon, 60);
                break;
        }
    }
}
