package bombergame;

import bombergame.medya.Model;
import bombergame.medya.ModelYapisi;
import bombergame.medya.Muzik;

import javax.swing.*;
import java.awt.*;

public interface SabitDegiskenler {
    int HARITA_GENISLIK = 33;
    int HARITA_YUKSEKLIK = 13;
    int[][] haritaMatrix = new int[HARITA_YUKSEKLIK][HARITA_GENISLIK]; // map size

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

    Model cikisKapisi = new Model(16, 0, 0, ModelYapisi.harita, 14, 14);
    Model saglamDuvar = new Model(16, 1, 0, ModelYapisi.harita, 16, 16);
    Model koridor = new Model(16, 2, 0, ModelYapisi.harita, 16, 16);

    Model kirilabilirDuvar = new Model(16, 0, 1, ModelYapisi.harita, 16, 16);
    Model kirilabilirDuvar_patla1 = new Model(16, 1, 1, ModelYapisi.harita, 16, 16);
    Model kirilabilirDuvar_patla2 = new Model(16, 2, 1, ModelYapisi.harita, 16, 16);
    Model kirilabilirDuvar_patla3 = new Model(16, 3, 1, ModelYapisi.harita, 16, 16);

    Model ust_sinir = new Model(16, 0, 2, ModelYapisi.harita, 16, 16);
    Model sol_sinir = new Model(16, 1, 2, ModelYapisi.harita, 16, 16);
    Model sag_sinir = new Model(16, 2, 2, ModelYapisi.harita, 16, 16);
    Model alt_sinir = new Model(16, 3, 2, ModelYapisi.harita, 16, 16);

    Model sol_ust_sinir = new Model(16, 0, 3, ModelYapisi.harita, 16, 16);
    Model sag_ust_sinir = new Model(16, 1, 3, ModelYapisi.harita, 16, 16);
    Model sag_alt_sinir = new Model(16, 2, 3, ModelYapisi.harita, 16, 16);
    Model sol_alt_sinir = new Model(16, 3, 3, ModelYapisi.harita, 16, 16);

    ImageIcon barIkon = new ImageIcon((new ImageIcon("res/model/merkez-bar.png")).getImage().getScaledInstance(80, 30, Image.SCALE_DEFAULT));
    ImageIcon ayarlarIkon = new ImageIcon((new ImageIcon("res/model/ayarlar.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon sesIkon = new ImageIcon((new ImageIcon("res/model/sesli.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon sessizIkon = new ImageIcon((new ImageIcon("res/model/sessiz.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    ImageIcon sifirlaIkon = new ImageIcon((new ImageIcon("res/model/sifirla.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

    String kayitDosyasi = "res/veri/kayit.txt";
    String skorDosyasi = "res/veri/MaxSkor.txt";
    String fontDosyasi = "res/font/VBRUSHTB.ttf";
    String ayarlarPanel = "res/model/ayarlar-tablo.png";
    String arkaPlanPanel = "res/model/anamenu.png";
    String yeniOyunPanel = "res/model/yeni-oyun.png";
    String skorPanel = "res/model/skor-tablo.png";

    Color arkaPlanRenk = new Color(0, 173, 57, 255);
}