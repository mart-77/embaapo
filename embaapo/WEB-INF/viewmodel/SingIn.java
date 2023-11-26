package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroViewModel {

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String password;


    @Command
    public void registrar() {
        if (validarDatos()) {
            // Si los datos son válidos, registrar en la base de datos
            if (registrarEnBaseDeDatos()) {
                // Registro exitoso, redirigir a la página de inicio de sesión
                Executions.sendRedirect("/Login.zul");
            } else {
                // Error al registrar en la base de datos, manejar según sea necesario
            }
        }
    }

    private boolean validarDatos() {
        // Agrega aquí lógica de validación según tus necesidades
        return true; // Retornar true si los datos son válidos
    }

    private boolean registrarEnBaseDeDatos() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");

            // Consulta para insertar el nuevo usuario
            String consulta = "INSERT INTO usuario (nombre, apellido, email, telefono, password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, apellido);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, telefono);
                preparedStatement.setString(5, password);

                // Ejecutar la inserción
                int filasAfectadas = preparedStatement.executeUpdate();

                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
            return false;
        }
    }
}
