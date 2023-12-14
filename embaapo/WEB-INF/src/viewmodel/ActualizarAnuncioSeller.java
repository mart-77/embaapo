package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

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
     @Command
    @NotifyChange({"errorMessage"})
    public void actualizarAnuncioSeller() {
        if (connect.actualizarAnuncioSeller(id_servicio, titulo, descripcion, tarifa,Instant.now())) {
            // Eliminación exitosa, puedes hacer algo después de eliminar si es necesario
                        Executions.sendRedirect("ServicioList.zul");

            errorMessage = "Seller correctamente eliminado";
        } else {
            errorMessage = "Error al eliminar el Seller. Verifica el ID ingresado.";
        }
    }
    boolean actualizarAnuncioSeller(int id_servicio, String titulo, String descripcion, int tarifa, Instant instant) {
        System.out.println("Datos ingresados:");
        System.out.println("titulo: " + titulo);
        System.out.println("descripcion: " + descripcion);
        System.out.println("tarifa: " + tarifa);
        System.out.println("Fecha de Insersión: " + fecha_mod);
    
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin", "1234");
            Timestamp timestampMod = Timestamp.from(fecha_mod);
    
            // Convertir java.util.Date a java.sql.Date
    
            // Consulta para insertar el nuevo usuario
            String consulta = "UPDATE seller_anuncio SET titulo = ?, descripcion = ?, tarifa = ?, fecha_mod = ? WHERE id_servicio = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setInt(1, id_servicio);
                preparedStatement.setString(2, titulo);
                preparedStatement.setString(3, descripcion);
                preparedStatement.setInt(4, tarifa);
                preparedStatement.setObject(5, timestampMod);
    
                // Ejecutar la inserción
                int filasAfectadas = preparedStatement.executeUpdate();
    
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
            return false;
        }
    }
public static void main(String[] args) {
        // Crear una instancia de conexión
        conexion connect = new conexion();

        // Datos de prueba para la actualización
        int id_servicio = 1; // Reemplaza con el ID correcto
        String titulo = "Progrmacion";
        String descripcion = "Se programan Apps";
        int tarifa = 200;
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
