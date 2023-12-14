package viewmodel;

public class Servicio {
    private int id_servicio;
    private int id_seller;
    private String titulo;
    private int tarifa;
    public Servicio(int id_servicio,int id_seller, String titulo, int tarifa) {
        this.id_servicio = id_servicio;
        this.id_seller = id_seller;
        this.titulo = titulo;
        this.tarifa = tarifa;
    }
    public int getId_servicio() {
        return id_servicio;
    }
    public int getId_seller() {
        return id_seller;
    }
    public String getTitulo() {
        return titulo;
    }
    public int getTarifa() {
        return tarifa;
    }
    

}
