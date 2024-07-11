package bombergame.varlik;


public abstract class AnimasyonluNesne extends Nesne {
    protected int _animasyon = 0;
    protected final int MAX_ANIMASYON = 7500; //save the animation status and dont let this get too big

    protected void animate() {
        if (_animasyon < MAX_ANIMASYON) _animasyon++;
        else _animasyon = 0; //reset animation
    }
}
