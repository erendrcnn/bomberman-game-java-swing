package bombergame.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Klavye implements KeyListener {

    private final boolean[] tuslar = new boolean[160]; // Klavye tuslarini tutmak icin kullanilir.
    public boolean yukariHareket, asagiHareket, solHareket, sagHareket, bomba, kumanda, mola;

    public void update() {
        yukariHareket = tuslar[KeyEvent.VK_UP] || tuslar[KeyEvent.VK_W];
        asagiHareket = tuslar[KeyEvent.VK_DOWN] || tuslar[KeyEvent.VK_S];
        solHareket = tuslar[KeyEvent.VK_LEFT] || tuslar[KeyEvent.VK_A];
        sagHareket = tuslar[KeyEvent.VK_RIGHT] || tuslar[KeyEvent.VK_D];
        bomba = tuslar[KeyEvent.VK_SPACE] || tuslar[KeyEvent.VK_Z];
        kumanda = tuslar[KeyEvent.VK_B];
        mola = tuslar[KeyEvent.VK_ESCAPE];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            tuslar[e.getKeyCode()] = true;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("[Hata] Bilinmeyen Klavye Tusu : " + ex);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            tuslar[e.getKeyCode()] = false;
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("[Hata] Bilinmeyen Klavye Tusu : " + ex);
        }
    }
}
