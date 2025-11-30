package bombergame.harita;

import bombergame.OyunTahtasi;

public abstract class Haritalama implements IHaritalama {
    protected int genislik, yukseklik;
    protected String[] satir;
    protected OyunTahtasi oyunTahtasi;

    public Haritalama(String dosyaYolu, OyunTahtasi oyunTahtasi) {
        this.oyunTahtasi = oyunTahtasi;
        if (dosyaYolu != null) {
            haritaYukle(dosyaYolu);
        }
    }

    protected Haritalama(OyunTahtasi oyunTahtasi) {
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
