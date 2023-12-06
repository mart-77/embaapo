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
        if (eliminarSellerEnBaseDeDatos(id_calificacion)) {
            // Eliminación exitosa, puedes hacer algo después de eliminar si es necesario
        } else {
            errorMessage = "Error al eliminar la calificacion. Verifica el ID ingresado.";
        }
    }

    private static boolean eliminarSellerEnBaseDeDatos(int id_calificacion) {
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

    private static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
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
