
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

public class CalificacionViewModel {

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
    

        connect = new conexion();
        connect.crearConexion();
    }

    @NotifyChange({ "errorMessage" })
    @Command
    public void agregarcalificar() {
       // if(!validarCalificacion(numeroSeleccionadoCombo)){

        
        if (guardarCalificacion()) {
            System.out.println("Calificación guardada con éxito");
            Executions.sendRedirect("Menu.zul");

        } else {
            System.out.println("Error al guardar la calificación: " + errorMessage);
        }
        }
   // }
    public boolean validarCalificacion(int calificacion) {
        // Verificar que la puntuación esté en el rango de 1 a 10
        if (calificacion < 1 || calificacion > 10) {
            errorMessage = "La puntuación debe estar en el rango de 1 a 10.";
            return false;
        }
    
        // La puntuación está en el rango permitido
        return true;
    }
   

  

    


    public void guardarNumeroSeleccionado() {
        // Obtener el número seleccionado del combobox
        Comboitem selectedItem = numeroSeleccionadoCombo.getSelectedItem();
        if (selectedItem != null) {
             numeroSeleccionado = Integer.parseInt(selectedItem.getLabel());

            // Llamar a un método para guardar en la base de datos
            guardarEnBaseDeDatos(numeroSeleccionado);
            // Asignar el valor a numeroSeleccionado después de convertirlo a int
            // Aquí puedes realizar otras acciones después de guardar en la base de datos
        }
    }

    private void guardarEnBaseDeDatos(Integer numeroSeleccionado) {
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
            errorMessage = "Error de base de datos al guardar el número seleccionado: " + e.getMessage();
        }
        System.out.println(nombres);

        return nombres;
    }

    public boolean guardarCalificacion() {
        if (nombreSeller != null && descripcion != null) {
            try (Connection connection = obtenerConexion()) {
                // Obtener el id del vendedor con el nombre proporcionado
                int idSeller = obtenerIdVendedor(connection, nombreSeller);
                Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
                Timestamp timestampMod = Timestamp.from(fecha_mod);
                // Insertar en la tabla calificacion
                String sql = "INSERT INTO calificacion (id_seller, descripcion, fecha_mod,fecha_insersion,puntuacion) VALUES (?, ?, ?,?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, idSeller);
                    preparedStatement.setString(2, descripcion);
                    preparedStatement.setObject(3, timestampInsersion);
                    preparedStatement.setObject(4, timestampMod);
                    preparedStatement.setInt(5, calificacion);

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
        String usuario = "martin";
        String contraseña = "1234";
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

    public Integer getCalificacion() {
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
        pruebaObtenerNombresSellers();
        pruebaGuardarCalificacionExitoso();
        pruebaGuardarCalificacionConNombreSellerNulo();
        pruebaObtenerIdVendedor();
        // Puedes agregar más llamadas a métodos de prueba según sea necesario.
    }

    public static void pruebaObtenerNombresSellers() {
        CalificacionViewModel tuClase = new CalificacionViewModel();  // Asegúrate de reemplazar TuClase con el nombre real de tu clase
        // Agregar lógica de configuración de datos de prueba si es necesario

        // Prueba de la función obtenerNombresSellers()
        System.out.println(tuClase.obtenerNombresSellers());
        // Agregar más verificaciones según sea necesario.
    }

    public static void pruebaGuardarCalificacionExitoso() {
        CalificacionViewModel tuClase = new CalificacionViewModel();  // Asegúrate de reemplazar TuClase con el nombre real de tu clase
        tuClase.setNombreSeller("Gonzalo");
        tuClase.setDescripcion("Descripción de prueba");
        tuClase.setFecha_insersion(Instant.now());  // Reemplaza con la fecha de prueba
        tuClase.setfecha_mod(Instant.now());  // Reemplaza con la fecha de prueba
        tuClase.setCalificacion(5);

        // Prueba de la función guardarCalificacion()
        System.out.println(tuClase.guardarCalificacion());
        // Puedes agregar más verificaciones según sea necesario.
    }

    public static void pruebaGuardarCalificacionConNombreSellerNulo() {
        CalificacionViewModel tuClase = new CalificacionViewModel();
        // Omitir la configuración de nombreSeller para simular un escenario con nombreSeller nulo

        // Prueba de la función guardarCalificacion()
        System.out.println(tuClase.guardarCalificacion());
        // Agregar más verificaciones según sea necesario.
    }

    public static void pruebaObtenerIdVendedor() {
        CalificacionViewModel tuClase = new CalificacionViewModel();  // Asegúrate de reemplazar TuClase con el nombre real de tu clase
        // Configuración de datos de prueba si es necesario

        try (Connection connection = tuClase.obtenerConexion()) {
            // Agregar lógica de configuración de datos de prueba si es necesario
            int id_seller = tuClase.obtenerIdVendedor(connection, "Gonzalo");

            // Puedes agregar más verificaciones según sea necesario.
            System.out.println("ID del vendedor: " + id_seller);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 

    
}
