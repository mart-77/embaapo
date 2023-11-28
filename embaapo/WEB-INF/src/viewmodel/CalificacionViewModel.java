package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalificacionViewModel {

    // Configuración de conexión a la base de datos (reemplaza con tus propias credenciales y URL)
    private String url = "jdbc:postgresql://localhost:5432/tp";
    private String usuario = "postgres";
    private String contraseña = "0077";

    // Método para obtener nombres de sellers desde la base de datos
    public List<String> obtenerNombresSellers() {
        List<String> nombres = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            String sql = "SELECT nombre FROM seller";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        nombres.add(resultSet.getString("nombre"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombres;
    }

    // Método para guardar una calificación en la base de datos
    public void guardarCalificacion(String nombreSeller, String descripcion, int puntuacion) {
        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Obtener el ID del seller
            int idSeller = obtenerIdSellerPorNombre(nombreSeller);

            // Insertar la calificación en la tabla calificacion
            String sql = "INSERT INTO calificacion (id_seller, descripcion, puntuacion) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idSeller);
                preparedStatement.setString(2, descripcion);
                preparedStatement.setInt(3, puntuacion);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener el ID de un seller por nombre
    private int obtenerIdSellerPorNombre(String nombre) {
        int idSeller = -1; // Valor predeterminado si no se encuentra el seller

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            String sql = "SELECT id_seller FROM sellers WHERE nombre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nombre);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        idSeller = resultSet.getInt("id_seller");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return idSeller;
    }
}
