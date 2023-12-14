package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    public List<Usuario> obtenerUsuariosDesdeBD() {
        List<Usuario> usuarios = new ArrayList<>();

        // Configurar la conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "postgres";
        String contraseña = "0077";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT nombre, apellido, telefono FROM usuario")) {

            while (resultSet.next()) {
                // Crear instancias de Servicio con datos de la base de datos
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int telefono = resultSet.getInt("telefono");

                usuarios.add(new Usuario(nombre, apellido, telefono));
            }

        } catch (SQLException e) {
            // Manejar la excepción de manera significativa, como lanzar una excepción
            // personalizada o mostrar un mensaje de error
            e.printStackTrace();
        }

        return usuarios;
    }
}
