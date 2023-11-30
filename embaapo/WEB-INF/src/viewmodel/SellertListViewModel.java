package viewmodel;

import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellertListViewModel {
    private conexion connect;

    private List<Seller> sellers;

    @Init
    public void initSeller() {
        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    public void eliminarSeller(int sellerId) {
        // Lógica para eliminar el seller con el sellerId proporcionado
        if (eliminarSellerEnBaseDeDatos(sellerId)) {
            // Vuelve a cargar los sellers después de eliminar uno
            sellers = cargarSellersDesdeBaseDeDatos();
        }
    }

    public List<Seller> getSellers() {
        return sellers;
    }

    public void setSellers(List<Seller> sellers) {
        this.sellers = sellers;
    }

    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
    }
   // Método para cargar los sellers desde la base de datos
   private List<Seller> cargarSellersDesdeBaseDeDatos() {
    List<Seller> sellers = new ArrayList<>();
    try (Connection connection = obtenerConexion()) {
            String sql = "SELECT id, nombre, oficio FROM vendedor";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nombre = resultSet.getString("nombre");
                        String oficio = resultSet.getString("oficio");

                        sellers.add(new Seller(id, nombre,oficio));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }

    return sellers;
}
    // Método para eliminar un seller en la base de datos
    private boolean eliminarSellerEnBaseDeDatos(int sellerId) {
        try (Connection connection = obtenerConexion()) {
            String sql = "DELETE FROM seller WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, sellerId);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
            return false;
        }
    }

}
