package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class RegistroViewModel {
    private static String nombre;
    private static String apellido;
    private static String email;
    private static String telefono;
    private static String password;

    private String errorMessage ;
    private conexion connect;

    @Init
    public void initRegistro() {
        connect = new conexion();
        connect.crearConexion();
    }
     @NotifyChange({"errorMessage"})
    @Command
    public void registrar() {
       // if (validarDatos()) {
            // Si los datos son válidos, registrar en la base de datos
            if (registrarEnBaseDeDatos()) {
                // Registro exitoso, redirigir a la página de inicio de sesión
                Executions.sendRedirect("Login.zul");
            } else {
                errorMessage = "Usuario o contraseña incorrectos";


                // Error al registrar en la base de datos, manejar según sea necesario
            }
       // }
    }

    private boolean validarDatos() {
        // Agrega aquí lógica de validación según tus necesidades
        return true; // Retornar true si los datos son válidos
    }

    private static boolean registrarEnBaseDeDatos() {
       
       /* System.out.println("Datos ingresados:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Apelliod: " + apellido);
         System.out.println("Email: " + email);
        System.out.println("Password: " + password);
         System.out.println("Telfono: " + telefono);/* */
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");

            // Consulta para insertar el nuevo usuario
            String consulta = "INSERT INTO usuario (id_rol,nombre, apellido, email, telefono, password) VALUES (?, ?, ?, ?, ?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, apellido);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, telefono);
                preparedStatement.setString(6, password);

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
     /*   public static void main(String[] args) {
        // Solicitar datos al usuario
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre: ");
         nombre = scanner.next();
        System.out.println("Ingrese su apellido: ");
         apellido = scanner.next();
        System.out.println("Ingrese su email: ");
         email = scanner.next();
        System.out.println("Ingrese su telefono: ");
         telefono = scanner.next();
        System.out.println("Ingrese su pasword: ");
         password = scanner.next();
      


        // Ejecutar la autenticación
        if (registrarEnBaseDeDatos()) {
            System.out.println("Se creo en la base de datos");
        } else {
            System.out.println("No se creo en la base de datos.Por favor, inténtalo de nuevo.");
        }
    }/* */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
  public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

