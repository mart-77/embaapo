package viewmodel;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import java.sql.*;
import java.time.Instant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class RegistrarAnuncioSeller {
    private String errorMessage;
    private String titulo;
    private String descripcion;
    private int tarifa;
    private Instant fecha_insersion = Instant.now();
    private Instant fecha_mod = Instant.now();
    private conexion connect;

    @Init
    public void initRegistro() {
        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    @NotifyChange({ "errorMessage" })
     public void registrar() {
        // if (!validarDatosRegistro(nombre, apellido, email, telefono, password)) {
        errorMessage = "Datos de registro no válidos.";
        if (registrarEnBaseDeDatos()) {
            // Registro exitoso, redirigir a la página de inicio de sesión
            Executions.sendRedirect("Login.zul");
        } else {
            errorMessage = "Usuario o contraseña incorrectos";

            // Error al registrar en la base de datos, manejar según sea necesario
        }
        // }
    }

    
    private boolean registrarEnBaseDeDatos() {
        System.out.println("Datos ingresados:");
        System.out.println("titulo: " + titulo);
        System.out.println("descripcion: " + descripcion);
        System.out.println("tarifa: " + tarifa);
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                    "0077");
            Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
            Timestamp timestampMod = Timestamp.from(fecha_mod);
            // Consulta para insertar el nuevo usuario
            String consulta = "INSERT INTO usuario (titulo ,descripcion, tarifa,fecha_insersion,fecha_mod) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, titulo);
                preparedStatement.setString(2, descripcion);
                preparedStatement.setInt(3, tarifa);
                preparedStatement.setObject(4, timestampInsersion);
                preparedStatement.setObject(5, timestampMod);
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

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public static void main(String[] args) {
        // Crear una instancia de conexión
        conexion connect = new conexion();

        // Datos de prueba para el registro
        String titulo = "Título de prueba";
        String descripcion = "Descripción de prueba";
        int tarifa = 100;

        // Llamar a la función de registro
        boolean registroExitoso = connect.registrarEnBaseDeDatos(titulo, descripcion, tarifa);

        // Imprimir el resultado
        if (registroExitoso) {
            System.out.println("Registro exitoso en la base de datos.");
        } else {
            System.out.println("Error al intentar registrar en la base de datos.");
        }
    }

}
