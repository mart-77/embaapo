package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSellerViewModel {
    private conexion connect;

    private int id_seller;
    private String errorMessage;

    @Init
    public void initDelete() {
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

    private static boolean eliminarSellerEnBaseDeDatos(int id_seller) {
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

    private static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
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
    public static void main(String[] args) {
        // ID del seller que deseas eliminar
        int idSellerAEliminar = 6; // Reemplaza esto con el ID real

        // Llamar a la función para eliminar el seller
        boolean eliminado = eliminarSellerEnBaseDeDatos(idSellerAEliminar);

        // Verificar si el seller fue eliminado correctamente
        if (eliminado) {
            System.out.println("El Seller fue eliminado correctamente.");
        } else {
            System.out.println("Error al eliminar el Seller.");
        }
    }
}
