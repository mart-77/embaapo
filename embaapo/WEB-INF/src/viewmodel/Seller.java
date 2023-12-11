package viewmodel;

import java.sql.Date;

public class Seller {
    private int id_seller;
    private String nombre;
    private String oficio;
    private int cedula;
    private Date fecha_nacimiento;
    private String direccion;

    public Seller(int id_seller, String nombre, String oficio, int cedula, Date fecha_nacimiento, String direccion) {
        this.id_seller = id_seller;
        this.nombre = nombre;
        this.oficio = oficio;
        this.cedula = cedula;
        this.fecha_nacimiento = fecha_nacimiento;
        this.direccion = direccion;
    }

    public Seller(String nombre, String oficio, int cedula) {
        this.nombre = nombre;
        this.oficio = oficio;
        this.cedula = cedula;
    }

    public Seller(String nombre, String oficio, int cedula, Object fecha_nacimiento, String direccion) {
        this.nombre = nombre;
        this.oficio = oficio;
        this.cedula = cedula;
        this.fecha_nacimiento = (Date) fecha_nacimiento;
        this.direccion = direccion;
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

    public int getCedula() {
        return cedula;
    }

    public Date getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

}