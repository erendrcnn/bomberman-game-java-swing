package bombergame.varlik;


public abstract class AnimasyonluNesne extends Nesne {
    protected int _animasyon = 0;
    protected final int MAX_ANIMASYON = 7500;           // 7500 ms = 7.5 saniye || Animasyonun akis suresini belirler.

    protected void animate() {
        if (_animasyon < MAX_ANIMASYON) _animasyon++;
        else _animasyon = 0;                            // Animasyonu günceller.
    }
}
