package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RegistroViewModel {
    private static String errorMessage;
    private static String nombre;
    private static String apellido;
    private static String email;
    private static String telefono;
    private static String password;
    private conexion connect;

    @Init
    public void initRegistro() {
        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    @NotifyChange( "errorMessage" )
    public void registrar() {
       
            if (registrarEnBaseDeDatos()) {
                // Registro exitoso, redirigir a la página de inicio de sesión
                Executions.sendRedirect("Login.zul");
            } else {
                errorMessage = "Campos vacios";

                // Error al registrar en la base de datos, manejar según sea necesario
            }
        
    }

    public static boolean validarDatosRegistro(String nombre, String apellido, String email, String telefono,String password) {
        System.out.println("Nombre: " + nombre);
System.out.println("Apellido: " + apellido);
System.out.println("Email: " + email);
System.out.println("Teléfono: " + telefono);
System.out.println("Password: " + password);
System.out.println("Error Message: " + errorMessage);

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

    public static boolean validarFormatoEmail(String email) {
        // Utilizando una expresión regular simple para verificar el formato del email
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(regex);
    }

    public static boolean verificarEmailExistente(String email) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                        "0077");) {
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
            throw new RuntimeException("Error al verificar si el correo electrónico existe: " + e.getMessage(), e);
            
            // Manejo de excepciones según sea necesario
        }
        return false; // En caso de error, consideramos que el email no existe
    }

    private static boolean registrarEnBaseDeDatos() {
          System.out.println("Datos ingresados:");
          System.out.println("Nombre: " + nombre);
          System.out.println("Apelliod: " + apellido);
          System.out.println("Email: " + email);
          System.out.println("Password: " + password);
         System.out.println("Telfono: " + telefono);
 if (!validarDatosRegistro(nombre, apellido, email, telefono, password)) {
            errorMessage = "Datos de registro no válidos.";
         
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                    "0077");

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
return false;
}

    /*public static void main(String[] args) {
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
   /*  public static void main(String[] args) {
        testValidarFormatoEmail();
    }

    private static void testValidarFormatoEmail() {
        conexion connect = new conexion();  // Asegúrate de que estás utilizando la misma clase que contiene validarFormatoEmail

        // Prueba con un email válido
        boolean resultadoValido = validarFormatoEmail("admin@example.com");
        if (resultadoValido) {
            System.out.println("El formato del email es válido");
        } else {
            System.out.println("El formato del email no es válido");
        }

        // Prueba con un email inválido
        boolean resultadoInvalido = validarFormatoEmail("correo_invalido");
        if (!resultadoInvalido) {
            System.out.println("El formato del email no es válido");
        } else {
            System.out.println("El formato del email es válido");
        }
    }/* */
   /* public static void main(String[] args) {
        testValidarDatosRegistro();
    }

    private static void testValidarDatosRegistro() {
        conexion connect = new conexion();  // Asegúrate de que estás utilizando la misma clase que contiene validarDatosRegistro

        // Prueba con datos válidos
        boolean resultadoValido = validarDatosRegistro("Nombre", "Apellido", "usuario@example.com", "123456789", "contrasena");
        if (resultadoValido) {
            System.out.println("Los datos de registro son válidos");
        } else {
            System.out.println("Invalido");
        }

        // Prueba con datos inválidos (correo ya existente)
        boolean resultadoInvalidoCorreoExistente = validarDatosRegistro("Nombre", "Apellido", "admin@example.com", "123456789", "contrasena");
        if (!resultadoInvalidoCorreoExistente) {
            System.out.println("Invalido");
        }

        // Prueba con datos inválidos (formato de correo incorrecto)
        boolean resultadoInvalidoFormatoCorreo = validarDatosRegistro("Nombre", "Apellido", "correo_invalido", "123456789", "contrasena");
        if (!resultadoInvalidoFormatoCorreo) {
            System.out.println("Invalido");
        }

        // Agrega más casos de prueba según sea necesario
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

   /*  public static void main(String[] args) {
        testVerificarEmailExistente();
    }/* 

    /*private static void testVerificarEmailExistente() {
        conexion connect = new conexion();  // Asegúrate de que estás utilizando la misma clase que contiene verificarEmailExistente

        // Prueba con un email existente
        boolean resultadoExistente = verificarEmailExistente("admin@example.com");
        if (resultadoExistente) {
            System.out.println("El email ya existe en la base de datos");
        } else {
            System.out.println("El email no existe en la base de datos");
        }

        // Prueba con un email inexistente
        boolean resultadoInexistente = verificarEmailExistente("nuevo_usuario@example.com");
        if (!resultadoInexistente) {
            System.out.println("El email no existe en la base de datos");
        } else {
            System.out.println("El email ya existe en la base de datos");
        }
    }/* */


    public String geterrorMessage() {
        return errorMessage;
    }
     public void seterrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }






}
