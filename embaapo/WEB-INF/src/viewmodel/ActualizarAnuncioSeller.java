package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.zkoss.bind.annotation.Init;

public class ActualizarAnuncioSeller {
     private String errorMessage;
    private String titulo;
    private String descripcion;
    private int tarifa;
    private int id_servicio;
    private Instant fecha_mod = Instant.now();
    private conexion connect;

    @Init
    public void initRegistro() {
        connect = new conexion();
        connect.crearConexion();
    }
public static void main(String[] args) {
        // Crear una instancia de conexión
        conexion connect = new conexion();

        // Datos de prueba para la actualización
        int id_servicio = 1; // Reemplaza con el ID correcto
        String titulo = "Nuevo Título";
        String descripcion = "Nueva Descripción";
        int tarifa = 150;
        Instant fecha_mod = Instant.now(); // Reemplaza con la fecha correcta
        Timestamp timestampMod = Timestamp.from(fecha_mod);

        // Llamar a la función de actualización
        boolean actualizacionExitosa = connect.actualizarAnuncioSeller(id_servicio, titulo, descripcion, tarifa,Instant.now());

        // Imprimir el resultado
        if (actualizacionExitosa) {
            System.out.println("Actualización exitosa en la base de datos.");
        } else {
            System.out.println("Error al intentar actualizar en la base de datos.");
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }
    public String getTitulo() {
        return titulo;
    }

    public int getId_servicio() {
        return id_servicio;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public int getTarifa() {
        return tarifa;
    }

    public Instant getFecha_mod() {
        return fecha_mod;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public void setFecha_mod(Instant fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    
}
