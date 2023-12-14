package viewmodel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SellerDAO {
        public List<Seller> obtenerSellersDesdeBD() {
        List<Seller> sellers = new ArrayList<>();

        // Configurar la conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "martin";
        String contraseña = "1234";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT nombre, cedula, oficio FROM seller")) {

            while (resultSet.next()) {
                // Crear instancias de Servicio con datos de la base de datos
                String nombre = resultSet.getString("nombre");
                int cedula = resultSet.getInt("cedula");
                String oficio = resultSet.getString("oficio");

                sellers.add(new Seller(nombre,oficio, cedula));
            }

        } catch (SQLException e) {
            // Manejar la excepción de manera significativa, como lanzar una excepción personalizada o mostrar un mensaje de error
            e.printStackTrace();
        }

        return sellers;
    }
}