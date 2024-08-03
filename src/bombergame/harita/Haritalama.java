package bombergame.harita;

import bombergame.OyunTahtasi;

public abstract class Haritalama implements IHaritalama {
    protected int genislik, yukseklik;
    protected String[] satir;
    protected OyunTahtasi oyunTahtasi;

    public Haritalama(String dosyaYolu, OyunTahtasi oyunTahtasi) {
        haritaYukle(dosyaYolu);
        this.oyunTahtasi = oyunTahtasi;
    }

    @Override
    public abstract void haritaYukle(String dosyaYolu);

    public abstract void varlikOlustur();

    public int getGenislik() {
        return genislik;
    }

    public int getYukseklik() {
        return yukseklik;
    }
}
