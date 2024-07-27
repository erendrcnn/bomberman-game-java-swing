package bombergame;

import bombergame.gui.Klavye;
import bombergame.harita.DosyaHaritalama;
import bombergame.harita.Haritalama;
import bombergame.medya.Ekran;
import bombergame.medya.Guncelleme;
import bombergame.varlik.Mesaj;
import bombergame.varlik.Nesne;
import bombergame.varlik.karakter.Karakter;
import bombergame.varlik.karakter.Oyuncu;
import bombergame.varlik.karakter.dusman.Canavar;
import bombergame.varlik.nesne.ozellik.Ozellik;
import bombergame.varlik.saldiri.Bomba;
import bombergame.varlik.saldiri.Patlama;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.*;

public class OyunTahtasi implements Guncelleme {

    private static final boolean DEMO_MODE = false;
    protected Haritalama _haritalama;
    protected Oyun _oyun;
    protected Klavye _girdi;
    protected Ekran _ekran;

    public Nesne[] _varliklar;
    public List<Karakter> _karakterler = new ArrayList<>();
    protected List<Bomba> _bombalar = new ArrayList<>();
    private List<Mesaj> _mesajlar = new ArrayList<>();

    private int _gosterilenEkran = -1; //1:endgame, 2:changelevel, 3:paused

    private int _zaman = Oyun.TIME;
    private int puanlar = Oyun.POINTS;
    private int seviye = Oyun.SEVIYE;
    private int can = Oyun.CAN;
    public static char seciliOzellik = 'A';
    public static int seciliTema = 1;
    public static ArrayList<Character> ozellikListe = new ArrayList<>(Arrays.asList('A', 'K', 'M', 'H', 'B'));

    public OyunTahtasi(Oyun oyun, Klavye input, Ekran screen) {
        _oyun = oyun;
        _girdi = input;
        _ekran = screen;

        _gosterilenEkran = 4;
    }

    public void bombaTetikle() {
        for (Bomba b : _bombalar) {
            b.patlat();
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Guncelleme
    |--------------------------------------------------------------------------
     */
    @Override
    public void guncelle() {
        if (_oyun.oyunDurduMu()) return;

        varlikGuncelle();
        karakterGuncelle();
        bombaGuncelle();
        mesajGuncelle();
        zamanDolduMu();

        _karakterler.removeIf(Karakter::kaldirildiMi);
    }

    @Override
    public void olustur(Ekran screen) {
        if (_oyun.oyunDurduMu()) return;

        // Ekranın sadece görünen kısmını render et
        int x0 = Ekran.xKaydirma >> 4; //tile precision, -> left X
        int x1 = (Ekran.xKaydirma + screen.getGenislik() + Oyun.KARE_BOYUT) / Oyun.KARE_BOYUT; // -> right X
        int y0 = Ekran.yKaydirma >> 4;
        int y1 = (Ekran.yKaydirma + screen.getYukseklik()) / Oyun.KARE_BOYUT; //render one tile plus to fix black margins

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                _varliklar[x + y * _haritalama.getGenislik()].olustur(screen);
            }
        }

        bombaCiz(screen);
        karakterCiz(screen);
    }

    /*
    |--------------------------------------------------------------------------
    | Oyun Yönetimi
    |--------------------------------------------------------------------------
     */
    public void yeniOyun() {
        degerleriSifirla();
        haritaDegistir();
    }

    @SuppressWarnings("static-access")
    private void degerleriSifirla() {
        puanlar = Oyun.POINTS;
        seviye = Oyun.SEVIYE;
        can = Oyun.CAN;
        Oyuncu._ozellikler.clear();

        _oyun.oyuncuHiz = 0.7;
        _oyun.bombaAlan = 1;
        _oyun.bombaCephane = 1;
        _oyun.atlama = false;
        _oyun.bombaKontrol = false;
    }

    public void haritaDegistir() {
        // Tüm karakter ve thread'leri durdur
        for (Karakter karakter : _karakterler) {
            if (karakter instanceof Oyuncu) {
                ((Oyuncu) karakter).stop();
            } else if (karakter instanceof Canavar) {
                ((Canavar) karakter).stop();
            }
        }

        _zaman = Oyun.TIME;
        _gosterilenEkran = 2;
        _oyun.sifirlaEkranYenileme();
        _oyun.duraklat();
        _karakterler.clear();
        _bombalar.clear();
        _mesajlar.clear();
        _varliklar = null;

        haritaDosyasiOlustur("dunya/Harita.txt", 13, 33, seciliOzellik);

        if (DEMO_MODE)
            _haritalama = new DosyaHaritalama("dunya/HaritaTest.txt", this);
        else
            _haritalama = new DosyaHaritalama("dunya/Harita.txt", this);

        _varliklar = new Nesne[_haritalama.getYukseklik() * _haritalama.getGenislik()];

        _haritalama.varlikOlustur();
    }

    public static void haritaDosyasiOlustur(String dosyaYolu, int yukseklik, int genislik, char ozellikSembolu) {
        // Harita boyutları
        char[][] harita = new char[yukseklik][genislik];

        // Harita kenarlarını oluşturacak karakterler
        String ustKisim = "TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT";
        String altKisim = "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD";
        String sagKisim = "L";
        String solKisim = "R";

        // Kenar karakterlerini haritaya yerleştir
        harita[0] = ('6' + ustKisim + '7').toCharArray();
        harita[yukseklik - 1] = ('9' + altKisim + '8').toCharArray();

        for (int i = 1; i < yukseklik - 1; i++) {
            harita[i][0] = sagKisim.charAt(0);
            harita[i][genislik - 1] = solKisim.charAt(0);
            for (int j = 1; j < genislik - 1; j++) {
                harita[i][j] = ' '; // KORIDOR
            }
        }

        harita[1][1] = 'C'; // OYUNCU
        for (int i = 2; i < yukseklik - 2; i += 2) {
            for (int j = 2; j < genislik - 2; j += 2) {
                harita[i][j] = '#'; // SABIT DUVAR
            }
        }

        // Rasgele karakterleri yerleştir
        Random rastgele = new Random();
        int sayiOzellik = 1;
        int sayiDuvar = new Random().nextInt(11) + 50; // 50-60 arasında duvar
        int sayiKapi = 1;
        int sayiBalon = 5;

        while (sayiOzellik > 0 || sayiDuvar > 0 || sayiKapi > 0 || sayiBalon > 0) {
            int rastgeleSatir = rastgele.nextInt(yukseklik - 2) + 1;
            int rastgeleSutun = rastgele.nextInt(genislik - 2) + 1;

            if (harita[rastgeleSatir][rastgeleSutun] == ' ') {
                if (sayiOzellik > 0) {
                    if (rastgeleSatir > 2 || rastgeleSutun > 2) {
                        harita[rastgeleSatir][rastgeleSutun] = ozellikSembolu;
                        sayiOzellik--;
                    }
                } else if (sayiDuvar > 0) {
                    if (rastgeleSatir > 2 || rastgeleSutun > 2) {
                        harita[rastgeleSatir][rastgeleSutun] = '*';
                        sayiDuvar--;
                    }
                } else if (sayiKapi > 0) {
                    if (rastgeleSatir > 4 || rastgeleSutun > 4) {
                        harita[rastgeleSatir][rastgeleSutun] = 'P';
                        sayiKapi--;
                    }
                } else {
                    if (rastgeleSatir > 6 || rastgeleSutun > 6) {
                        harita[rastgeleSatir][rastgeleSutun] = '1';
                        sayiBalon--;
                    }
                }
            }
        }

        // Haritayı dosyaya yaz
        URL absPath = OyunTahtasi.class.getResource("/" + dosyaYolu);
        assert absPath != null;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(absPath.getPath()))) {
            writer.write(yukseklik + " " + genislik);
            writer.newLine();
            for (char[] row : harita) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean ozellikKullanildiMi(int x, int y) {
        for (Ozellik p : Oyuncu._ozellikler) {
            if (p.getX() == x && p.getY() == y)
                return true;
        }

        return false;
    }

    /*
    |--------------------------------------------------------------------------
    | Oyun Bitirme
    |--------------------------------------------------------------------------
     */
    protected void zamanDolduMu() {
        if (_zaman <= 0)
            oyunBitir();
    }

    public void oyunBitir() {
        if (can > 1) {
            _gosterilenEkran = 2;
            _oyun.sifirlaEkranYenileme();
            _oyun.duraklat();
            _oyun.oyunBitti = true;
            if (getPuanlar() >= _oyun.getMaxPuan()) {
                _oyun.setMaxPuan(getPuanlar());
                _oyun.maxSkorKaydet();
            }
            haritaDegistir();
            setCan(getCan() - 1);
        } else {
            setCan(getCan() - 1);
            _gosterilenEkran = 1;
            _oyun.sifirlaEkranYenileme();
            _oyun.duraklat();
            _oyun.oyunBitti = true;
            if (getPuanlar() >= _oyun.getMaxPuan()) {
                _oyun.setMaxPuan(getPuanlar());
                _oyun.maxSkorKaydet();
            }
        }
    }

    @SuppressWarnings("static-access")
    public void seviyeAtlama() {
        _gosterilenEkran = 2;
        Random r = new Random();
        if (!ozellikListe.isEmpty())
            seciliOzellik = ozellikListe.get(r.nextInt(ozellikListe.size()));
        else
            seciliOzellik = '*';

        Oyun.addBombaCephane(getBombalar().size());
        _oyun.sifirlaEkranYenileme();
        _oyun.duraklat();
        _oyun.oyunBitti = true;
        if (getPuanlar() >= _oyun.getMaxPuan()) {
            _oyun.setMaxPuan(getPuanlar());
            _oyun.maxSkorKaydet();
        }

        // Diger islemlerin bitmesi icin biraz bekleyelim
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        haritaDegistir();
        _oyun.bombaKontrol = false;
        setSeviye(getSeviye() + 1);
        setCan(getCan() + 1);
    }

    public boolean dusmanlarTemizlendi() {
        int total = 0;
        for (Karakter mob : _karakterler) {
            if (!(mob instanceof Oyuncu))
                ++total;
        }

        return total == 0;
    }

    /*
    |--------------------------------------------------------------------------
    | Oyun Duraklatma
    |--------------------------------------------------------------------------
     */
    public void oyunDuraklatAyarlar() {
        _oyun.sifirlaEkranYenileme();
        _gosterilenEkran = 5;
        _oyun.duraklat();
        _oyun.setAyarlarMenusu(true);
    }

    public void oyunDuraklatSifirla() {
        _oyun.sifirlaEkranYenileme();
        _gosterilenEkran = 6;
        _oyun.duraklat();
        _oyun.oyunYenilendi = true;
    }

    public void oyunDevam() {
        _oyun.sifirlaEkranYenileme();
        _gosterilenEkran = -1;
        _oyun.oyunDevam();
    }

    public void oyunDuraklat() {
        _oyun.sifirlaEkranYenileme();
        _gosterilenEkran = 3;
        _oyun.duraklat();
        _oyun.setAyarlarMenusu(true);
    }

    /*
    |--------------------------------------------------------------------------
    | Ekran Gösterme
    |--------------------------------------------------------------------------
     */
    public void ekranGoster(Graphics g) {
        _ekran.yaziTipiYukle();
        switch (_gosterilenEkran) {
            case 1:
                _ekran.oyunBittiCiz(g, puanlar, _oyun.getMaxPuan());
                break;
            case 2:
                _ekran.oyunYuklemeCiz(g);
                break;
            case 3:
                _ekran.oyunDuraklamaCiz(g);
                break;
            case 4:
                _ekran.menuCiz(g);
                break;
            case 5:
                _ekran.ayarlarCiz(g);
                break;
            case 6:
                _ekran.yeniOyunCiz(g);
                break;
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Getters & Setters
    |--------------------------------------------------------------------------
     */
    public Nesne getVarlik(double x, double y, Karakter m) {
        Nesne res;

        res = getPatlamaKonum((int) x, (int) y);
        if (res != null) return res;

        res = getBombaKonum(x, y);
        if (res != null) return res;

        res = getFarkiKarakter((int) x, (int) y, m);
        if (res != null) return res;

        res = getVarlikKonum((int) x, (int) y);

        return res;
    }

    public List<Bomba> getBombalar() {
        return _bombalar;
    }

    public Bomba getBombaKonum(double x, double y) {
        try {
            for (Bomba b : _bombalar) {
                if (b.getX() == (int) x && b.getY() == (int) y)
                    return b;
            }

            return null;
        } catch (ConcurrentModificationException | NoSuchElementException e) {
            return null;
        }
    }

    public Karakter getKarakterKonum(double x, double y) {
        try {
            for (Karakter cur : _karakterler) {
                if (cur.getX() == x && cur.getY() == y)
                    return cur;
            }

            return null;
        } catch (ConcurrentModificationException | NoSuchElementException e) {
            return null;
        }
    }

    public Oyuncu getOyuncu() {
        try {
            for (Karakter cur : _karakterler) {
                if (cur instanceof Oyuncu)
                    return (Oyuncu) cur;
            }

            return null;
        } catch (ConcurrentModificationException | NoSuchElementException e) {
            return null;
        }
    }

    public Karakter getFarkiKarakter(int x, int y, Karakter a) {
        try {
            for (Karakter cur : _karakterler) {
                if (cur == a) continue;
                if (cur.getXBoyut() == x && cur.getYBoyut() == y) {
                    return cur;
                }
            }

            return null;
        } catch (ConcurrentModificationException | NoSuchElementException e) {
            return null;
        }
    }

    public Patlama getPatlamaKonum(int x, int y) {
        try {
            for (Bomba b : _bombalar) {
                Patlama e = b.patlamaKonum(x, y);
                if (e != null) {
                    return e;
                }
            }

            return null;
        } catch (ConcurrentModificationException | NoSuchElementException e) {
            return null;
        }
    }

    public Nesne getVarlikKonum(double x, double y) {
        return _varliklar[(int) x + (int) y * _haritalama.getGenislik()];
    }

    /*
    |--------------------------------------------------------------------------
    | Ekleme
    |--------------------------------------------------------------------------
     */
    public void addVarlik(int pos, Nesne e) {
        _varliklar[pos] = e;
    }

    public void addKarakter(Karakter e) {
        _karakterler.add(e);
    }

    public void addBomba(Bomba e) {
        _bombalar.add(e);
    }

    public void addMesaj(Mesaj e) {
        _mesajlar.add(e);
    }

    /*
    |--------------------------------------------------------------------------
    | Cizim
    |--------------------------------------------------------------------------
     */

    protected void karakterCiz(Ekran screen) {
        for (Karakter karakter : _karakterler) {
            karakter.olustur(screen);
        }
    }

    protected void bombaCiz(Ekran screen) {
        for (Bomba bomba : _bombalar) {
            bomba.olustur(screen);
        }
    }

    public void mesajCiz(Graphics g) {
        for (Mesaj message : _mesajlar) {
            g.setFont(new Font("Arial", Font.PLAIN, message.getSize()));
            g.setColor(message.getColor());
            g.drawString(message.getMessage(), (int) message.getX() - Ekran.xKaydirma * Oyun.SCALE, (int) message.getY());
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Guncelleme
    |--------------------------------------------------------------------------
     */
    protected void varlikGuncelle() {
        if (_oyun.oyunDurduMu()) return;

        try {
            for (Nesne entity : _varliklar) {
                entity.guncelle();
            }
        } catch (ConcurrentModificationException | NoSuchElementException e) {
        }
    }

    protected void karakterGuncelle() {
        if (_oyun.oyunDurduMu()) return;

        try {
            for (Karakter karakter : _karakterler) {
                if (!_oyun.oyunDurduMu()) {
                    karakter.guncelle();
                }
            }
        } catch (ConcurrentModificationException | NoSuchElementException e) {
        }
    }

    protected void bombaGuncelle() {
        if (_oyun.oyunDurduMu()) return;

        try {
            for (Bomba bomba : _bombalar) {
                bomba.guncelle();
            }
        } catch (ConcurrentModificationException | NoSuchElementException e) {
        }
    }

    protected void mesajGuncelle() {
        if (_oyun.oyunDurduMu()) return;
        _mesajlar.removeIf(message -> message.getDuration() <= 0);

        try {
            for (Mesaj message : _mesajlar) {
                message.setDuration(message.getDuration() - 1);
            }
        } catch (ConcurrentModificationException | NoSuchElementException e) {
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Getters & Setters
    |--------------------------------------------------------------------------
     */
    public Klavye getGirdi() {
        return _girdi;
    }

    public Haritalama getHarita() {
        return _haritalama;
    }

    public Oyun getOyun() {
        return _oyun;
    }

    public int getAnlikEkran() {
        return _gosterilenEkran;
    }

    public void setAnlikEkran(int i) {
        _gosterilenEkran = i;
    }

    public void setPuanlar(int i) {
        puanlar = i;
    }

    public int getZaman() {
        return _zaman;
    }

    public void setZaman(int i) {
        _zaman = i;
    }

    public int getSeviye() {
        return seviye;
    }

    public void setSeviye(int i) {
        seviye = i;
    }

    public int getCan() {
        return can;
    }

    public void setCan(int i) {
        can = i;
    }

    public int zamanAzalt() {
        if (_oyun.oyunDurduMu())
            return this._zaman;
        else
            return this._zaman--;
    }

    public int getPuanlar() {
        return puanlar;
    }

    public void addPuanlar(int points) {
        this.puanlar += points;
    }

    public int getGenislik() {
        return _haritalama.getGenislik();
    }

    public int getYukseklik() {
        return _haritalama.getYukseklik();
    }
}
