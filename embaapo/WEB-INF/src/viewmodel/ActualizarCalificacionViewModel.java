package viewmodel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

public class ActualizarCalificacionViewModel {
    private conexion connect;
    private String errorMessage;
    String nombreSeller;
    String descripcion;
    private int calificacion;
    String nombre;
    private Instant fecha_insersion = Instant.now();
    private Instant fecha_mod = Instant.now();

    private List<Integer> numeros;

    @Init
    public void initCalificar() {
        // Inicializar la lista de números del 1 al 10
        numeros = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            numeros.add(i);
        }

        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    public void actualizarcalificar() {
        if (actualizarCalificacion()) {
            System.out.println("Calificación guardada con éxito");
            Executions.sendRedirect("Menu.zul");

        } else {
            System.out.println("Error al guardar la calificación: " + errorMessage);
        }
    }

    public List<String> obtenerNombresSellers() {
        List<String> nombres = new ArrayList<>();

        try (Connection connection = obtenerConexion()) {
            String sql = "SELECT nombre FROM seller";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    nombres.add(resultSet.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(nombres);

        return nombres;
    }

    public boolean actualizarCalificacion() {
        if (nombreSeller != null && descripcion != null) {
            try (Connection connection = obtenerConexion()) {
                // Obtener el id del vendedor con el nombre proporcionado
                int idSeller = obtenerIdVendedor(connection, nombreSeller);
                Timestamp timestampMod = Timestamp.from(fecha_mod);
                // Insertar en la tabla calificacion
                String sql = "UPDATE calificacion SET descripcion = ?, puntuacion=? WHERE id_seller = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, descripcion);
                    preparedStatement.setInt(2, calificacion);
                    preparedStatement.setInt(3, idSeller);
                    preparedStatement.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                errorMessage = "Error de base de datos: " + e.getMessage();
            }
        } else {
            errorMessage = "Nombre de seller o descripción es null";
        }
        return false;
    }

    private int obtenerIdVendedor(Connection connection, String nombreSeller) throws SQLException {
        String sql = "SELECT id_seller FROM seller WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombreSeller);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet != null && resultSet.next()) {
                    return resultSet.getInt("id_seller");
                }
            }
        }
        // Si no se encuentra el vendedor, podrías lanzar una excepción o manejarlo
        // según tu lógica de negocio
        throw new SQLException("No se encontró el vendedor con nombre '" + nombreSeller + "'");
    }

    private Connection obtenerConexion() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "postgres";
        String contraseña = "0077";
        return DriverManager.getConnection(url, usuario, contraseña);
    }

    public String getNombreSeller() {
        return nombreSeller;
    }

    public void setNombreSeller(String nombreSeller) {
        this.nombreSeller = nombreSeller;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    // Getter y setter para NumeroSeleccionadoCombo

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Integer> getNumeros() {
        return numeros;
    }

    public void setNumeros(List<Integer> numeros) {
        this.numeros = numeros;
    }

    public Instant getFecha_insersion() {
        return fecha_insersion;
    }

    public void setFecha_insersion(Instant fecha_insersion) {
        this.fecha_insersion = fecha_insersion;
    }

    public Instant getFecha_mod() {
        return fecha_mod;
    }

    public void setfecha_mod(Instant fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public static void main(String[] args) {
        realizarPruebas();
    }

    public static void realizarPruebas() {
        pruebaActualizarCalificacionExitoso();
        pruebaObtenerIdVendedorExitoso();
        pruebaObtenerIdVendedorConNombreInexistente();
        // Puedes agregar más llamadas a métodos de prueba según sea necesario.
    }

    public static void pruebaActualizarCalificacionExitoso() {
        ActualizarCalificacionViewModel tuClase = new ActualizarCalificacionViewModel();  // Asegúrate de reemplazar TuClase con el nombre real de tu clase
        tuClase.setNombreSeller("Gonzalo");
        tuClase.setDescripcion("Nueva descripción de prueba");
        tuClase.setCalificacion(4);  // Nueva calificación

        // Prueba de la función actualizarCalificacion()
        System.out.println(tuClase.actualizarCalificacion());
        // Puedes agregar más verificaciones según sea necesario para validar el estado esperado después de actualizar la calificación.
    }

    public static void pruebaObtenerIdVendedorExitoso() {
        ActualizarCalificacionViewModel tuClase = new ActualizarCalificacionViewModel();  // Asegúrate de reemplazar TuClase con el nombre real de tu clase

        try (Connection connection = tuClase.obtenerConexion()) {
            // Configuración de datos de prueba si es necesario
            int idVendedor = tuClase.obtenerIdVendedor(connection, "Gonzalo");

            // Puedes agregar más verificaciones según sea necesario.
            System.out.println("ID del vendedor: " + idVendedor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void pruebaObtenerIdVendedorConNombreInexistente() {
        ActualizarCalificacionViewModel tuClase = new ActualizarCalificacionViewModel();  // Asegúrate de reemplazar TuClase con el nombre real de tu clase

        try (Connection connection = tuClase.obtenerConexion()) {
            // Intentar obtener el ID de un vendedor con un nombre inexistente
            int idVendedor = tuClase.obtenerIdVendedor(connection, "Gonzalo");

            // Verificar que se obtenga un valor negativo o 0 (dependiendo de tu lógica de negocio)
            System.out.println("ID del vendedor (con nombre inexistente): " + idVendedor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
