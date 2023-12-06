package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import java.time.Instant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Scanner;

public class RegistroViewModel {
    private  String errorMessage;
    private static  String nombre;
    private static  String apellido;
    private static  String email;
    private static  String telefono;
    private static  String password;
    private static  Instant fecha_insersion = Instant.now();
    private static  Instant fecha_mod = Instant.now();
    private conexion connect;
    

    @Init
    public void initRegistro() {
        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    @NotifyChange({ "errorMessage" })
    public void registrar() {
        if (!validarDatosRegistro(nombre, apellido, email, telefono, password)) {
            errorMessage = "Datos de registro no válidos.";
            if (registrarEnBaseDeDatos()) {
                // Registro exitoso, redirigir a la página de inicio de sesión
                Executions.sendRedirect("Login.zul");
            } else {
                errorMessage = "Usuario o contraseña incorrectos";

                // Error al registrar en la base de datos, manejar según sea necesario
            }
        }
    }

    

    public boolean validarDatosRegistro(String nombre, String apellido, String email, String telefono,
            String password) {
        // Verificar que todos los campos sean obligatorios
        if (nombre == null || nombre.isEmpty() ||
                apellido == null || apellido.isEmpty() ||
                email == null || email.isEmpty() ||
                telefono == null || telefono.isEmpty() ||
                password == null || password.isEmpty()) {
            errorMessage = "Todos los campos son obligatorios.";

            return false;
        }
        if (!validarFormatoEmail(email)) {
            errorMessage = "Formato de correo electrónico no válido.";
            return false;
        }
    
        // Verificar si el correo ya existe en la base de datos
        if (verificarEmailExistente(email)) {
            errorMessage = "Este correo ya está registrado.";
            return false;
        }
        // Verificar que el teléfono contenga solo números
        if (!telefono.matches("\\d+")) {
            errorMessage = "El teléfono debe contener solo números.";
            return false;
        }

        // Verificar que la contraseña tenga al menos 4 caracteres
        if (password.length() < 4) {
            errorMessage = "La contraseña debe tener al menos 4 caracteres.";
            return false;
        }

        // Todos los criterios de validación han pasado
        return true;
    }

    public boolean validarFormatoEmail(String email) {
        // Utilizando una expresión regular simple para verificar el formato del email
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(regex);
    }

    public boolean verificarEmailExistente(String email) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                        "1234");) {
            String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        // Si count es mayor que cero, significa que el email ya existe
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        return false; // En caso de error, consideramos que el email no existe
    }

    private static  boolean registrarEnBaseDeDatos() {

        
         System.out.println("Datos ingresados:");
          System.out.println("Nombre: " + nombre);
          System.out.println("Apelliod: " + apellido);
          System.out.println("Email: " + email);
          System.out.println("Password: " + password);
          System.out.println("Telfono: " + telefono);
         
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres","0077");
                    Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
                    Timestamp timestampMod = Timestamp.from(fecha_mod);

            // Consulta para insertar el nuevo usuario
            String consulta = "INSERT INTO usuario (id_rol ,nombre, apellido, email, telefono, password,  fecha_insersion,fecha_mod) VALUES (?, ?, ?, ?, ?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setInt(1, 3);
                preparedStatement.setString(2, nombre);
                preparedStatement.setString(3, apellido);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, telefono);
                preparedStatement.setString(6, password);
                preparedStatement.setObject(7, timestampInsersion);
                preparedStatement.setObject(8, timestampMod);
            

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

    public void setFecha_insersion(Instant fecha_insersion) {
        this.fecha_insersion = fecha_insersion;
    }

    public void setFecha_mod(Instant fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public Instant getFecha_insersion() {
        return fecha_insersion;
    }

    public Instant getFecha_mod() {
        return fecha_mod;
    }

    
      public static void main(String[] args) {
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
      System.out.
      println("No se creo en la base de datos.Por favor, inténtalo de nuevo.");
     }
      }
     
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

    
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}