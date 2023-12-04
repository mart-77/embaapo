package viewmodel;

  public class Seller {
    private int id_seller; 
    private String nombre;
    private String oficio;

    public Seller(int id_seller, String nombre, String oficio) {
        this.id_seller = id_seller;
        this.nombre = nombre;
        this.oficio = oficio;
    }

    public int getId_seller() {
        return id_seller;
    }

    public String getNombre() {
        return nombre;
    }

    public String getOficio() {
        return oficio;
    }
}

