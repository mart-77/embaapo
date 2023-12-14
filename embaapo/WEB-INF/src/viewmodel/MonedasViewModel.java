package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import java.sql.Timestamp;

public class MonedasViewModel {
    private conexion connect;
    private String errorMessage;
    private String nombre;
    private int id_diviza;
    private String simbolo;
    private String usuario_mod = "admin@example.com";
    private Instant fecha_insersion = Instant.now();
    private  Instant fecha_mod = Instant.now();

    @Init
    public void initCalificar() {

        connect = new conexion();
        connect.crearConexion();
    }

    @NotifyChange({ "errorMessage" })

    @Command
    public void actualizar() {
        if (actualizarMoneda()) {
            Executions.sendRedirect("/Monedas.zul");
        } else {
            errorMessage = ("Error al actualizar");
        }

    }

    @Command
    public void agregar() {
        if (agregarMoneda()) {
            Executions.sendRedirect("/Monedas.zul");
        } else {
            errorMessage = ("Error al agregar");

        }
    }

    @Command
    public void eliminar() {
        if (eliminarMoneda()) {
            Executions.sendRedirect("/Monedas.zul");
        } else {
            errorMessage = ("Error al eliminar");

        }
    }

    public boolean actualizarMoneda() {
        // Establecer la conexión a la base de datos (reemplaza con tus propias
        // credenciales y URL)
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "martin";
        String contraseña = "1234";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Preparar la consulta SQL para insertar en la tabla divisas
            Timestamp timestampMod = Timestamp.from(fecha_mod);
            String consulta = "UPDATE diviza SET nombre = ?, simbolo = ?, usuario_mod = ?, fecha_mod = ? WHERE id_diviza = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, simbolo);
                preparedStatement.setString(3, usuario_mod);
                preparedStatement.setObject(4, timestampMod);
                preparedStatement.setInt(5, id_diviza);

                // Ejecutar la consulta de eliminación
                int filasEliminadas = preparedStatement.executeUpdate();

                if (filasEliminadas > 0) {
                    // Mensaje de éxito si se eliminaron filas
                    System.out.println("Moneda eliminada exitosamente de la tabla divisas.");
                } else {
                    // Mensaje si no se encontraron filas para eliminar
                    System.out
                            .println("No se encontraron monedas para eliminar con el nombre y símbolo proporcionados.");
                }
            }
        } catch (SQLException e) {
            // Manejar errores de conexión o consulta
            e.printStackTrace();
        }
        return false;

    }

    public boolean agregarMoneda() {
        // Establecer la conexión a la base de datos (reemplaza con tus propias
        // credenciales y URL)
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "martin";
        String contraseña = "1234";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Preparar la consulta SQL para insertar en la tabla divisas
            Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
            Timestamp timestampMod = Timestamp.from(fecha_mod);
            String consulta = "INSERT INTO diviza (nombre, simbolo, usuario_mod, fecha_mod,fecha_insersion) VALUES (?, ?, ?, ?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, simbolo);
                preparedStatement.setString(3, usuario_mod);
                preparedStatement.setObject(4, timestampMod);
                preparedStatement.setObject(5, timestampInsersion);

                // Ejecutar la consulta de inserción
                preparedStatement.executeUpdate();

                // Mensaje de éxito
                System.out.println("Moneda guardada exitosamente en la tabla divisas.");
            }
        } catch (SQLException e) {
            // Manejar errores de conexión o consulta
            e.printStackTrace();
        }
        return false;

    }
/* 
    public static void main(String[] args) {
        // Crear una instancia de la clase que contiene la función a probar
        MonedasViewModel instancia = new MonedasViewModel();

        // Configurar datos de prueba (ajusta según tus necesidades)
        instancia.setNombre("Dólar");
        instancia.setSimbolo("USD");
        instancia.setUsuario_mod("admin@example.com");
        instancia.setFecha_insersion(Instant.now());
        instancia.setFecha_mod(Instant.now());

        // Llamar a la función y almacenar el resultado
        boolean monedaAgregada = instancia.agregarMoneda();

        // Verificar el resultado
        if (monedaAgregada) {
            System.out.println("La moneda se agregó exitosamente.");
        } else {
            System.out.println("Error al agregar la moneda.");
        }
    }
/* */
    public boolean eliminarMoneda() {
        // Establecer la conexión a la base de datos (reemplaza con tus propias
        // credenciales y URL)
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "martin";
        String contraseña = "1234";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Preparar la consulta SQL para insertar en la tabla divisas
            String consulta = "DELETE FROM diviza WHERE id_diviza = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setInt(1, id_diviza);

                // Ejecutar la consulta de inserción
                preparedStatement.executeUpdate();

                // Mensaje de éxito
                System.out.println("Moneda eliminada exitosamente en la tabla divisas.");
            }
        } catch (SQLException e) {
            // Manejar errores de conexión o consulta
            e.printStackTrace();
        }
        return false;

    }
    public static void main(String[] args) {
        testEliminarMoneda();
    }

    public static void testEliminarMoneda() {
        MonedasViewModel monedasViewModel = new MonedasViewModel();  // Asegúrate de crear una instancia de tu ViewModel

        // Configura el parámetro de prueba
        monedasViewModel.setId_diviza(4);  // Suponiendo que 1 es el ID de la divisa que quieres eliminar

        // Llama a la función que quieres probar
        boolean resultado = monedasViewModel.eliminarMoneda();

        // Verifica el resultado
        if (resultado) {
            System.out.println("Moneda eliminada exitosamente en la tabla divisas.");
        } else {
            System.out.println("No se pudo eliminar la moneda.");
        }
    }




    // Getters y Setters para nombre y simbolo

    public String getNombre() {
        return nombre;
    }

    public void setId_diviza(int id_diviza) {
        this.id_diviza = id_diviza;
    }

    public int getId_diviza() {
        return id_diviza;
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

    public String getUsuario_mod() {
        return usuario_mod;
    }

    public void setUsuario_mod(String usuario_mod) {
        this.usuario_mod = usuario_mod;
    }

    public Instant getFecha_mod() {
        return fecha_mod;
    }

    public void setFecha_mod(Instant fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public Instant getFecha_insersion() {
        return fecha_insersion;
    }

    public void setFecha_insersion(Instant fecha_insersion) {
        this.fecha_insersion = fecha_insersion;
    }

    /*
     * public static void main(String[] args) {
     * MonedasViewModel viewModel = new MonedasViewModel();
     * // Configurar datos de prueba (ajusta según tus necesidades)
     * 
     * viewModel.nombre = "Dolares";
     * viewModel.simbolo = "$";
     * viewModel.usuario_mod = "admin@example.com";
     * 
     * 
     * // Llamar a guardarMoneda y mostrar el resultado
     * boolean monedaGuardada = viewModel.guardarMoneda();
     * if (monedaGuardada) {
     * System.out.println("La moneda se guardó exitosamente.");
     * } else {
     * System.out.println("Error al guardar la calificación.");
     * }
     * 
     * }
     * /*
     */
}
