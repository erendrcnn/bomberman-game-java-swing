package bombergame;

import bombergame.gui.Klavye;
import bombergame.gui.Pencere;
import bombergame.medya.Ekran;
import bombergame.varlik.karakter.Karakter;
import bombergame.varlik.karakter.Oyuncu;
import bombergame.varlik.karakter.dusman.Canavar;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.*;

public class Oyun extends Canvas implements MouseListener, MouseMotionListener, SabitDegiskenler {

    /*
    >> SABIT DEGISKENLER
     */

    public static final int KARE_BOYUT = 16;
    public static final int GENISLIK = KARE_BOYUT * (33 / 2);
    public static final int YUKSEKLIK = 13 * KARE_BOYUT;
    public static final int OLCEK = 3;
    private static final String BASLIK = "Bomberman Game - Java Swing (Eren Durucan)";
    private static final int BOMBACEPHANE = 1;
    private static final int BOMBAALAN = 1;
    private static final boolean BOMBAKONTROL = false;
    private static final boolean ATLAMA = false;
    private static final double OYUNCUHIZ = 0.7;
    private static final int EKRANYENILEME = 2;
    protected static final int SURE_SABIT = 200;
    protected static final int PUAN_SABIT = 0;
    protected static final int SEVITE_SABIT = 1;
    protected static final int CAN_SABIT = 2;

    /*
    >> DEGISKENLER
     */

    private static int _maxPuan = 0;
    protected static int bombaCephane = BOMBACEPHANE;
    protected static int bombaAlan = BOMBAALAN;
    protected static boolean bombaKontrol = BOMBAKONTROL;
    protected static boolean atlama = ATLAMA;
    protected static double oyuncuHiz = OYUNCUHIZ;
    protected int _ekranYenileme = EKRANYENILEME;

    private boolean _oyunDevam = false;
    private boolean _menu = true;
    private boolean _oyunDurdu = true;
    private boolean ayarlarMenusu = false;
    public boolean oyunBitti = false;
    public boolean oyunYenilendi = false;

    private Klavye _girdi;
    private OyunTahtasi _oyunTahtasi;
    private Ekran ekran;
    private Pencere _pencere;

    private final BufferedImage resim = new BufferedImage(GENISLIK, YUKSEKLIK, BufferedImage.TYPE_INT_RGB);
    private int[] pikseller = ((DataBufferInt) resim.getRaster().getDataBuffer()).getData();

    public Oyun(Pencere pencere) {
        this._pencere = pencere;
        this._pencere.setTitle(BASLIK);
        this.ekran = new Ekran(GENISLIK, YUKSEKLIK);
        this._girdi = new Klavye();
        this._oyunTahtasi = new OyunTahtasi(this, _girdi, ekran);

        addKeyListener(_girdi);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    /*
    >> OYUN BASLATMA VE YENILEME
     */

    private void oyunuYenile() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        ekran.temizle();
        _oyunTahtasi.olustur(ekran);

        System.arraycopy(ekran._pikseller, 0, pikseller, 0, pikseller.length);

        Graphics g = bs.getDrawGraphics();
        g.drawImage(resim, 0, 0, getWidth(), getHeight(), null);
        _oyunTahtasi.mesajCiz(g);

        g.dispose();
        bs.show();
    }

    public void ekraniYenile() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        ekran.temizle();
        Graphics g = bs.getDrawGraphics();
        _oyunTahtasi.ekranGoster(g);

        g.dispose();
        bs.show();
    }

    private void yenile() {
        _girdi.update();
        _oyunTahtasi.guncelle();
    }

    public void baslat() {
        maxSkorOku();
        oyunMuzik.muzikCal(100);

        while (_menu) {
            ekraniYenile();
        }

        _oyunTahtasi.haritaDegistir();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; // 60 FPS
        double delta = 0;
        requestFocus();

        while (_oyunDevam) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                yenile();
                delta--;
            }

            if (_girdi.mola) {
                _girdi.mola = false;

                if (oyunDurduMu()) {
                    oyunKayitOku();
                    _oyunTahtasi.oyunDevam();

                } else {
                    _oyunTahtasi.oyunDuraklat();
                    oyunKayit();
                }

                for (Karakter karakter : _oyunTahtasi._karakterler) {
                    if (karakter instanceof Canavar canavar) {
                        if (_oyunDurdu) {
                            canavar.duraklat();
                        } else {
                            canavar.devamEt();
                        }
                    }
                    if (karakter instanceof Oyuncu oyuncu) {
                        if (_oyunDurdu) {
                            oyuncu.duraklat();
                        } else {
                            oyuncu.devamEt();
                        }
                    }
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (bombaKontrol && getGirdi().kumanda) {
                _oyunTahtasi.bombaTetikle();
            }

            if (!_oyunDurdu) {
                _pencere.getBilgiPanel().setVisible(true);
            } else {
                _pencere.getBilgiPanel().setVisible(ayarlarMenusu || oyunBitti || oyunYenilendi);
            }

            if (_oyunDurdu) {
                if (_ekranYenileme <= 0) {
                    _oyunTahtasi.setAnlikEkran(5);
                    _oyunDurdu = false;
                }
                ekraniYenile();
            } else {
                oyunuYenile();
            }

            if (System.currentTimeMillis() - timer > 1000) {
                _pencere.setZaman(_oyunTahtasi.zamanAzalt());
                _pencere.setPuan(_oyunTahtasi.getPuanlar());
                _pencere.setSeviye(_oyunTahtasi.getSeviye());
                _pencere.setCan(_oyunTahtasi.getCan());
                timer += 1000;
                _pencere.setTitle(BASLIK);

                if (_oyunTahtasi.getAnlikEkran() == 2)
                    --_ekranYenileme;
            }
        }
    }

    /*
    >> OYUN KAYIT VE OKUMA
     */

    public void oyunKayit() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(kayitDosyasi))) {
            out.write(_oyunTahtasi.getPuanlar() + "\n");
            out.write(_oyunTahtasi.getZaman() + "\n");
            out.write(_oyunTahtasi.getSeviye() + "\n");
            out.write(_maxPuan + "\n");
            out.write(bombaCephane + "\n");
            out.write(bombaAlan + "\n");
            out.write(bombaKontrol + "\n");
            out.write(atlama + "\n");
            out.write(oyuncuHiz + "\n");
            out.write(_ekranYenileme + "\n");
            out.write(_oyunDevam + "\n");
            out.write(_menu + "\n");
            out.write(_oyunDurdu + "\n");
            out.write(ayarlarMenusu + "\n");
            out.write(oyunBitti + "\n");
            out.write(oyunYenilendi + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void oyunKayitOku() {
        try (BufferedReader in = new BufferedReader(new FileReader(kayitDosyasi))) {
            _oyunTahtasi.setPuanlar(Integer.parseInt(in.readLine()));
            _oyunTahtasi.setZaman(Integer.parseInt(in.readLine()));
            _oyunTahtasi.setSeviye(Integer.parseInt(in.readLine()));
            _maxPuan = Integer.parseInt(in.readLine());
            bombaCephane = Integer.parseInt(in.readLine());
            bombaAlan = Integer.parseInt(in.readLine());
            bombaKontrol = Boolean.parseBoolean(in.readLine());
            atlama = Boolean.parseBoolean(in.readLine());
            oyuncuHiz = Double.parseDouble(in.readLine());
            _ekranYenileme = Integer.parseInt(in.readLine());
            _oyunDevam = Boolean.parseBoolean(in.readLine());
            _menu = Boolean.parseBoolean(in.readLine());
            _oyunDurdu = Boolean.parseBoolean(in.readLine());
            ayarlarMenusu = Boolean.parseBoolean(in.readLine());
            oyunBitti = Boolean.parseBoolean(in.readLine());
            oyunYenilendi = Boolean.parseBoolean(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void maxSkorOku() {
        try (BufferedReader read = new BufferedReader(new FileReader(new File(skorDosyasi)))) {
            String score = read.readLine().trim();
            _maxPuan = score == null ? 0 : Integer.parseInt(score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void maxSkorKaydet() {
        try (FileWriter fileWriter = new FileWriter(new File(skorDosyasi))) {
            fileWriter.write(String.valueOf(_maxPuan));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    >> MOUSE HAREKETLERI VE EYLEMLERI
     */

    @Override
    public void mouseClicked(MouseEvent e) {
        handleMouseClick(e);
    }

    private void handleMouseClick(MouseEvent e) {
        Rectangle oynaButon = new Rectangle(GENISLIK + 40, YUKSEKLIK + 140, 170, 60);
        Rectangle ayarlarButon = new Rectangle(GENISLIK + 40, YUKSEKLIK + 210, 170, 60);
        Rectangle ayarlarKapatButon = new Rectangle(GENISLIK + 300, YUKSEKLIK - 60, 50, 50);
        Rectangle ozellikDegistirSagButon = new Rectangle(GENISLIK + 270, YUKSEKLIK + 40, 30, 30);
        Rectangle ozellikDegistirSolButon = new Rectangle(GENISLIK + 70, YUKSEKLIK + 40, 30, 30);
        Rectangle temaDegistirSagButon = new Rectangle(GENISLIK + 270, YUKSEKLIK + 140, 30, 30);
        Rectangle temaDegistirSolButon = new Rectangle(GENISLIK + 70, YUKSEKLIK + 140, 30, 30);
        Rectangle tamamButon = new Rectangle(GENISLIK + 90, YUKSEKLIK + 220, 100, 50);
        Rectangle yeniOyunBaslatButon = new Rectangle(GENISLIK + 150, YUKSEKLIK + 100, 100, 40);
        Rectangle yeniOyunPencereKapatButon = new Rectangle(GENISLIK - 10, YUKSEKLIK + 100, 100, 40);
        Rectangle yeniOyunButon = new Rectangle(GENISLIK + 50, YUKSEKLIK + 170, 150, 50);

        if (oynaButon.contains(e.getX(), e.getY()) && _menu && !ayarlarMenusu) {
            _menu = false;
            _oyunDevam = true;
        }

        if (ayarlarButon.contains(e.getX(), e.getY()) && _menu && !ayarlarMenusu) {
            ayarlarMenusu = true;
            getOyunTahtasi().setAnlikEkran(5);
        }

        if (ayarlarKapatButon.contains(e.getX(), e.getY()) && (_oyunDurdu || ayarlarMenusu)) {
            ayarlarMenusu = false;
            if (_menu) {
                getOyunTahtasi().setAnlikEkran(4);
            } else {
                getOyunTahtasi().oyunDevam();
            }
        }

        handleOzellikDegistir(e, ozellikDegistirSagButon, ozellikDegistirSolButon);
        handleTemaDegistir(e, temaDegistirSagButon, temaDegistirSolButon);

        if (tamamButon.contains(e.getX(), e.getY()) && ayarlarMenusu) {
            ayarlarMenusu = false;
            if (_menu) {
                getOyunTahtasi().setAnlikEkran(4);
            } else {
                getOyunTahtasi().yeniOyun();
            }
            switch (OyunTahtasi.getSeciliTema()) {
                case 2 -> _pencere.getBilgiPanel().arkaplanDegistir(arkaPlanRenk2);
                case 3 -> _pencere.getBilgiPanel().arkaplanDegistir(arkaPlanRenk3);
                case 4 -> _pencere.getBilgiPanel().arkaplanDegistir(arkaPlanRenk4);
                default -> _pencere.getBilgiPanel().arkaplanDegistir(arkaPlanRenk);
            }
        }

        if (yeniOyunBaslatButon.contains(e.getX(), e.getY()) && oyunYenilendi) {
            getOyunTahtasi().yeniOyun();
            oyunYenilendi = false;
        }

        if (yeniOyunPencereKapatButon.contains(e.getX(), e.getY()) && oyunYenilendi) {
            getOyunTahtasi().oyunDevam();
            oyunYenilendi = false;
        }

        if (yeniOyunButon.contains(e.getX(), e.getY()) && oyunBitti) {
            _oyunTahtasi.yeniOyun();
            oyunBitti = false;
        }
    }

    private void handleOzellikDegistir(MouseEvent e, Rectangle sagButon, Rectangle solButon) {
        if (sagButon.contains(e.getX(), e.getY()) && ayarlarMenusu) {
            char secilen = switch (OyunTahtasi.getSeciliOzellik()) {
                case 'B' -> 'M';
                case 'M' -> 'A';
                case 'A' -> 'H';
                case 'H' -> 'K';
                case 'K' -> 'B';
                default -> OyunTahtasi.getSeciliOzellik();
            };
            OyunTahtasi.setSeciliOzellik(secilen);
        }

        if (solButon.contains(e.getX(), e.getY()) && ayarlarMenusu) {
            char secilen = switch (OyunTahtasi.getSeciliOzellik()) {
                case 'B' -> 'K';
                case 'M' -> 'B';
                case 'A' -> 'M';
                case 'H' -> 'A';
                case 'K' -> 'H';
                default -> OyunTahtasi.getSeciliOzellik();
            };
            OyunTahtasi.setSeciliOzellik(secilen);
        }
    }

    private void handleTemaDegistir(MouseEvent e, Rectangle sagButon, Rectangle solButon) {
        if (sagButon.contains(e.getX(), e.getY()) && ayarlarMenusu) {
            int secilen = switch (OyunTahtasi.getSeciliTema()) {
                case 1 -> 2;
                case 2 -> 3;
                case 3 -> 4;
                case 4 -> 1;
                default -> OyunTahtasi.getSeciliTema();
            };
            OyunTahtasi.setSeciliTema(secilen);
        }

        if (solButon.contains(e.getX(), e.getY()) && ayarlarMenusu) {
            int secilen = switch (OyunTahtasi.getSeciliTema()) {
                case 1 -> 4;
                case 2 -> 1;
                case 3 -> 2;
                case 4 -> 3;
                default -> OyunTahtasi.getSeciliTema();
            };
            OyunTahtasi.setSeciliTema(secilen);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        handleMouseMove(e);
    }

    private void handleMouseMove(MouseEvent e) {
        Rectangle oynaButon = new Rectangle(GENISLIK + 40, YUKSEKLIK + 140, 170, 60);
        Rectangle ayarlarButon = new Rectangle(GENISLIK + 40, YUKSEKLIK + 210, 170, 60);
        Rectangle ayarlarKapatButon = new Rectangle(GENISLIK + 300, YUKSEKLIK - 60, 50, 50);
        Rectangle ozellikDegistirSagButon = new Rectangle(GENISLIK + 270, YUKSEKLIK + 40, 30, 30);
        Rectangle ozellikDegistirSolButon = new Rectangle(GENISLIK + 70, YUKSEKLIK + 40, 30, 30);
        Rectangle temaDegistirSagButon = new Rectangle(GENISLIK + 270, YUKSEKLIK + 140, 30, 30);
        Rectangle temaDegistirSolButon = new Rectangle(GENISLIK + 70, YUKSEKLIK + 140, 30, 30);
        Rectangle tamamButon = new Rectangle(GENISLIK + 90, YUKSEKLIK + 220, 100, 50);

        if (_menu && !ayarlarMenusu) {
            if (oynaButon.contains(e.getX(), e.getY()) || ayarlarButon.contains(e.getX(), e.getY())) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }

        if (ayarlarMenusu) {
            if (ayarlarKapatButon.contains(e.getX(), e.getY())
                    || ozellikDegistirSagButon.contains(e.getX(), e.getY())
                    || ozellikDegistirSolButon.contains(e.getX(), e.getY())
                    || temaDegistirSagButon.contains(e.getX(), e.getY())
                    || temaDegistirSolButon.contains(e.getX(), e.getY())
                    || tamamButon.contains(e.getX(), e.getY())) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }

        if (oyunBitti) {
            Rectangle yeniOyunButon = new Rectangle(GENISLIK + 50, YUKSEKLIK + 170, 150, 50);
            if (yeniOyunButon.contains(e.getX(), e.getY())) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }

        if (oyunYenilendi) {
            Rectangle yeniOyunBaslatButon = new Rectangle(GENISLIK + 150, YUKSEKLIK + 100, 100, 40);
            Rectangle yeniOyunPencereKapatButon = new Rectangle(GENISLIK - 10, YUKSEKLIK + 100, 100, 40);
            if (yeniOyunBaslatButon.contains(e.getX(), e.getY()) || yeniOyunPencereKapatButon.contains(e.getX(), e.getY())) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                setCursor(Cursor.getDefaultCursor());
            }
        }

        if (!_menu && !ayarlarMenusu && !oyunBitti && !oyunYenilendi) {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    /*
    >> GETTER VE SETTER METODLARI
     */

    public static double getOyuncuHiz() {
        return oyuncuHiz;
    }

    public static int getBombaCephane() {
        return bombaCephane;
    }

    public static int getBombaAlan() {
        return bombaAlan;
    }

    public static boolean getBombaKontrol() {
        return bombaKontrol;
    }

    public static void addOyuncuHiz(double i) {
        oyuncuHiz += i;
    }

    public static void addBombaMenzil(int i) {
        bombaAlan += i;
    }

    public static void addBombaCephane(int i) {
        bombaCephane += i;
    }

    public static void setKontrol(boolean b) {
        bombaKontrol = b;
    }

    public static void setAtlama(boolean b) {
        atlama = b;
    }

    public static boolean getAtlama() {
        return atlama;
    }

    public int getEkranYenileme() {
        return _ekranYenileme;
    }

    public void sifirlaEkranYenileme() {
        _ekranYenileme = EKRANYENILEME;
    }

    public Klavye getGirdi() {
        return _girdi;
    }

    public OyunTahtasi getOyunTahtasi() {
        return _oyunTahtasi;
    }

    public Pencere getPencere() {
        return _pencere;
    }

    public void oyunDevam() {
        _oyunDevam = true;
        _oyunDurdu = false;
    }

    public boolean getMenu() {
        return _menu;
    }

    public void oyunSonlandir() {
        _oyunDevam = false;
    }

    public boolean oyunDevamMi() {
        return _oyunDevam;
    }

    public boolean oyunDurduMu() {
        return _oyunDurdu;
    }

    public void duraklat() {
        _oyunDurdu = true;
    }

    public void setAyarlarMenusu(boolean _isSetting) {
        this.ayarlarMenusu = _isSetting;
    }

    public boolean isAyarlarMenusu() {
        return ayarlarMenusu;
    }

    public int getMaxPuan() {
        return _maxPuan;
    }

    public void setMaxPuan(int highscore) {
        _maxPuan = highscore;
    }

    public Ekran getEkran() {
        return ekran;
    }
}
