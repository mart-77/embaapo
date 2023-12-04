package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.sql.*;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

public class CalificacionViewModel {
            static CalificacionViewModel viewModel = new CalificacionViewModel();

    private conexion connect;
    private static String errorMessage ;
        String nombreSeller;
        String descripcion;
        int puntuacion;
        static String nombre;
            private static Instant fecha_insersion = Instant.now();
    private static Instant fecha_mod = Instant.now();

    @Init
    public void initCalificar() {
        connect = new conexion();
        connect.crearConexion();
    }
    @NotifyChange({"errorMessage"})
    @Command
    public void calificar() {
    
        if (guardarCalificacion()) {
            System.out.println("Calificación guardada con éxito");
        } else {
            System.out.println("Error al guardar la calificación: " + errorMessage);
        }
    }
    /*public List<String> obtenerNombresSellers() {
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
    }/* */

    public boolean guardarCalificacion() {
        try (Connection connection = obtenerConexion()) {
            // Obtener el id del vendedor con el nombre proporcionado
            int idSeller = obtenerIdVendedor(connection, nombreSeller);
            Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
            Timestamp timestampMod = Timestamp.from(fecha_mod);
            // Insertar en la tabla calificacion
            String sql = "INSERT INTO calificacion (id_seller, descripcion, puntuacion,fecha_mod,fecha_insersion) VALUES (?, ?, ?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, idSeller);
                preparedStatement.setString(2, descripcion);
                preparedStatement.setInt(3, puntuacion);
                preparedStatement.setObject(4, timestampInsersion);
                preparedStatement.setObject(5, timestampMod);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage = "Error de base de datos: " + e.getMessage();
        }
        return false;
    }
    private int obtenerIdVendedor(Connection connection, String nombreSeller) throws SQLException {
        String sql = "SELECT id_seller FROM seller WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombreSeller);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_seller");
                }
            }
        }
        // Si no se encuentra el vendedor, podrías lanzar una excepción o manejarlo según tu lógica de negocio
        throw new SQLException("No se encontró el vendedor con nombre '" + nombreSeller + "'");
    }

    /*private static int obtenerIdSellerPorNombre() {
        int idSeller = -1; // Valor predeterminado si no se encuentra el seller

        try (Connection connection = obtenerConexion()) {
                    List<String> nombres = viewModel.obtenerNombresSellers();

            String sql = "SELECT id_seller FROM seller WHERE nombre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nombre);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        idSeller = resultSet.getInt("id_seller");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage = "Error al obtener el ID del seller: " + e.getMessage();
        }

        return idSeller;
    }/* */

    
    private static Connection obtenerConexion() throws SQLException {
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
   public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Instant  getFecha_insersion() {
        return fecha_insersion;
    }

    public void setFecha_insersion(Instant  fecha_insersion) {
        this.fecha_insersion = fecha_insersion;
    }
     public Instant  getFecha_mod() {
        return fecha_mod;
    }

    public void setfecha_mod(Instant  fecha_mod) {
        this.fecha_mod = fecha_mod;
    }
    /*public static void main(String[] args) {
        // Crear instancia de CalificacionViewModel
        CalificacionViewModel viewModel = new CalificacionViewModel();

        // Llamar a obtenerNombresSellers y mostrar el resultado
        List<String> nombres = viewModel.obtenerNombresSellers();

        if (nombres != null) {
            System.out.println("Nombres de Sellers:");
            for (String nombre : nombres) {
                System.out.println(nombre);
            }
        } else {
            System.out.println("Error al obtener los nombres de Sellers.");
        }
    }/* */
    /*public static void main(String[] args) {
        // Crear instancia de CalificacionViewModel
        CalificacionViewModel viewModel = new CalificacionViewModel();

        // Configurar datos de prueba (ajusta según tus necesidades)
        viewModel.setNombre("NombreDeSeller");

        // Llamar a obtenerIdSellerPorNombre y mostrar el resultado
        int idSeller = CalificacionViewModel.obtenerIdSellerPorNombre();

        if (idSeller != -1) {
            System.out.println("El ID del seller es: " + idSeller);
        } else {
            System.out.println("No se encontró un seller con ese nombre.");
            System.out.println("Mensaje de error: " + viewModel.getErrorMessage());
        }
    }
    private String getErrorMessage() {
        return null;
    }

      /*  public static void main(String[] args) {
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
        // Crear instancia de CalificacionViewModel
        CalificacionViewModel viewModel = new CalificacionViewModel();

        // Configurar datos de prueba (ajusta según tus necesidades)
        viewModel.setNombre("NombreDeSeller");
        viewModel.setDescripcion("Buena atención");
        viewModel.setPuntuacion(5);

        // Llamar a guardarCalificacion y mostrar el resultado
        boolean calificacionGuardada = viewModel.guardarCalificacion();

        if (calificacionGuardada) {
            System.out.println("La calificación se guardó exitosamente.");
        } else {
            System.out.println("Error al guardar la calificación.");
            System.out.println("Mensaje de error: " + viewModel.getErrorMessage());
        }
    }
    private String getErrorMessage() {
        return errorMessage;
    }/* */

}




