package bombergame.harita;

import bombergame.OyunTahtasi;
import java.util.List;

public class HafizaHaritalama extends DosyaHaritalama {

    public HafizaHaritalama(List<String> mapData, int width, int height, OyunTahtasi oyunTahtasi) {
        super(oyunTahtasi);
        this.genislik = width;
        this.yukseklik = height;
        this.satir = mapData.toArray(new String[0]);
    }

    @Override
    public void haritaYukle(String dosyaYolu) {
        // Do nothing, map is already loaded from memory
    }
}
