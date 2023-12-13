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
    private Integer numeroSeleccionado;
    private Combobox numeroSeleccionadoCombo;
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
    public boolean validarCalificacion(int numeroSeleccionadoCombo) {
        // Verificar que la puntuación esté en el rango de 1 a 10
        if (numeroSeleccionadoCombo < 1 || numeroSeleccionadoCombo > 10) {
            errorMessage = "La puntuación debe estar en el rango de 1 a 10.";
            return false;
        }
    
        // La puntuación está en el rango permitido
        return true;
    }
    public void guardarNumeroSeleccionado() {
        // Validar que numeroSeleccionadoCombo no sea null antes de usarlo
        if (numeroSeleccionadoCombo != null) {
            Comboitem selectedItem = numeroSeleccionadoCombo.getSelectedItem();
            if (selectedItem != null) {
                numeroSeleccionado = Integer.parseInt(selectedItem.getLabel());

                // Llamar a un método para guardar en la base de datos
                guardarEnBaseDeDatos(numeroSeleccionado);
                // Asignar el valor a numeroSeleccionado después de convertirlo a int
                // Aquí puedes realizar otras acciones después de guardar en la base de datos
            }
            System.out.println(numeroSeleccionado);
        } else {
            System.out.println("numeroSeleccionadoCombo es null");
        }
    }

    private void guardarEnBaseDeDatos(Integer numeroSeleccionado) {
        try (Connection connection = obtenerConexion()) {
            String sql = "INSERT INTO calificacion (puntacion) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, numeroSeleccionado);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage = "Error de base de datos al guardar el número seleccionado: " + e.getMessage();
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

    public boolean guardarCalificacion() {
        if (nombreSeller != null && descripcion != null) {
            guardarNumeroSeleccionado();
            try (Connection connection = obtenerConexion()) {
                // Obtener el id del vendedor con el nombre proporcionado
                int idSeller = obtenerIdVendedor(connection, nombreSeller);
                Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
                Timestamp timestampMod = Timestamp.from(fecha_mod);
                // Insertar en la tabla calificacion
                String sql = "INSERT INTO calificacion (id_seller, descripcion, fecha_mod,fecha_insersion) VALUES (?, ?, ?,?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, idSeller);
                    preparedStatement.setString(2, descripcion);
                    preparedStatement.setObject(3, timestampInsersion);
                    preparedStatement.setObject(4, timestampMod);
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

    public Integer getNumeroSeleccionado() {
        return numeroSeleccionado;
    }

    public void setNumeroSeleccionado(Integer numeroSeleccionado) {
        this.numeroSeleccionado = numeroSeleccionado;
    }

    // Getter y setter para NumeroSeleccionadoCombo

    public Combobox getNumeroSeleccionadoCombo() {
        return numeroSeleccionadoCombo;
    }

    public void setNumeroSeleccionadoCombo(Combobox numeroSeleccionadoCombo) {
        this.numeroSeleccionadoCombo = numeroSeleccionadoCombo;
    }

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

    /*
     * public static void main(String[] args) {
     * // Crear instancia de CalificacionViewModel
     * CalificacionViewModel viewModel = new CalificacionViewModel();
     * 
     * // Llamar a obtenerNombresSellers y mostrar el resultado
     * List<String> nombres = viewModel.obtenerNombresSellers();
     * 
     * if (nombres != null) {
     * System.out.println("Nombres de Sellers:");
     * for (String nombre : nombres) {
     * System.out.println(nombre);
     * }
     * } else {
     * System.out.println("Error al obtener los nombres de Sellers.");
     * }
     * }/*
     */
    /*
     * public static void main(String[] args) {
     * // Crear instancia de CalificacionViewModel
     * CalificacionViewModel viewModel = new CalificacionViewModel();
     * 
     * // Configurar datos de prueba (ajusta según tus necesidades)
     * viewModel.setNombre("NombreDeSeller");
     * 
     * // Llamar a obtenerIdSellerPorNombre y mostrar el resultado
     * int idSeller = CalificacionViewModel.obtenerIdSellerPorNombre();
     * 
     * if (idSeller != -1) {
     * System.out.println("El ID del seller es: " + idSeller);
     * } else {
     * System.out.println("No se encontró un seller con ese nombre.");
     * System.out.println("Mensaje de error: " + viewModel.getErrorMessage());
     * }
     * }
     * private String getErrorMessage() {
     * return null;
     * }
     * 
     * /* public static void main(String[] args) {
     * // Solicitar datos al usuario
     * Scanner scanner = new Scanner(System.in);
     * System.out.println("Ingrese su nombre: ");
     * nombre = scanner.next();
     * System.out.println("Ingrese su apellido: ");
     * apellido = scanner.next();
     * System.out.println("Ingrese su email: ");
     * email = scanner.next();
     * System.out.println("Ingrese su telefono: ");
     * telefono = scanner.next();
     * System.out.println("Ingrese su pasword: ");
     * password = scanner.next();
     * 
     * 
     * 
     * // Ejecutar la autenticación
     * if (registrarEnBaseDeDatos()) {
     * System.out.println("Se creo en la base de datos");
     * } else {
     * System.out.
     * println("No se creo en la base de datos.Por favor, inténtalo de nuevo.");
     * }
     * }/*
     */

    /*
     * public static void main(String[] args) {
     * // Crear instancia de CalificacionViewModel
     * CalificacionViewModel viewModel = new CalificacionViewModel();
     * 
     * // Configurar datos de prueba (ajusta según tus necesidades)
     * viewModel.setNombre("NombreDeSeller");
     * viewModel.setDescripcion("Buena atención");
     * viewModel.setPuntuacion(5);
     * 
     * // Llamar a guardarCalificacion y mostrar el resultado
     * boolean calificacionGuardada = viewModel.guardarCalificacion();
     * 
     * if (calificacionGuardada) {
     * System.out.println("La calificación se guardó exitosamente.");
     * } else {
     * System.out.println("Error al guardar la calificación.");
     * System.out.println("Mensaje de error: " + viewModel.getErrorMessage());
     * }
     * }
     * private String getErrorMessage() {
     * return errorMessage;
     * }/*
     */

}
