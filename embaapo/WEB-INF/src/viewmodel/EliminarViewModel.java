package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EliminarViewModel {
    private conexion connect;

    private int id_seller;
    private String errorMessage;

    @Init
    public void initCalificar() {
        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    @NotifyChange({"errorMessage"})
    public void eliminarSeller() {
        if (eliminarSellerEnBaseDeDatos(id_seller)) {
            // Eliminación exitosa, puedes hacer algo después de eliminar si es necesario
            errorMessage = "Seller correctamente eliminado";
        } else {
            errorMessage = "Error al eliminar el Seller. Verifica el ID ingresado.";
        }
    }

    private boolean eliminarSellerEnBaseDeDatos(int id_seller) {
        try (Connection connection = obtenerConexion()) {
            String sql = "DELETE FROM seller WHERE id_seller = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_seller);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
            return false;
        }
    }

    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin", "1234");
    }

    public int getId_seller() {
        return id_seller;
    }

    public void setId_seller(int id_seller) {
        this.id_seller = id_seller;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
