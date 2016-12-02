/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import negocio.Carta;
import negocio.SolitarioSpider;
import negocio.Mazo;

/**
 * @author Ariel Daza
 * @version 1.0
 * @created 27-Nov.-2016 10:43:35 a.m.
 */
public class Juego extends JPanel implements MouseListener, MouseMotionListener {

    public static final int CARD_WIDTH = 90;
    public static final int CARD_HEIGHT = 125;
    public static final int SPACES_CARD_NOVISIBLE = 15;
    public static final int SPACES_CARD_VISIBLE = 30;
    public static final int TABLERO_PADING = 10;
    public static final int BARAJA_POSX = 910;
    public static final int BARAJA_POSY = 550;
    public static final int MAZO_ESCALERA_POSX = 10;
    public static final int MAZO_ESCALERA_POSY = 550;

    private SolitarioSpider solitarioSpider;
    private ArrayList<Image> imagenes;
    private int tmpx;
    private int tmpy;
    private int mx;
    private int my;
    private Rectangle tmpBounds;
    private Rectangle mazoBounds;

    public Juego() {
        CargarImagenes();
        tmpBounds = new Rectangle();
        mazoBounds = new Rectangle();
        solitarioSpider = new SolitarioSpider();
        this.setBackground(Color.decode("0x005f00"));
        this.setBounds(0, 0, 1025, 730);
        this.setDoubleBuffered(true);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.repaint();
    }

    private void dibujarMazos(Graphics g) {
        int posx = TABLERO_PADING;
        int posy = TABLERO_PADING;

        ArrayList<Mazo> mazos = solitarioSpider.getMazos();
        for (int i = SolitarioSpider.INDEX_MAZO_UNO; i <= SolitarioSpider.INDEX_MAZO_DIEZ; i++) {
            Mazo mazo = mazos.get(i);
            if (mazo.vacio()) {
                dibujarMazoVacio(g, posx, posy);
            } else {
                dibujarMazo(g, mazos.get(i), posx, posy);
            }
            posx = posx + CARD_WIDTH + 10;
        }
    }

    private void dibujarMazo(Graphics g, Mazo mazo, int posx, int posy) {
        ArrayList<Carta> cartas = mazo.getCartas();
        if (cartas.isEmpty()) {
            dibujarMazoVacio(g, posx, posy);
        } else {
            for (Carta carta : cartas) {
                if (carta.esVisible()) {
                    g.drawImage(imagenes.get(carta.getValor()), posx, posy, this);
                    posy = posy + SPACES_CARD_VISIBLE;
                } else {
                    g.drawImage(imagenes.get(0), posx, posy, this);
                    posy = posy + SPACES_CARD_NOVISIBLE;
                }
            }
        }
    }

    private void dibujarMazoBaraja(Graphics g) {
        int posx = BARAJA_POSX;
        int posy = BARAJA_POSY;
        ArrayList<Mazo> mazos = solitarioSpider.getMazos();
        Mazo baraja = mazos.get(SolitarioSpider.INDEX_MAZO_BARAJA);
        if (baraja.vacio()) {
            dibujarMazoVacio(g, posx, posy);
        } else {
            int cant = baraja.size() / 10;
            for (int i = 0; i < cant; i++) {
                g.drawImage(imagenes.get(0), posx, posy, this);
                posx = posx - 15;
            }
        }
    }

    private void CargarImagenes() {
        imagenes = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            if (i == 0) {
                imagenes.add(new ImageIcon("cartas\\detailed_card_back.png").getImage());
            } else {
                imagenes.add(new ImageIcon("cartas\\s" + i + ".png").getImage());
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujarMazoBaraja(g);
        dibujarMazos(g);
        dibujarMazoEscalera(g);
        dibujarMazoTemporal(g);
    }

    private void dibujarMazoTemporal(Graphics g) {
        if (solitarioSpider.getTemp() != null) {
            dibujarMazo(g, solitarioSpider.getTemp(), tmpx, tmpy);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (solitarioSpider.getTemp() != null) {
            tmpx = e.getX() - mx;
            tmpy = e.getY() - my;
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int posy = BARAJA_POSY;
        ArrayList<Mazo> mazos = solitarioSpider.getMazos();
        Mazo baraja = mazos.get(SolitarioSpider.INDEX_MAZO_BARAJA);
        if (!baraja.vacio()) {
            int cantidad = baraja.size() / 10;
            int posx = BARAJA_POSX - ((cantidad - 1) * 15);
            if (e.getX() > posx && e.getX() < posx + CARD_WIDTH && e.getY() > posy && e.getY() < posy + CARD_HEIGHT) {
                solitarioSpider.repartirCartas();
                repaint();
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int posMazoX = TABLERO_PADING;
        for (int i = SolitarioSpider.INDEX_MAZO_UNO; i <= SolitarioSpider.INDEX_MAZO_DIEZ; i++) {
            Mazo mazo = solitarioSpider.getMazo(i);
            int posMazoY = TABLERO_PADING + (mazo.cantCartasNoVisibles() * SPACES_CARD_NOVISIBLE) + (mazo.cantCartasVisibles() * SPACES_CARD_VISIBLE);
//            System.out.println("nroMazo: "+i+" posy: " +posMazoY);
//            System.out.println("nroMazo: "+i+" visibles: " +mazo.cantCartasVisibles());
//            System.out.println("nroMazo: "+i+" No visibles: " +mazo.cantCartasNoVisibles());

            ArrayList<Carta> cartas = mazo.getCartas();
            for (int j = 1; j <= cartas.size(); j++) {
                Carta carta = cartas.get(cartas.size() - j);
                if (!carta.esVisible()) {
                    break;
                } else {
                    posMazoY = posMazoY - 30;
                    if (e.getX() > posMazoX && e.getX() < posMazoX + CARD_WIDTH && e.getY() > posMazoY && e.getY() < posMazoY + CARD_HEIGHT) {
                        mx = e.getX() - posMazoX;
                        my = e.getY() - posMazoY;
                        int nroMazo = i;
                        int posicionCarta = cartas.size() - j;
                        solitarioSpider.cortarMazo(posicionCarta, nroMazo);
                        return;
                    }
                }
            }
            posMazoX = posMazoX + TABLERO_PADING + CARD_WIDTH;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (solitarioSpider.getTemp() != null) {
            int posMazoX = TABLERO_PADING;
            for (int i = SolitarioSpider.INDEX_MAZO_UNO; i <= SolitarioSpider.INDEX_MAZO_DIEZ; i++) {
                Mazo mazo = solitarioSpider.getMazo(i);

                int posMazoY = TABLERO_PADING + (mazo.cantCartasNoVisibles() * SPACES_CARD_NOVISIBLE) + (mazo.cantCartasVisibles() * SPACES_CARD_VISIBLE);
                posMazoY = posMazoY - 30;
                if (e.getX() - mx > posMazoX && e.getX() - mx < posMazoX + CARD_WIDTH && e.getY() - my > posMazoY && e.getY() - my < posMazoY + CARD_HEIGHT) {
                    solitarioSpider.mover(i);
//                    if (juego.winGame()) {
//                        JOptionPane.showMessageDialog(null, "Ganaste el Juego", "Ganaste", JOptionPane.INFORMATION_MESSAGE);
//                    }
                    this.repaint();
                    return;
                }
                posMazoX = posMazoX + TABLERO_PADING + CARD_WIDTH;
            }
            solitarioSpider.previewMover();
            this.repaint();
        }

    }

    private void dibujarMazoVacio(Graphics g, int posx, int posy) {
        Graphics2D g2 = (Graphics2D) g;
        float dash[] = {4.0f};
        g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2.setPaint(Color.white);
        Rectangle r = new Rectangle(posx, posy, CARD_WIDTH - 1, CARD_HEIGHT - 1);
        g2.draw(r);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void dibujarMazoEscalera(Graphics g) {
        int posx = MAZO_ESCALERA_POSX;
        int posy = MAZO_ESCALERA_POSY;
//        for (int j = 0; j < juego.getMazosEscaleras(); j++) {
//
//            g.drawImage(imagenes.get(13), posx, posy, this);
//            posx = posx + 15;
//        }

    }
}
