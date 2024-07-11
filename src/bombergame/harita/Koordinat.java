package bombergame.harita;

import bombergame.Oyun;

public class Koordinat {

    public static int pikseldenHuceye(double i) {
        return (int) (i / Oyun.KARE_BOYUT);
    }

    public static int hucredenPiksele(int i) {
        return i * Oyun.KARE_BOYUT;
    }

    public static int hucredenPiksele(double i) {
        return (int) (i * Oyun.KARE_BOYUT);
    }
}
