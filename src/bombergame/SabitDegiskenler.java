package bombergame;

import bombergame.medya.Model;
import bombergame.medya.ModelYapisi;
import bombergame.medya.Muzik;

import javax.swing.*;
import java.awt.*;

/*
 >> Bu arayüz, oyunun tum sabit degiskenlerini ve sabit nesnelerini icerir.
 */
public interface SabitDegiskenler {
    int HARITA_GENISLIK = 33;
    int HARITA_YUKSEKLIK = 13;
    int[][] haritaMatrix = new int[HARITA_YUKSEKLIK][HARITA_GENISLIK]; // HARITA BOYUTU

    String kayitDosyasi = "res/veri/kayit.txt";
    String skorDosyasi = "res/veri/MaxSkor.txt";
    String fontDosyasi1 = "res/font/OYUNFONT1.otf";             // Font: JOYSTICK
    String fontDosyasi2 = "res/font/OYUNFONT2.ttf";             // Font: VBBRUSHTB Bold
    String ayarlarPanel = "res/model/ayarlar-tablo.png";
    String arkaPlanPanel = "res/model/anamenu.png";
    String yeniOyunPanel = "res/model/yeni-oyun.png";
    String skorPanel = "res/model/skor-tablo.png";

    Color arkaPlanRenk = new Color(0, 173, 57, 255);
    Color arkaPlanRenk2 = new Color(62, 62, 62, 255);
    Color arkaPlanRenk3 = new Color(51, 152, 75, 255);
    Color arkaPlanRenk4 = new Color(244, 215, 169, 255);

    Muzik oyunMuzik = new Muzik("res/muzik/Tema.wav");
    Muzik bombaYerlestirMuzik = new Muzik("res/muzik/BombaKoyma.wav");
    Muzik bombaPatlamaMuzik = new Muzik("res/muzik/Patlama.wav");
    Muzik olumMuzik = new Muzik("res/muzik/DarbeAlma.wav");
    Muzik esyaAlmaMuzik = new Muzik("res/muzik/EsyaAlma.wav");
    Muzik duvarKirmaMuzik = new Muzik("res/muzik/Kirilma.wav");

    ModelYapisi karakter = new ModelYapisi("/model/karakter.png", 64);
    ModelYapisi bomba = new ModelYapisi("/model/bomba.png", 64);
    ModelYapisi esya = new ModelYapisi("/model/esya.png", 64);
    ModelYapisi dusman = new ModelYapisi("/model/dusman.png", 128);
    ModelYapisi harita = new ModelYapisi("/model/haritalama.png", 64);
    ModelYapisi harita2 = new ModelYapisi("/model/haritalama2.png", 64);
    ModelYapisi harita3 = new ModelYapisi("/model/haritalama3.png", 64);
    ModelYapisi harita4 = new ModelYapisi("/model/haritalama4.png", 64);

    Model cikisKapisi = new Model(16, 0, 0, ModelYapisi.harita);
    Model saglamDuvar = new Model(16, 1, 0, ModelYapisi.harita);
    Model koridor = new Model(16, 2, 0, ModelYapisi.harita);

    Model kirilabilirDuvar = new Model(16, 0, 1, ModelYapisi.harita);
    Model kirilabilirDuvar_patla1 = new Model(16, 1, 1, ModelYapisi.harita);
    Model kirilabilirDuvar_patla2 = new Model(16, 2, 1, ModelYapisi.harita);
    Model kirilabilirDuvar_patla3 = new Model(16, 3, 1, ModelYapisi.harita);

    Model ust_sinir = new Model(16, 0, 2, ModelYapisi.harita);
    Model sol_sinir = new Model(16, 1, 2, ModelYapisi.harita);
    Model sag_sinir = new Model(16, 2, 2, ModelYapisi.harita);
    Model alt_sinir = new Model(16, 3, 2, ModelYapisi.harita);

    Model sol_ust_sinir = new Model(16, 0, 3, ModelYapisi.harita);
    Model sag_ust_sinir = new Model(16, 1, 3, ModelYapisi.harita);
    Model sag_alt_sinir = new Model(16, 2, 3, ModelYapisi.harita);
    Model sol_alt_sinir = new Model(16, 3, 3, ModelYapisi.harita);

    Model cikisKapisi2 = new Model(16, 0, 0, ModelYapisi.harita2);
    Model saglamDuvar2 = new Model(16, 1, 0, ModelYapisi.harita2);
    Model koridor2 = new Model(16, 2, 0, ModelYapisi.harita2);

    Model kirilabilirDuvar2 = new Model(16, 0, 1, ModelYapisi.harita2);
    Model kirilabilirDuvar_patla12 = new Model(16, 1, 1, ModelYapisi.harita2);
    Model kirilabilirDuvar_patla22 = new Model(16, 2, 1, ModelYapisi.harita2);
    Model kirilabilirDuvar_patla32 = new Model(16, 3, 1, ModelYapisi.harita2);

    Model ust_sinir2 = new Model(16, 0, 2, ModelYapisi.harita2);
    Model sol_sinir2 = new Model(16, 1, 2, ModelYapisi.harita2);
    Model sag_sinir2 = new Model(16, 2, 2, ModelYapisi.harita2);
    Model alt_sinir2 = new Model(16, 3, 2, ModelYapisi.harita2);

    Model sol_ust_sinir2 = new Model(16, 0, 3, ModelYapisi.harita2);
    Model sag_ust_sinir2 = new Model(16, 1, 3, ModelYapisi.harita2);
    Model sag_alt_sinir2 = new Model(16, 2, 3, ModelYapisi.harita2);
    Model sol_alt_sinir2 = new Model(16, 3, 3, ModelYapisi.harita2);

    Model cikisKapisi3 = new Model(16, 0, 0, ModelYapisi.harita3);
    Model saglamDuvar3 = new Model(16, 1, 0, ModelYapisi.harita3);
    Model koridor3 = new Model(16, 2, 0, ModelYapisi.harita3);

    Model kirilabilirDuvar3 = new Model(16, 0, 1, ModelYapisi.harita3);
    Model kirilabilirDuvar_patla13 = new Model(16, 1, 1, ModelYapisi.harita3);
    Model kirilabilirDuvar_patla23 = new Model(16, 2, 1, ModelYapisi.harita3);
    Model kirilabilirDuvar_patla33 = new Model(16, 3, 1, ModelYapisi.harita3);

    Model ust_sinir3 = new Model(16, 0, 2, ModelYapisi.harita3);
    Model sol_sinir3 = new Model(16, 1, 2, ModelYapisi.harita3);
    Model sag_sinir3 = new Model(16, 2, 2, ModelYapisi.harita3);
    Model alt_sinir3 = new Model(16, 3, 2, ModelYapisi.harita3);

    Model sol_ust_sinir3 = new Model(16, 0, 3, ModelYapisi.harita3);
    Model sag_ust_sinir3 = new Model(16, 1, 3, ModelYapisi.harita3);
    Model sag_alt_sinir3 = new Model(16, 2, 3, ModelYapisi.harita3);
    Model sol_alt_sinir3 = new Model(16, 3, 3, ModelYapisi.harita3);

    Model cikisKapisi4 = new Model(16, 0, 0, ModelYapisi.harita4);
    Model saglamDuvar4 = new Model(16, 1, 0, ModelYapisi.harita4);
    Model koridor4 = new Model(16, 2, 0, ModelYapisi.harita4);

    Model kirilabilirDuvar4 = new Model(16, 0, 1, ModelYapisi.harita4);
    Model kirilabilirDuvar_patla14 = new Model(16, 1, 1, ModelYapisi.harita4);
    Model kirilabilirDuvar_patla24 = new Model(16, 2, 1, ModelYapisi.harita4);
    Model kirilabilirDuvar_patla34 = new Model(16, 3, 1, ModelYapisi.harita4);

    Model ust_sinir4 = new Model(16, 0, 2, ModelYapisi.harita4);
    Model sol_sinir4 = new Model(16, 1, 2, ModelYapisi.harita4);
    Model sag_sinir4 = new Model(16, 2, 2, ModelYapisi.harita4);
    Model alt_sinir4 = new Model(16, 3, 2, ModelYapisi.harita4);

    Model sol_ust_sinir4 = new Model(16, 0, 3, ModelYapisi.harita4);
    Model sag_ust_sinir4 = new Model(16, 1, 3, ModelYapisi.harita4);
    Model sag_alt_sinir4 = new Model(16, 2, 3, ModelYapisi.harita4);
    Model sol_alt_sinir4 = new Model(16, 3, 3, ModelYapisi.harita4);

    ImageIcon barIkon = new ImageIcon((new ImageIcon("res/model/merkez-bar.png")).getImage().getScaledInstance(80, 30, Image.SCALE_DEFAULT));
    ImageIcon ayarlarIkon = new ImageIcon((new ImageIcon("res/model/ayarlar.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon sesIkon = new ImageIcon((new ImageIcon("res/model/sesli.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon sessizIkon = new ImageIcon((new ImageIcon("res/model/sessiz.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon sifirlaIkon = new ImageIcon((new ImageIcon("res/model/sifirla.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
}
