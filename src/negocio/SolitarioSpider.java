package negocio;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ariel Daza
 * @version 1.0
 * @created 26-Nov.-2016 08:52:40 a.m.
 */
public class SolitarioSpider {

    public static final int INDEX_MAZO_BARAJA = 0;
    public static final int INDEX_MAZO_UNO = 1;
    public static final int INDEX_MAZO_DOS = 2;
    public static final int INDEX_MAZO_TRES = 3;
    public static final int INDEX_MAZO_CUATRO = 4;
    public static final int INDEX_MAZO_CINCO = 5;
    public static final int INDEX_MAZO_SEIS = 6;
    public static final int INDEX_MAZO_SIETE = 7;
    public static final int INDEX_MAZO_OCHO = 8;
    public static final int INDEX_MAZO_NUEVE = 9;
    public static final int INDEX_MAZO_DIEZ = 10;
    public static final int NRO_MAZOS = 11;

    private Mazo temp;
    private int puntuacion;
    private int indexTemp;
    public ArrayList<Mazo> mazos;

    public SolitarioSpider() {
        int count = 0;
        int[] valores = new int[104];
        for (int i = 0; i < 104; i++) {
            count++;
            count = (count < 14) ? count : 1;
            valores[i] = count;
        }

        shuffleArray(valores);

        mazos = new ArrayList<>();

        for (int i = 0; i < NRO_MAZOS; i++) {
            mazos.add(new Mazo());
        }

        Mazo baraja = mazos.get(INDEX_MAZO_BARAJA);

        for (int i = 0; i < 104; i++) {
            Carta carta = new Carta(valores[i], Carta.TIPO_ESPADA);
            baraja.addCarta(carta);
        }

        init();
//        for (int i = 0; i < 11; i++) {
//            System.out.println(mazos.get(i).toString());
//
//        }
    }

    public void clearTemporal() {
        this.temp = null;
        this.indexTemp = 0;
    }

    public void init() {
        for (int i = 1; i < 6; i++) {
            if (i == 4) {
                distribuirCartas(INDEX_MAZO_UNO, INDEX_MAZO_CUATRO, false);
                distribuirCartas(INDEX_MAZO_CINCO, INDEX_MAZO_DIEZ, true);
            } else if (i == 5) {
                distribuirCartas(INDEX_MAZO_UNO, INDEX_MAZO_CUATRO, true);
            } else {
                distribuirCartas(INDEX_MAZO_UNO, INDEX_MAZO_DIEZ, false);
            }

        }
    }

    public void repartirCartas() {
        distribuirCartas(INDEX_MAZO_UNO, INDEX_MAZO_DIEZ, true);
    }

    /**
     *
     * @param mazoInicial
     * @param mazoFinal
     * @param visible
     */
    public void distribuirCartas(int mazoInicial, int mazoFinal, boolean visible) {
        Mazo baraja = mazos.get(INDEX_MAZO_BARAJA);
        if (!baraja.vacio()) {
            for (int i = mazoInicial; i <= mazoFinal; i++) {
                Carta carta = baraja.cortarTop();
                if (carta != null) {
                    if (visible) {
                        carta.voltear();
                    }
                    Mazo mazo = mazos.get(i);
                    mazo.addCarta(carta);
                }
            }
        }
    }

    /**
     *
     * @param posicion
     * @param nroMazo
     */
    public void cortarMazo(int posicion, int nroMazo) {
        Mazo mazo = mazos.get(nroMazo);
        if (mazo.validarCorte(posicion)) {
            this.temp = mazo.cortar(posicion);
            this.indexTemp = nroMazo;
            System.out.println("corte: "+temp.toString());
        }
    }

    /**
     *
     * @param nroMazo
     */
    public void mover(int mazoFinal) {
        System.out.println("mover");
        System.out.println("index temp: " + indexTemp);
        System.out.println("index destino: " + mazoFinal);
        if (mazoFinal == indexTemp) {//caso 1 mover al mismo lugar
            mazos.get(indexTemp).addCorte(temp);
        } else {
            if (temp != null) {
                Mazo aux = mazos.get(mazoFinal);
                System.out.println("temp :" + temp.toString());
                System.out.println("destino :" + aux.toString());
                if (aux.validarAddCorte(temp)) {
                    mazos.get(mazoFinal).addCorte(temp);
                    mazos.get(indexTemp).voltearTop();
                } else {
                    System.out.println("mover no valido");
                    mazos.get(indexTemp).addCorte(temp);
                }
            }
        }
        clearTemporal();
    }

    public ArrayList<Mazo> getMazos() {
        return mazos;
    }

    public Mazo getTemp() {
        return temp;
    }

    private void shuffleArray(int[] cartas) {

        Random rnd = ThreadLocalRandom.current();
        for (int i = cartas.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = cartas[index];
            cartas[index] = cartas[i];
            cartas[i] = a;
        }
    }

    public static void main(String[] args) {
        SolitarioSpider solitarioSpider = new SolitarioSpider();
    }

    public Mazo getMazo(int nroMazo) {
        return mazos.get(nroMazo);
    }

    public void previewMover() {
        mazos.get(indexTemp).addCorte(temp);
        clearTemporal();
    }

}
