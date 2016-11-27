package negocio;

import java.util.ArrayList;

/**
 * @author Ariel Daza
 * @version 1.0s
 * @created 26-Nov.-2016 08:52:40 a.m.
 */
public class Mazo {

    public ArrayList<Carta> cartas;

    public Mazo() {
        cartas = new ArrayList<>();
    }

    public boolean vacio() {
        return cartas.isEmpty();
    }

    /**
     *
     * @param carta
     */
    public void addCarta(Carta carta) {
        cartas.add(carta);
    }

    /**
     *
     * @param mazo
     */
    public void addCorte(Mazo mazo) {
        for (Carta carta : mazo.getCartas()) {
            this.addCarta(carta);
        }
    }

    /**
     *
     * @param posicionCarta
     * @return boolean
     */
    public boolean validarCorte(int posicionCarta) {
        Carta aux = cartas.get(posicionCarta);
        for (int i = posicionCarta + 1; i < cartas.size(); i++) {
            Carta carta = cartas.get(i);
            if (aux.getValor() != carta.getValor() + 1) {
                return false;
            }
            aux = carta;
        }
        return true;
    }

    /**
     *
     * @param posicionCarta
     * @return Mazo
     */
    public Mazo cortar(int posicionCarta) {
        Mazo corte = new Mazo();
        for (int i = posicionCarta; i < cartas.size(); i++) {
            corte.addCarta(cartas.remove(i));
        }
        return corte;
    }

    public ArrayList<Carta> getCartas() {
        return cartas;
    }

    public int cantCartasVisibles() {
        return this.cartas.size() - this.cantCartasNoVisibles();
    }

    public int cantCartasNoVisibles() {
        int cant = 0;
        for (Carta carta : cartas) {
            if (carta.esVisible()) {
                cant++;
            } else {
                return cant;
            }
        }
        return cant;
    }

    public boolean validarEscalera() {
        if (cartas.size() > 12) {
            Carta aux = cartas.get(cartas.size() - 1);
            int size = cartas.size();
            for (int i = 2; i < 14; i++) {
                int pos = size - i;
                Carta carta = cartas.get(pos);
                if (aux.getValor() + 1 != carta.getValor()) {
                    return false;
                }
                aux = carta;
            }
            return true;
        } else {
            return false;
        }
    }

    public void removerEscalera() {
        for (int i = 0; i < 13; i++) {
            cartas.remove(cartas.size() - 1);
        }
    }

    public Carta getBottom() {
        if (!this.vacio()) {
            return this.cartas.get(0);
        }
        return null;
    }

    public Carta getTop() {
        if (!this.vacio()) {
            return cartas.get(cartas.size());
        }
        return null;
    }
    
    public Carta cortarTop(){
        if (!this.vacio()) {
            return cartas.remove(cartas.size()-1);
        }
        return null;
    }

    public void voltearTop() {
        if (!this.vacio()) {
            this.getTop().voltear();
        }
    }

    public boolean validarAddCorte(Mazo mazo) {
        if (!this.vacio()) {
            if (this.getTop().getValor() != mazo.getBottom().getValor() + 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mazo{" + cartas + '}';
    }

}
