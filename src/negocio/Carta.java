package negocio.SolitarioSpider;

/**
 * @author Ariel Daza
 * @version 1.0
 * @created 26-Nov.-2016 08:52:39 a.m.
 */
public class Carta {

    private int valor;
    private boolean visible;
    private int tipo;

    public static final int TIPO_ESPADA = 0;
    public static final int TIPO_CORAZON = 1;
    public static final int TIPO_TREBOL = 2;
    public static final int TIPO_DIAMANTE = 3;

    public Carta(int valor, int tipo) {
        this.valor = valor;
        this.tipo = tipo;
        this.visible = false;
    }

    public int getValor() {
        return this.valor;
    }

    public int getTipo() {
        return this.tipo;
    }

    public boolean esVisible() {
        return visible;
    }

    public void voltear() {
        this.visible = true;
    }

    @Override
    public String toString() {
        return "Carta{" + valor + "," + visible + '}';
    }

}
