package bombergame.gui;

import bombergame.Oyun;

import javax.swing.*;
import java.awt.*;

public class OyunPanel extends JPanel {

    private Oyun _oyun;

    public OyunPanel(Pencere pencere) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Oyun.GENISLIK * Oyun.SCALE, Oyun.YUKSEKLIK * Oyun.SCALE));

        _oyun = new Oyun(pencere);
        add(_oyun);
        _oyun.setVisible(true);

        setVisible(true);
        setFocusable(true);
    }

    public void boyutDegistir() {
        setPreferredSize(new Dimension(Oyun.GENISLIK * Oyun.SCALE, Oyun.YUKSEKLIK * Oyun.SCALE));
        revalidate();
        repaint();
    }

    public Oyun getOyun() {
        return _oyun;
    }

}
