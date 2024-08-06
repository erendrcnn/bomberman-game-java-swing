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

    public DosyaHaritalama(String dosyaYolu, OyunTahtasi oyunTahtasi) {
        super(dosyaYolu, oyunTahtasi);
    }

    @Override
    public void haritaYukle(String dosyaYolu) {
        try {
            URL absPath = DosyaHaritalama.class.getResource("/" + dosyaYolu);
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
            System.out.println("[HATA] Harita olusturulamadi! : " + dosyaYolu);
        }
    }

    @Override
    public void varlikOlustur() {
        for (int y = 0; y < getYukseklik(); y++) {
            for (int x = 0; x < getGenislik(); x++) {
                haritaMatrix[y][x] = 0;
                haritaVarlikEkle(satir[y].charAt(x), x, y);
            }
        }
    }

    public void haritaVarlikEkle(char c, int x, int y) {
        int pos = x + y * getGenislik();
        Model cikisKapisiSecili, saglamDuvarSecili, koridorSecili, kirilabilirDuvarSecili,
                ust_sinirSecili, sol_sinirSecili, sag_sinirSecili, alt_sinirSecili, sol_ust_sinirSecili,
                sag_ust_sinirSecili, sag_alt_sinirSecili, sol_alt_sinirSecili;

        // Seçili temaya göre model nesnelerini belirle
        switch (OyunTahtasi.getSeciliTema()) {
            case 2:
                cikisKapisiSecili = cikisKapisi2;
                saglamDuvarSecili = saglamDuvar2;
                koridorSecili = koridor2;
                kirilabilirDuvarSecili = kirilabilirDuvar2;
                ust_sinirSecili = ust_sinir2;
                sol_sinirSecili = sol_sinir2;
                sag_sinirSecili = sag_sinir2;
                alt_sinirSecili = alt_sinir2;
                sol_ust_sinirSecili = sol_ust_sinir2;
                sag_ust_sinirSecili = sag_ust_sinir2;
                sag_alt_sinirSecili = sag_alt_sinir2;
                sol_alt_sinirSecili = sol_alt_sinir2;
                break;

            case 3:
                cikisKapisiSecili = cikisKapisi3;
                saglamDuvarSecili = saglamDuvar3;
                koridorSecili = koridor3;
                kirilabilirDuvarSecili = kirilabilirDuvar3;
                ust_sinirSecili = ust_sinir3;
                sol_sinirSecili = sol_sinir3;
                sag_sinirSecili = sag_sinir3;
                alt_sinirSecili = alt_sinir3;
                sol_ust_sinirSecili = sol_ust_sinir3;
                sag_ust_sinirSecili = sag_ust_sinir3;
                sag_alt_sinirSecili = sag_alt_sinir3;
                sol_alt_sinirSecili = sol_alt_sinir3;
                break;

            case 4:
                cikisKapisiSecili = cikisKapisi4;
                saglamDuvarSecili = saglamDuvar4;
                koridorSecili = koridor4;
                kirilabilirDuvarSecili = kirilabilirDuvar4;
                ust_sinirSecili = ust_sinir4;
                sol_sinirSecili = sol_sinir4;
                sag_sinirSecili = sag_sinir4;
                alt_sinirSecili = alt_sinir4;
                sol_ust_sinirSecili = sol_ust_sinir4;
                sag_ust_sinirSecili = sag_ust_sinir4;
                sag_alt_sinirSecili = sag_alt_sinir4;
                sol_alt_sinirSecili = sol_alt_sinir4;
                break;

            default:
                cikisKapisiSecili = cikisKapisi;
                saglamDuvarSecili = saglamDuvar;
                koridorSecili = koridor;
                kirilabilirDuvarSecili = kirilabilirDuvar;
                ust_sinirSecili = ust_sinir;
                sol_sinirSecili = sol_sinir;
                sag_sinirSecili = sag_sinir;
                alt_sinirSecili = alt_sinir;
                sol_ust_sinirSecili = sol_ust_sinir;
                sag_ust_sinirSecili = sag_ust_sinir;
                sag_alt_sinirSecili = sag_alt_sinir;
                sol_alt_sinirSecili = sol_alt_sinir;
        }

        // HARITA NESNELERI
        switch (c) {
            case '6': // Sol-Ust Kose
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sol_ust_sinirSecili));
                break;
            case '7': // Sag-Ust Kose
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sag_ust_sinirSecili));
                break;
            case '8': // Sag-Alt Kose
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sag_alt_sinirSecili));
                break;
            case '9': // Sol-Alt Kose
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sol_alt_sinirSecili));
                break;
            case 'T': // Ust sinir
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, ust_sinirSecili));
                break;
            case 'L': // Sol sinir
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sol_sinirSecili));
                break;
            case 'R': // Sag sinir
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, sag_sinirSecili));
                break;
            case 'D': // Alt sinir
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, alt_sinirSecili));
                break;

            case '#': // Kirilamaz Duvar
                oyunTahtasi.addVarlik(pos, new Duvar(x, y, saglamDuvarSecili));
                break;
            case '*': // Kirilabilir Duvar
                oyunTahtasi.addVarlik(pos, new KatmanliNesne(x, y,
                        new Koridor(x, y, koridorSecili),
                        new SaglamZemin(x, y, kirilabilirDuvarSecili)));
                break;
            case 'P': // Cikis Kapisi
                oyunTahtasi.addVarlik(pos, new KatmanliNesne(x, y,
                        new Koridor(x, y, koridorSecili),
                        new CikisKapisi(x, y, oyunTahtasi, cikisKapisiSecili),
                        new SaglamZemin(x, y, kirilabilirDuvarSecili)));
                break;

            case 'C': // Oyuncu
                haritaMatrix[y][x] = 1;
                oyunTahtasi.addKarakter(new Oyuncu(Koordinat.hucredenPiksele(x), Koordinat.hucredenPiksele(y) + Oyun.KARE_BOYUT, oyunTahtasi));
                Ekran.kaydirmaAyarla(0, 0);
                oyunTahtasi.addVarlik(pos, new Koridor(x, y, koridorSecili));
                break;

            // Ozellikler
            case 'B': // Bomba Arttirma Ozelligi
                KatmanliNesne layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridorSecili),
                        new SaglamZemin(x, y, kirilabilirDuvarSecili));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikBomba(x, y, oyunTahtasi, Model.ozellik_bomba));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;
            case 'H': // Hiz Arttirma Ozelligi
                layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridorSecili),
                        new SaglamZemin(x, y, kirilabilirDuvarSecili));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikHiz(x, y, oyunTahtasi, Model.ozellik_hiz));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;
            case 'M': // Menzil Arttirma Ozelligi
                layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridorSecili),
                        new SaglamZemin(x, y, kirilabilirDuvarSecili));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikMenzil(x, y, oyunTahtasi, Model.ozellik_menzil));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;
            case 'K': // Kumanda Ozelligi
                layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridorSecili),
                        new SaglamZemin(x, y, kirilabilirDuvarSecili));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikKumanda(x, y, oyunTahtasi, Model.ozellik_kumanda));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;
            case 'A': // Atlama Ozelligi
                layer = new KatmanliNesne(x, y,
                        new Koridor(x, y, koridorSecili),
                        new SaglamZemin(x, y, kirilabilirDuvarSecili));

                if (!oyunTahtasi.ozellikKullanildiMi(x, y)) {
                    layer.addBeforeTop(new OzellikAtlama(x, y, oyunTahtasi, Model.ozellik_atlama));
                }

                oyunTahtasi.addVarlik(pos, layer);
                break;

            // Dusmanlar
            case '1':
                haritaMatrix[y][x] = 1;
                oyunTahtasi.addKarakter(new Balon(Koordinat.hucredenPiksele(x), Koordinat.hucredenPiksele(y) + Oyun.KARE_BOYUT, oyunTahtasi));
                oyunTahtasi.addVarlik(pos, new Koridor(x, y, koridorSecili));
                break;
            case '2':
                haritaMatrix[y][x] = 1;
                oyunTahtasi.addKarakter(new Sogan(Koordinat.hucredenPiksele(x), Koordinat.hucredenPiksele(y) + Oyun.KARE_BOYUT, oyunTahtasi));
                oyunTahtasi.addVarlik(pos, new Koridor(x, y, koridorSecili));
                break;

            // Zemin
            default:
                haritaMatrix[y][x] = 1;
                oyunTahtasi.addVarlik(pos, new Koridor(x, y, koridorSecili));
                break;
        }
    }

}
