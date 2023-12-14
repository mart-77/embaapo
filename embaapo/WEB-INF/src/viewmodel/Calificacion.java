package viewmodel;

public class Calificacion {
    private int id_calificacion;
    private int id_seller;
    private String descripcion;

     public Calificacion(int id_calificacion, int id_seller, String descripcion) {
        this.id_calificacion = id_calificacion;
        this.id_seller = id_seller;
        this.descripcion = descripcion;
    }
    public int getId_calificacion() {
        return id_calificacion;
    }

    public int getId_seller() {
        return id_seller;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
