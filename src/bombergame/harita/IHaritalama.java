package bombergame.harita;

public interface IHaritalama {
    void haritaYukle(String dosyaYolu);
    void varlikOlustur();

    int getGenislik();
    int getYukseklik();
}