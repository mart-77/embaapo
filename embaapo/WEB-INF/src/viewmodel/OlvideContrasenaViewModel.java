package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class OlvideContrasenaViewModel {

    private static String email;
    private static String nuevaContrasena;
    private String errorMessage ;

private conexion connect;

    @Init
    public void initOlvideContrasena() {
        connect = new conexion();
        connect.crearConexion();
    }
    // Getters y Setters para email y nuevaContrasena
    @NotifyChange({"errorMessage"})
    @Command
    public void restablecerContrasena() {
                errorMessage = "Boton Presionado";
       // if (validarDatos()) {
            // Si los datos son válidos, restablecer la contraseña en la base de datos
        if (restablecerContrasenaEnBaseDeDatos()) {
                // Restablecimiento de contraseña exitoso, redirigir a la página de inicio de sesión
                Executions.sendRedirect("/Inicio.zul");
         } else {
                errorMessage = "Usuario incorrecto";
                // Error al restablecer la contraseña en la base de datos, manejar según sea necesario
            }
       // }
    }

 //   private boolean validarDatos() {
        // Agrega aquí lógica de validación según tus necesidades
 //       return true; // Retornar true si los datos son válidos
//    }

    private static boolean restablecerContrasenaEnBaseDeDatos() {
        try {
            System.out.println("Email: " + email);
            System.out.println("Password: " + nuevaContrasena);
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
            // Consulta para actualizar la contraseña
            String consulta = "UPDATE usuario SET password = ? WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nuevaContrasena);
                preparedStatement.setString(2, email);

                // Ejecutar la actualización
                int filasAfectadas = preparedStatement.executeUpdate();

                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        OlvideContrasenaViewModel viewModel = new OlvideContrasenaViewModel();

        // Configurar el ViewModel con datos de prueba
       Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su email: ");
         email = scanner.next();
        System.out.println("Ingrese su nueva contraseña: ");
         nuevaContrasena = scanner.next();
        // Ejecutar el restablecimiento de contraseña
        if (restablecerContrasenaEnBaseDeDatos()) {
            System.out.println("Restablecimiento de contraseña exitoso.");
        } else {
            System.out.println("Error al restablecer la contraseña.");
        }
    }








    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }
}
