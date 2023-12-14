package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class CalificacionListViewModel {
    private conexion connect;
    private int id_calificacion;
    private String errorMessage;
    private List<Map<String, Object>> calificaciones;

    @Init
    public void initCalificar() {
        System.out.println("\n\n\n\n\n\n\nInit method called!");
        connect = new conexion();
        connect.crearConexion();
        calificaciones = connect.obtenerCalificaciones();

        // Verificar que la lista de usuarios se haya llenado correctamente
       // System.out.println("Total de usuarios en ViewModel: " + calificaciones.size());
    }
 @Command
    @NotifyChange({"errorMessage"})
    public void eliminarCalificacion() {
        if (eliminarCalificacionEnBaseDeDatos(id_calificacion)) {
            // Eliminación exitosa, puedes hacer algo después de eliminar si es necesario
        } else {
            errorMessage = "Error al eliminar la calificacion. Verifica el ID ingresado.";
        }
    }
    private static boolean eliminarCalificacionEnBaseDeDatos(int id_calificacion) {
        try (Connection connection = obtenerConexion()) {
            String sql = "DELETE FROM calificacion WHERE id_calificacion = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_calificacion);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
            return false;
        }
    }
    /* 
    public static void main(String[] args) {
        // Simular eliminación de calificación con un ID específico
        int id_calificacion = 1;

        boolean eliminacionExitosa = eliminarCalificacionEnBaseDeDatos(id_calificacion);

        // Imprimir el resultado de la eliminación
        if (eliminacionExitosa) {
            System.out.println("Calificación eliminada exitosamente.");
        } else {
            System.out.println("Error al eliminar la calificación.");
        }
    }
/* */
    private static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin", "1234");
    }

    public int getId_calificacion() {
        return id_calificacion;
    }

    public void setId_seller(int id_calificacion) {
        this.id_calificacion = id_calificacion;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public List<Map<String, Object>> getCalificaciones() {
        return calificaciones;
    }

    public void setCalifaciones(List<Map<String, Object>> calificaciones) {
        this.calificaciones = calificaciones;
    }
  
}
