package bombergame.gui;

import bombergame.Oyun;
import bombergame.OyunTahtasi;
import bombergame.SabitDegiskenler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BilgiPanel extends JPanel implements SabitDegiskenler {
    private JLabel bosEtiket1;
    private JLabel bosEtiket2;

    private JLabel zamanEtiket;
    private JLabel puanEtiket;
    private JLabel seviyeEtiket;
    private JLabel canEtiket;
    private JLabel sesEtiket;
    private JLabel ayarlarEtiket;
    private JLabel sifirlaEtiket;

    private JButton ayarlarButon;
    private JButton sesButon;
    private JButton sifirlaButon;

    public BilgiPanel(Oyun oyun) {
        setLayout(new GridLayout(1, 8));

        bosEtiket1 = new JLabel();
        bosEtiket2 = new JLabel();

        zamanEtiket = new JLabel();
        zamanEtiket.setIcon(barIkon);
        zamanEtiket.setText("Süre: " + oyun.getOyunTahtasi().getZaman());
        zamanEtiket.setForeground(Color.white);
        zamanEtiket.setHorizontalAlignment(JLabel.CENTER);
        zamanEtiket.setHorizontalTextPosition(JLabel.CENTER);

        puanEtiket = new JLabel();
        puanEtiket.setIcon(barIkon);
        puanEtiket.setText("Puan: " + oyun.getOyunTahtasi().getPuanlar());
        puanEtiket.setForeground(Color.white);
        puanEtiket.setHorizontalAlignment(JLabel.CENTER);
        puanEtiket.setHorizontalTextPosition(JLabel.CENTER);

        seviyeEtiket = new JLabel();
        seviyeEtiket.setIcon(barIkon);
        seviyeEtiket.setText("Seviye: " + oyun.getOyunTahtasi().getSeviye());
        seviyeEtiket.setForeground(Color.white);
        seviyeEtiket.setHorizontalAlignment(JLabel.CENTER);
        seviyeEtiket.setHorizontalTextPosition(JLabel.CENTER);

        canEtiket = new JLabel();
        canEtiket.setIcon(barIkon);
        canEtiket.setText("Can: " + oyun.getOyunTahtasi().getCan());
        canEtiket.setForeground(Color.white);
        canEtiket.setHorizontalAlignment(JLabel.CENTER);
        canEtiket.setHorizontalTextPosition(JLabel.CENTER);

        sesEtiket = new JLabel();
        sesEtiket.setLayout(new BorderLayout());

        sesButon = new JButton();
        sesButon.setIcon(sesIkon);

        switch (OyunTahtasi.getSeciliTema()) {
            case 2:
                sesButon.setBackground(arkaPlanRenk2);
                break;
            case 3:
                sesButon.setBackground(arkaPlanRenk3);
                break;
            case 4:
                sesButon.setBackground(arkaPlanRenk4);
                break;
            default:
                sesButon.setBackground(arkaPlanRenk);
        }
        sesButon.setBorder(BorderFactory.createEmptyBorder());
        sesButon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sesButon.setModel(new SabitButon());
        sesButon.setFocusPainted(false);
        sesButon.setPreferredSize(new Dimension(30, 30));
        sesButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (sesButon.getIcon().equals(sesIkon)) {
                    sesKapat();
                    sesButon.setIcon(sessizIkon);
                } else {
                    sesAc();
                    sesButon.setIcon(sesIkon);
                }

            }
        });
        sesEtiket.add(sesButon, BorderLayout.EAST);

        ayarlarEtiket = new JLabel();
        ayarlarEtiket.setLayout(new BorderLayout());

        ayarlarButon = new JButton();

        switch (OyunTahtasi.getSeciliTema()) {
            case 2:
                ayarlarButon.setBackground(arkaPlanRenk2);
                break;
            case 3:
                ayarlarButon.setBackground(arkaPlanRenk3);
                break;
            case 4:
                ayarlarButon.setBackground(arkaPlanRenk4);
                break;
            default:
                ayarlarButon.setBackground(arkaPlanRenk);
        }
        ayarlarButon.setBorder(BorderFactory.createEmptyBorder());
        ayarlarButon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ayarlarButon.setIcon(ayarlarIkon);
        ayarlarButon.setModel(new SabitButon());
        ayarlarButon.setFocusPainted(false);
        ayarlarButon.setPreferredSize(new Dimension(35, 35));
        ayarlarButon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!oyun.oyunDurduMu()) {
                    oyun.getOyunTahtasi().oyunDuraklatAyarlar();
                }
            }
        });
        ayarlarEtiket.add(ayarlarButon, BorderLayout.WEST);

        sifirlaEtiket = new JLabel();
        sifirlaEtiket.setLayout(new BorderLayout());

        sifirlaButon = new JButton();

        switch (OyunTahtasi.getSeciliTema()) {
            case 2:
                sifirlaButon.setBackground(arkaPlanRenk2);
                break;
            case 3:
                sifirlaButon.setBackground(arkaPlanRenk3);
                break;
            case 4:
                sifirlaButon.setBackground(arkaPlanRenk4);
                break;
            default:
                sifirlaButon.setBackground(arkaPlanRenk);
                break;
        }
        sifirlaButon.setBorder(BorderFactory.createEmptyBorder());
        sifirlaButon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sifirlaButon.setIcon(sifirlaIkon);
        sifirlaButon.setModel(new SabitButon());
        sifirlaButon.setFocusPainted(false);
        sifirlaButon.setPreferredSize(new Dimension(35, 35));
        sifirlaButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oyun.getOyunTahtasi().oyunDuraklatSifirla();
            }
        });
        sifirlaEtiket.add(sifirlaButon, BorderLayout.CENTER);

        add(sifirlaEtiket);
        add(bosEtiket1);
        add(zamanEtiket);
        add(puanEtiket);
        add(seviyeEtiket);
        add(canEtiket);
        add(bosEtiket2);
        add(sesEtiket);
        add(ayarlarEtiket);

        switch (OyunTahtasi.getSeciliTema()) {
            case 2:
                setBackground(arkaPlanRenk2);
                break;
            case 3:
                setBackground(arkaPlanRenk3);
                break;
            case 4:
                setBackground(arkaPlanRenk4);
                break;
            default:
                setBackground(arkaPlanRenk);
        }
        setPreferredSize(new Dimension(0, 40));
    }

    private void sesKapat() {
        oyunMuzik.muzikDurdur();
        bombaYerlestirMuzik.muzikDurdur();
        bombaPatlamaMuzik.muzikDurdur();
        olumMuzik.muzikDurdur();
        esyaAlmaMuzik.muzikDurdur();
        duvarKirmaMuzik.muzikDurdur();
    }

    private void sesAc() {
        oyunMuzik.setKapali(false);
        bombaYerlestirMuzik.setKapali(false);
        bombaPatlamaMuzik.setKapali(false);
        olumMuzik.setKapali(false);
        esyaAlmaMuzik.setKapali(false);
        duvarKirmaMuzik.setKapali(false);
        oyunMuzik.muzikCal(100);
    }

    public void setZaman(int t) {
        this.zamanEtiket.setText("Süre: " + t);
    }

    public void setPuan(int t) {
        this.puanEtiket.setText("Puan: " + t);
    }

    public void setSeviye(int t) {
        this.seviyeEtiket.setText("Seviye: " + t);
    }

    public void setCan(int t) {
        this.canEtiket.setText("Can: " + t);
    }

    public void arkaplanDegistir(Color c) {
        setBackground(c);
        sesButon.setBackground(c);
        ayarlarButon.setBackground(c);
        sifirlaButon.setBackground(c);
    }
}

class SabitButon extends DefaultButtonModel {
    SabitButon() {
    }

    public boolean isPressed() {
        return false;
    }

    public boolean isRollover() {
        return false;
    }

    public void setRollover(boolean b) {
    }
}