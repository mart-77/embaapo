package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

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
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                    "0077");
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

    // Método para cargar los sellers desde la base de datos
    public List<Map<String, Object>> obtenerSellers() {
        List<Map<String, Object>> listaSellers = new ArrayList<>();
        System.out.println("Entro en la carga");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077");) {
            String sql = "SELECT id_seller, nombre, oficio FROM seller";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> seller = new HashMap<>();
                        seller.put("id_seller", resultSet.getInt("id_seller"));
                        seller.put("nombre", resultSet.getString("nombre"));
                        seller.put("oficio", resultSet.getString("oficio"));
                        listaSellers.add(seller);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        System.out.println("Salio en la carga");

        return listaSellers;
    }
    // Método para cargar los sellers desde la base de datos
    public List<Map<String, Object>> obtenerCalificaciones() {
        List<Map<String, Object>> listaCalificaciones = new ArrayList<>();
        System.out.println("Entro en la carga");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077");) {
            String sql = "SELECT id_calificacion, id_seller, descripcion,puntuacion  FROM seller";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> calificacion = new HashMap<>();
                        calificacion.put("id_calificacion", resultSet.getInt("id_calificacion"));
                        calificacion.put("id_seller", resultSet.getInt("id_seller"));
                        calificacion.put("descripcion", resultSet.getString("descripcion"));
                        calificacion.put("puntuacion", resultSet.getInt("puntuacion"));
                        listaCalificaciones.add(calificacion);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        System.out.println("Salio en la carga");

        return listaCalificaciones;
    }
    // Método para cargar los sellers desde la base de datos
    public List<Map<String, Object>> obtenerMonedas() {
        List<Map<String, Object>> listaMonedas = new ArrayList<>();
        System.out.println("Entro en la carga");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077");) {
            String sql = "SELECT id_diviza, nombre, simbolo FROM diviza";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> moneda = new HashMap<>();
                        moneda.put("id_diviza", resultSet.getInt("id_diviza"));
                        moneda.put("nombre", resultSet.getString("nombre"));
                        moneda.put("simbolo", resultSet.getString("simbolo"));
                        listaMonedas.add(moneda);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        System.out.println("Salio en la carga");

        return listaMonedas;
    }
    private String titulo;
    private String descripcion;
    private int tarifa;
    private Instant fecha_insersion = Instant.now();
    private Instant fecha_mod = Instant.now();
    private conexion connect;
      public List<Map<String, Object>> obtenerServicios() {
        List<Map<String, Object>> listaServicios = new ArrayList<>();
        System.out.println("Entro en la carga");
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres",
                "0077");) {
            String sql = "SELECT sa.id_servicio, sa.titulo, sa.tarifa," + 
                    "       s.id_seller, s.nombre, s.direccion, s.oficio" + 
                    "FROM seller_anuncio sa" + 
                    "JOIN seller s ON sa.id_seller = s.id_seller;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> servicio = new HashMap<>();
                        servicio.put("id_servicio", resultSet.getInt("id_servicio"));
                        servicio.put("id_seller", resultSet.getInt("id_seller"));
                        servicio.put("titulo", resultSet.getString("titulo"));
                        servicio.put("tarifa", resultSet.getInt("tarifa"));
                        servicio.put("nombre", resultSet.getString("nombre"));
                        servicio.put("direccion", resultSet.getString("direccion"));
                        servicio.put("oficio", resultSet.getString("oficio"));
                        listaServicios.add(servicio);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        System.out.println("Salio en la carga");

        return listaServicios;
    }
        public List<Map<String, Object>> buscarServicios(String terminoBusqueda) {
        List<Map<String, Object>> listaServicios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077")) {
            String sql = "SELECT sa.id_servicio, sa.titulo, sa.tarifa, s.id_seller, s.nombre, s.direccion, s.oficio " +
                         "FROM seller_anuncio sa " +
                         "JOIN seller s ON sa.id_seller = s.id_seller " +
                         "WHERE lower(sa.titulo) LIKE ?"; // Utilizamos LOWER para hacer la búsqueda insensible a mayúsculas y minúsculas

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + terminoBusqueda.toLowerCase() + "%"); // Agregamos % para la búsqueda parcial
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> servicio = new HashMap<>();
                        servicio.put("id_servicio", resultSet.getInt("id_servicio"));
                        servicio.put("id_seller", resultSet.getInt("id_seller"));
                        servicio.put("titulo", resultSet.getString("titulo"));
                        servicio.put("tarifa", resultSet.getInt("tarifa"));
                        servicio.put("nombre", resultSet.getString("nombre"));
                        servicio.put("direccion", resultSet.getString("direccion"));
                        servicio.put("oficio", resultSet.getString("oficio"));
                        listaServicios.add(servicio);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }

        return listaServicios;
    }

    public static void main(String[] args) {
        // Crear una instancia de conexión
        conexion connect = new conexion();

        // Término de búsqueda para la prueba
        String terminoBusqueda = "Servicio Ejemplo"; // Reemplaza con el término que deseas buscar

        // Llamar a la función de búsqueda
        List<Map<String, Object>> resultados = connect.buscarServicios(terminoBusqueda);

        // Imprimir los resultados
        if (!resultados.isEmpty()) {
            System.out.println("Resultados de la búsqueda para '" + terminoBusqueda + "':");
            for (Map<String, Object> servicio : resultados) {
                System.out.println("ID Servicio: " + servicio.get("id_servicio"));
                System.out.println("ID Seller: " + servicio.get("id_seller"));
                System.out.println("Título: " + servicio.get("titulo"));
                System.out.println("Tarifa: " + servicio.get("tarifa"));
                System.out.println("Nombre: " + servicio.get("nombre"));
                System.out.println("Dirección: " + servicio.get("direccion"));
                System.out.println("Oficio: " + servicio.get("oficio"));
                System.out.println("------");
            }
        } else {
            System.out.println("No se encontraron resultados para '" + terminoBusqueda + "'.");
        }
    }
boolean registrarEnBaseDeDatos(String titulo, String descripcion, int tarifa) {
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
            String consulta = "INSERT INTO seller_anuncio (titulo ,descripcion, tarifa,fecha_insersion,fecha_mod) VALUES (?, ?, ?, ?, ?)";
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
    private int id_servicio;

    boolean actualizarAnuncioSeller(int id_servicio, String titulo, String descripcion, int tarifa, Instant instant) {
        System.out.println("Datos ingresados:");
        System.out.println("titulo: " + titulo);
        System.out.println("descripcion: " + descripcion);
        System.out.println("tarifa: " + tarifa);
        System.out.println("Fecha de Insersión: " + fecha_mod);
    
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
            Timestamp timestampMod = Timestamp.from(fecha_mod);
    
            // Convertir java.util.Date a java.sql.Date
    
            // Consulta para insertar el nuevo usuario
            String consulta = "UPDATE seller_anuncio SET titulo = ?, descripcion = ?, tarifa = ?, fecha_mod = ? WHERE id_servicio = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setInt(1, id_servicio);
                preparedStatement.setString(2, titulo);
                preparedStatement.setString(3, descripcion);
                preparedStatement.setInt(4, tarifa);
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


  /*   public static void main(String[] args) {
        testValidarCredenciales();
    }

    private static void testValidarCredenciales() {
        conexion connect = new conexion(); // Asegúrate de que estás utilizando la misma clase que contiene
                                           // validarCredenciales

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
    }/* */
}
