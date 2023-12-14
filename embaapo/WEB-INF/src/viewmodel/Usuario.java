package viewmodel;

import java.sql.Date;

public class Usuario {
    private int id_usuario;
    private String nombre;
    private String apellido;
    private String email;
    private int telefono;
     public Usuario(int id_usuario, String nombre, String apellido, int telefono,  String email) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
    }

    public Usuario(String nombre, String apellido, int telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }
    

    public int getId_usuario() {
        return id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    
}
