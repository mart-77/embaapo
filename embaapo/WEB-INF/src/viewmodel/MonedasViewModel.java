package viewmodel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;

public class MonedasViewModel {

    private String nombre;
    private String simbolo;

    @Init
    public void init() {
        // Puedes realizar inicializaciones aquí si es necesario
    }

    @Command
    public void guardarMoneda() {
        // Establecer la conexión a la base de datos (reemplaza con tus propias credenciales y URL)
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "postgres";
        String contraseña = "0077";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Preparar la consulta SQL para insertar en la tabla divisas
            String sql = "INSERT INTO diviza (nombre, simbolo) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Establecer los parámetros en la consulta
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, simbolo);

                // Ejecutar la consulta de inserción
                preparedStatement.executeUpdate();

                // Mensaje de éxito
                System.out.println("Moneda guardada exitosamente en la tabla divisas.");
            }
        } catch (SQLException e) {
            // Manejar errores de conexión o consulta
            e.printStackTrace();
        }
    }

    // Getters y Setters para nombre y simbolo

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}
