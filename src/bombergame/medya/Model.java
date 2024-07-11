package bombergame.medya;

import java.util.Arrays;

public class Model {
    public int[] pikseller;
    public final int UZUNLUK;
    private int x, y;
    private ModelYapisi yapi;

    /**
     * OYUNCU
     */

    public static Model oyuncu_asagi = new Model(16, 0, 0, ModelYapisi.karakter, 16, 16);
    public static Model oyuncu_sag = new Model(16, 0, 1, ModelYapisi.karakter, 16, 16);
    public static Model oyuncu_sol = new Model(16, 0, 2, ModelYapisi.karakter, 16, 16);
    public static Model oyuncu_yukari = new Model(16, 0, 3, ModelYapisi.karakter, 16, 16);

    public static Model oyuncu_asagi_1 = new Model(16, 1, 0, ModelYapisi.karakter, 16, 16);
    public static Model oyuncu_asagi_2 = new Model(16, 2, 0, ModelYapisi.karakter, 16, 16);

    public static Model oyuncu_sag_1 = new Model(16, 1, 1, ModelYapisi.karakter, 16, 16);
    public static Model oyuncu_sag_2 = new Model(16, 2, 1, ModelYapisi.karakter, 16, 16);

    public static Model oyuncu_sol_1 = new Model(16, 1, 2, ModelYapisi.karakter, 16, 16);
    public static Model oyuncu_sol_2 = new Model(16, 2, 2, ModelYapisi.karakter, 16, 16);

    public static Model oyuncu_yukari_1 = new Model(16, 1, 3, ModelYapisi.karakter, 16, 16);
    public static Model oyuncu_yukari_2 = new Model(16, 2, 3, ModelYapisi.karakter, 16, 16);

    public static Model oyuncu_olum = new Model(16, 3, 2, ModelYapisi.karakter, 16, 16);

    /**
     * DUSMANLAR
     */

    // BALON
    public static Model balon_sol_1 = new Model(16, 0, 0, ModelYapisi.dusman, 16, 16);
    public static Model balon_sol_2 = new Model(16, 0, 1, ModelYapisi.dusman, 16, 16);
    public static Model balon_sol_3 = new Model(16, 0, 2, ModelYapisi.dusman, 16, 16);

    public static Model balon_sag_1 = new Model(16, 1, 0, ModelYapisi.dusman, 16, 16);
    public static Model balon_sag_2 = new Model(16, 1, 1, ModelYapisi.dusman, 16, 16);
    public static Model balon_sag_3 = new Model(16, 1, 2, ModelYapisi.dusman, 16, 16);

    public static Model balon_olum = new Model(16, 0, 3, ModelYapisi.dusman, 16, 16);

    // SOGAN 
    public static Model sogan_sol_1 = new Model(16, 2, 0, ModelYapisi.dusman, 16, 16);
    public static Model sogan_sol_2 = new Model(16, 2, 1, ModelYapisi.dusman, 16, 16);
    public static Model sogan_sol_3 = new Model(16, 2, 2, ModelYapisi.dusman, 16, 16);

    public static Model sogan_sag_1 = new Model(16, 3, 0, ModelYapisi.dusman, 16, 16);
    public static Model sogan_sag_2 = new Model(16, 3, 1, ModelYapisi.dusman, 16, 16);
    public static Model sogan_sag_3 = new Model(16, 3, 2, ModelYapisi.dusman, 16, 16);

    public static Model sogan_olum = new Model(16, 2, 3, ModelYapisi.dusman, 16, 16);

    public static Model canavar_olum_1 = new Model(16, 6, 0, ModelYapisi.dusman, 16, 16);
    public static Model canavar_olum_2 = new Model(16, 6, 1, ModelYapisi.dusman, 16, 16);
    public static Model canavar_olum_3 = new Model(16, 6, 2, ModelYapisi.dusman, 16, 16);

    /**
     * BOMBA
     */

    public static Model bomba_sekil1 = new Model(16, 0, 0, ModelYapisi.bomba, 16, 16);
    public static Model bomba_sekil2 = new Model(16, 1, 0, ModelYapisi.bomba, 16, 16);
    public static Model bomba_sekil3 = new Model(16, 2, 0, ModelYapisi.bomba, 16, 16);

    /**
     * BOMBA PATLAMASI
     */

    public static Model patlama_merkez = new Model(16, 0, 2, ModelYapisi.bomba, 16, 16);
    public static Model patlama_sol_yatay = new Model(16, 0, 1, ModelYapisi.bomba, 16, 16);
    public static Model patlama_orta_yatay = new Model(16, 1, 1, ModelYapisi.bomba, 16, 16);
    public static Model patlama_sag_yatay = new Model(16, 2, 1, ModelYapisi.bomba, 16, 16);
    public static Model patlama_sag_dikey = new Model(16, 3, 0, ModelYapisi.bomba, 16, 16);
    public static Model patlama_orta_dikey = new Model(16, 3, 1, ModelYapisi.bomba, 16, 16);
    public static Model patlama_sol_dikey = new Model(16, 3, 2, ModelYapisi.bomba, 16, 16);

    /**
     * OZELLIKLER
     */

    public static Model ozellik_bomba = new Model(16, 2, 2, ModelYapisi.esya, 16, 16);
    public static Model ozellik_menzil = new Model(16, 1, 0, ModelYapisi.esya, 16, 16);
    public static Model ozellik_hiz = new Model(16, 2, 0, ModelYapisi.esya, 16, 16);
    public static Model ozellik_atlama = new Model(16, 0, 1, ModelYapisi.esya, 16, 16);
    public static Model ozellik_kumanda = new Model(16, 3, 2, ModelYapisi.esya, 16, 16);

    public Model(int size, int x, int y, ModelYapisi yapi, int rw, int rh) {
        UZUNLUK = size;
        pikseller = new int[UZUNLUK * UZUNLUK];
        this.x = x * UZUNLUK;
        this.y = y * UZUNLUK;
        this.yapi = yapi;
        load();
    }

    public Model(int size, int color) {
        UZUNLUK = size;
        pikseller = new int[UZUNLUK * UZUNLUK];
        setColor(color);
    }

    private void setColor(int color) {
        Arrays.fill(pikseller, color);
    }

    private void load() {
        for (int j = 0; j < UZUNLUK; j++) {
            for (int i = 0; i < UZUNLUK; i++) {
                pikseller[i + j * UZUNLUK] = yapi.pikseller[(i + x) + (j + y) * yapi.UZUNLUK];
            }
        }
    }

    /**
     * HAREKET ANIMASYONLARI
     */

    public static Model movingSprite(Model normal, Model x1, Model x2, int animate, int time) {
        int calc = animate % time;
        int diff = time / 3;

        if (calc < diff) {
            return normal;
        }

        if (calc < diff * 2) {
            return x1;
        }

        return x2;
    }

    public static Model movingSprite(Model x1, Model x2, int animate, int time) {
        int diff = time / 2;
        return (animate % time > diff) ? x1 : x2;
    }

    public int getSize() {
        return this.UZUNLUK;
    }

    public int[] getPikseller() {
        return this.pikseller;
    }

    public int getPixel(int i) {
        return this.pikseller[i];
    }
}
