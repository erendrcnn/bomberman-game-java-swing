package bombergame.harita;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.SabitDegiskenler;
import bombergame.medya.Ekran;
import bombergame.medya.Model;
import bombergame.varlik.KatmanliNesne;
import bombergame.varlik.karakter.Oyuncu;
import bombergame.varlik.karakter.dusman.Balon;
import bombergame.varlik.karakter.dusman.Sogan;
import bombergame.varlik.nesne.CikisKapisi;
import bombergame.varlik.nesne.Duvar;
import bombergame.varlik.nesne.Koridor;
import bombergame.varlik.nesne.duvar.SaglamZemin;
import bombergame.varlik.nesne.ozellik.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class DosyaHaritalama extends Haritalama implements SabitDegiskenler {

    public DosyaHaritalama(String path, OyunTahtasi oyunTahtasi) {
        super(path, oyunTahtasi);
    }

    @Override
    public void haritaYukle(String path) {
        try {
            URL absPath = DosyaHaritalama.class.getResource("/" + path);
            assert absPath != null;
            BufferedReader in = new BufferedReader(new InputStreamReader(absPath.openStream()));

            String data = in.readLine();
            StringTokenizer tokens = new StringTokenizer(data);

            yukseklik = Integer.parseInt(tokens.nextToken());
            genislik = Integer.parseInt(tokens.nextToken());

            satir = new String[yukseklik];

            for (int i = 0; i < yukseklik; i++) {
                satir[i] = in.readLine().substring(0, genislik);
            }

            in.close();
        } catch (IOException e) {
            System.out.println("[HATA] Harita olusturulamadi! : " + path);
        }
    }

    @Override
    public void varlikOlustur() {
        for (int y = 0; y < getYukseklik(); y++) {
            for (int x = 0; x < getGenislik(); x++) {
                haritaVarlikEkle(satir[y].charAt(x), x, y);
            }
        }
    }

    public void haritaVarlikEkle(char c, int x, int y) {
        int pos = x + y * getGenislik();

        // HARITA NESNELERI
        switch (c) {
            case '6': // Sol-Ust Kose
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sol_ust_sinir));
                break;
            case '7': // Sag-Ust Kose
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sag_ust_sinir));
                break;
            case '8': // Sag-Alt Kose
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sag_alt_sinir));
                break;
            case '9': // Sol-Alt Kose
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sol_alt_sinir));
                break;
            case 'T': // Ust sinir
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, ust_sinir));
                break;
            case 'L': // Sol sinir
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sol_sinir));
                break;
            case 'R': // Sag sinir
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sag_sinir));
                break;
            case 'D': // Alt sinir
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, alt_sinir));
                break;

            case '#': // Kirilamaz Duvar
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, saglamDuvar));
                break;
            case '*': // Kirilabilir Duvar
                oyunTahtasi.addVarlik(pos, new KatmanliNesne(x, y,
                        new Koridor(x, y, koridor),
                        new SaglamZemin(x, y, kirilabilirDuvar)));
                break;
            case 'P': // Cikis Kapisi
                oyunTahtasi.addVarlik(pos, new KatmanliNesne(x, y,
                        new Koridor(x, y, koridor),
                        new CikisKapisi(x, y, oyunTahtasi, cikisKapisi),
                        new SaglamZemin(x, y, kirilabilirDuvar)));
                break;

            case 'C': // Oyuncu
                haritaMatrix[y][x] = 1;
                oyunTahtasi.addKarakter(new Oyuncu(Koordinat.hucredenPiksele(x), Koordinat.hucredenPiksele(y) + Oyun.KARE_BOYUT, oyunTahtasi));
                Ekran.kaydirmaAyarla(0, 0);
                oyunTahtasi.addVarlik(pos, new Koridor(x, y, koridor));
                break;

            // Ozellikler
            case 'B': // Bomba Arttirma Ozelligi
                KatmanliNesne layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridor),
                        new SaglamZemin(x, y, kirilabilirDuvar));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikBomba(x, y, oyunTahtasi, Model.ozellik_bomba));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;
            case 'H': // Hiz Arttirma Ozelligi
                layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridor),
                        new SaglamZemin(x, y, kirilabilirDuvar));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikHiz(x, y, oyunTahtasi, Model.ozellik_hiz));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;
            case 'M': // Menzil Arttirma Ozelligi
                layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridor),
                        new SaglamZemin(x, y, kirilabilirDuvar));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikMenzil(x, y, oyunTahtasi, Model.ozellik_menzil));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;
            case 'K': // Kumanda Ozelligi
                layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridor),
                        new SaglamZemin(x, y, kirilabilirDuvar));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikKumanda(x, y, oyunTahtasi, Model.ozellik_kumanda));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;
            case 'A': // Atlama Ozelligi
                layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridor),
                        new SaglamZemin(x, y, kirilabilirDuvar));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikAtlama(x, y, oyunTahtasi, Model.ozellik_atlama));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;

            // Dusmanlar
            case '1':
                haritaMatrix[y][x] = 1;
                oyunTahtasi.addKarakter(new Balon(Koordinat.hucredenPiksele(x), Koordinat.hucredenPiksele(y) + Oyun.KARE_BOYUT, oyunTahtasi));
                oyunTahtasi.addVarlik(pos, new Koridor(x, y, koridor));
                break;
            case '2':
                haritaMatrix[y][x] = 1;
                oyunTahtasi.addKarakter(new Sogan(Koordinat.hucredenPiksele(x), Koordinat.hucredenPiksele(y) + Oyun.KARE_BOYUT, oyunTahtasi));
                oyunTahtasi.addVarlik(pos, new Koridor(x, y, koridor));
                break;

            // Zemin
            default:
                haritaMatrix[y][x] = 1;
                oyunTahtasi.addVarlik(pos, new Koridor(x, y, koridor));
                break;
        }
    }

}
