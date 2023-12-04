package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class conexion {

    private String url;
    private String usuario;
    private String contrasenia;
    private Connection conexion;

    public conexion() {
        this.url = "jdbc:postgresql://localhost:5432/tp";
        this.usuario = "postgres";
        this.contrasenia = "0077";
        this.conexion = null;
    }

    public conexion(String url, String usuario, String contrasenia) {
        this.url = "jdbc:postgresql://localhost:5432/tp";
        this.usuario = "postgres";
        this.contrasenia = "0077";
        this.conexion = null;
    }

    public void crearConexion() {
        try {
            // Se define el driver a utilizar (postgresql)
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Se crea la conexión
        try {
            // Conexión con la base de datos
            conexion = DriverManager.getConnection(this.url, this.usuario, this.contrasenia);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public boolean validarCredenciales(String email, String password) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres","0077");
            System.out.println("Conecto");
            // Consulta para verificar las credenciales
            String consulta = "SELECT * FROM usuario WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();

                }

            }
        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
            return false;
        }

    }


    public static void main(String[] args) {
        testValidarCredenciales();
    }

    private static void testValidarCredenciales() {
        conexion connect = new conexion();  // Asegúrate de que estás utilizando la misma clase que contiene validarCredenciales

        // Prueba credenciales válidas
        boolean resultadoValido = connect.validarCredenciales("admin@example.com", "0077");
        if (resultadoValido) {
            System.out.println("Credenciales válidas");
        } else {
            System.out.println("Credenciales inválidas");
        }

        // Prueba credenciales inválidas
        boolean resultadoInvalido = connect.validarCredenciales("usuario@example.com", "otra_contraseña");
        if (!resultadoInvalido) {
            System.out.println("Credenciales inválidas");
        } else {
            System.out.println("Credenciales válidas");
        }
    }
}





