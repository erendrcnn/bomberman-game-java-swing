package bombergame.harita;

public interface IHaritalama {
    void haritaYukle(String path);
    void varlikOlustur();

    int getGenislik();
    int getYukseklik();
}