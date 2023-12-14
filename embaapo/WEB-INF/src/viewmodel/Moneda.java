package viewmodel;

public class Moneda {
    private int id_diviza;
    private String nombre;
    private String simbolo;

    public Moneda(int id_diviza, String nombre, String simbolo) {
        this.id_diviza = id_diviza;
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    public int getId_diviza() {
        return id_diviza;
    }

    public String getNombre() {
        return nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }
    
}
