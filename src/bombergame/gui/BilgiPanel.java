package bombergame.gui;

import bombergame.Oyun;
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
    private JLabel sesEtiket;
    private JLabel ayarlarEtiket;
    private JLabel sifirlaEtiket;

    private JButton ayarlarButon;
    private JButton sesButon;
    private JButton sifirlaButon;

    private ImageIcon barIkon = new ImageIcon((new ImageIcon("res/model/merkez-bar.png")).getImage().getScaledInstance(80, 30, Image.SCALE_DEFAULT));
    private ImageIcon ayarlarIkon = new ImageIcon((new ImageIcon("res/model/ayarlar.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon sesIkon = new ImageIcon((new ImageIcon("res/model/sesli.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon sessizIkon = new ImageIcon((new ImageIcon("res/model/sessiz.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    private ImageIcon sifirlaIkon = new ImageIcon((new ImageIcon("res/model/sifirla.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

    public BilgiPanel(Oyun oyun) {
        setLayout(new GridLayout(1, 8));

        bosEtiket1 = new JLabel();
        bosEtiket2 = new JLabel();

        zamanEtiket = new JLabel();
        zamanEtiket.setIcon(barIkon);
        zamanEtiket.setText("Süre: " + oyun.getOyunTahtasi().getZaman());
        zamanEtiket.setForeground(Color.white);
        zamanEtiket.setHorizontalAlignment(JLabel.RIGHT);
        zamanEtiket.setHorizontalTextPosition(JLabel.CENTER);

        puanEtiket = new JLabel();
        puanEtiket.setIcon(barIkon);
        puanEtiket.setText("Puan: " + oyun.getOyunTahtasi().getPuanlar());
        puanEtiket.setForeground(Color.white);
        puanEtiket.setHorizontalAlignment(JLabel.CENTER);
        puanEtiket.setHorizontalTextPosition(JLabel.CENTER);

        sesEtiket = new JLabel();
        sesEtiket.setLayout(new BorderLayout());

        sesButon = new JButton();
        sesButon.setIcon(sesIkon);
        sesButon.setBackground(arkaPlanRenk);
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
        ayarlarButon.setBackground(arkaPlanRenk);
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
        sifirlaButon.setBackground(arkaPlanRenk);
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
        add(bosEtiket2);
        add(sesEtiket);
        add(ayarlarEtiket);

        setBackground(arkaPlanRenk);
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