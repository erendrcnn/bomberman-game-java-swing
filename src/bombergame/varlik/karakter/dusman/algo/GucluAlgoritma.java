package bombergame.varlik.karakter.dusman.algo;

import bombergame.varlik.karakter.Oyuncu;
import bombergame.varlik.karakter.dusman.Canavar;

public class GucluAlgoritma extends Algoritma {
    Oyuncu oyuncu;
    Canavar dusman;

    public GucluAlgoritma(Oyuncu oyuncu, Canavar dusman) {
        this.oyuncu = oyuncu;
        this.dusman = dusman;
    }

    @Override
    public int yonBelirle() {
        // Oyuncu ile düşman arasındaki mesafe 4x4 bir alandan büyükse, rastgele bir yön belirle
        if (Math.abs(oyuncu.getXBoyut() - dusman.getXBoyut()) > 3 || Math.abs(oyuncu.getYBoyut() - dusman.getYBoyut()) > 3) {
            return rastgele.nextInt(4);
        }

        // Oyuncuya yaklaşma mantığı
        int dikeyYon = rastgele.nextInt(2);

        if (dikeyYon == 1) {
            int v = satirYonuBelirle();
            if (v != -1) {
                return v;
            } else {
                return sutunYonuBelirle();
            }
        } else {
            int h = sutunYonuBelirle();
            if (h != -1) {
                return h;
            } else {
                return satirYonuBelirle();
            }
        }
    }

    protected int sutunYonuBelirle() {
        if (oyuncu.getXBoyut() < dusman.getXBoyut()) {
            return 3; // Sol
        } else if (oyuncu.getXBoyut() > dusman.getXBoyut()) {
            return 1; // Sağ
        }
        return -1;
    }

    protected int satirYonuBelirle() {
        if (oyuncu.getYBoyut() < dusman.getYBoyut()) {
            return 0; // Yukarı
        } else if (oyuncu.getYBoyut() > dusman.getYBoyut()) {
            return 2; // Aşağı
        }
        return -1;
    }
}