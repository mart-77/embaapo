package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

public class DeleteAnuncioSeller {
      private conexion connect;

    private int id_servicio;
    private String errorMessage;

    @Init
    public void initCalificar() {
        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    @NotifyChange({"errorMessage"})
    public void eliminarAnuncioSeller() {
        if (eliminarAnuncioSeller(id_servicio)) {
                        Executions.sendRedirect("ServicioList.zul");

            // Eliminación exitosa, puedes hacer algo después de eliminar si es necesario
            errorMessage = "Seller correctamente eliminado";
        } else {
            errorMessage = "Error al eliminar el Seller. Verifica el ID ingresado.";
        }
    }

    private boolean eliminarAnuncioSeller(int id_servicio) {
        try (Connection connection = obtenerConexion()) {
            String sql = "DELETE FROM seller_anuncio WHERE id_servicio = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_servicio);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
            return false;
        }
    }
    public static void main(String[] args) {
        DeleteAnuncioSeller prueba = new DeleteAnuncioSeller();
        prueba.testEliminarAnuncioSeller();
    }

    public void testEliminarAnuncioSeller() {
        // ID del servicio que deseas eliminar (ajústalo según tus necesidades)
        int idServicioAEliminar = 9;

        // Llamar a la función eliminarAnuncioSeller
        boolean eliminacionExitosa = eliminarAnuncioSeller(idServicioAEliminar);

        // Verificar el resultado
        if (eliminacionExitosa) {
            System.out.println("Eliminación exitosa del servicio con ID: " + idServicioAEliminar);
        } else {
            System.out.println("Error al intentar eliminar el servicio con ID: " + idServicioAEliminar);
        }
    }

    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin", "1234");
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
