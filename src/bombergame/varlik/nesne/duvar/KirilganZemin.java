package bombergame.varlik.nesne.duvar;

import bombergame.medya.Model;
import bombergame.varlik.Nesne;
import bombergame.varlik.nesne.Zemin;
import bombergame.varlik.saldiri.PatlamaYayilim;

public class KirilganZemin extends Zemin {

    private static final int MAX_ANIMASYON = 7500;  // Animasyon durumunu saklayın ve çok büyük olmasına izin vermeyin
    private int _animasyon = 0;
    protected boolean _parcalandi = false;
    protected int _kaybolmaSuresi = 20;
    protected Model _altTabaka = koridor;           // Varsayılan

    public KirilganZemin(int x, int y, Model model) {
        super(x, y, model);
    }

    @Override
    public void guncelle() {
        if (_parcalandi) {
            if (_animasyon < MAX_ANIMASYON) {
                _animasyon++;
            } else {
                _animasyon = 0; // Animasyonu sıfırla
            }
            if (_kaybolmaSuresi > 0) {
                _kaybolmaSuresi--;
            } else {
                kaldir();
            }
        }
    }

    public void destroy() {
        _parcalandi = true;
    }

    @Override
    public boolean kesisme(Nesne e) {
        if (e instanceof PatlamaYayilim) {
            destroy();
        }
        return false;
    }

    public void addBelowSprite(Model model) {
        _altTabaka = model;
    }

    protected Model movingSprite(Model normal, Model x1, Model x2) {
        int calc = _animasyon % 30;

        if (calc < 10) {
            return normal;
        } else if (calc < 20) {
            return x1;
        } else {
            return x2;
        }
    }
}
