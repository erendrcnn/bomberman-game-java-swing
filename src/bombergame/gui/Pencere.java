package bombergame.gui;

import bombergame.Oyun;

import javax.swing.*;
import java.awt.*;

public class Pencere extends JFrame {

    public OyunPanel _oyunpanel;
    private JPanel _anapanel;
    private BilgiPanel _bilgipanel;
    private Oyun _oyun;

    public Pencere() {
        _anapanel = new JPanel(new BorderLayout());
        _oyunpanel = new OyunPanel(this);
        _bilgipanel = new BilgiPanel(_oyunpanel.getOyun());

        _anapanel.add(_bilgipanel, BorderLayout.NORTH);
        _anapanel.add(_oyunpanel, BorderLayout.CENTER);

        _oyun = _oyunpanel.getOyun();
        _bilgipanel.setVisible(false);

        add(_anapanel);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        _oyun.baslat();
    }

	/*
	>> ANLIK BILGILERIN SUREKLI GUNCELLENMESI ICIN GETTER VE SETTER METOTLARI
	 */

    public BilgiPanel getBilgiPanel() {
        return _bilgipanel;
    }

    public void setZaman(int time) {
        _bilgipanel.setZaman(time);
    }

    public void setPuan(int points) {
        _bilgipanel.setPuan(points);
    }

    public void setSeviye(int level) {
        _bilgipanel.setSeviye(level);
    }

    public void setCan(int health) {
        _bilgipanel.setCan(health);
    }
}