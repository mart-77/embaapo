package viewmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;

public class conexion {

    private String url;
    private String usuario;
    private String contrasenia;
    private Connection conexion;

    public conexion() {
        this.url = "jdbc:postgresql://localhost:5432/tp";
        this.usuario = "martin";
        this.contrasenia = "1234";
        this.conexion = null;
    }

    public conexion(String url, String usuario, String contrasenia) {
        this.url = "jdbc:postgresql://localhost:5432/tp";
        this.usuario = "martin";
        this.contrasenia = "1234";
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

    /**
     * 
     * Esta función toma como parámetro una conexión y una consulta sql, y retorna
     * true si esa consulta muestra algún registro (por lo menos 1 fila)*
     * 
     * @param consultaSql
     * @return
     */
    public boolean validarCredenciales(String email, String password) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                    "1234");
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

    public List<Map<String, Object>> obtenerUsuarios() {

        List<Map<String, Object>> listaUsuarios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {

            String sql = "SELECT u.id_usuario, u.nombre, u.email, u.id_rol, r.descripcion AS rol_descripcion " +
                    "FROM usuario u " +
                    "JOIN rol r ON u.id_rol = r.id_rol";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> usuario = new HashMap<>();
                        usuario.put("id_usuario", resultSet.getInt("id_usuario"));
                        usuario.put("nombre", resultSet.getString("nombre"));
                        usuario.put("email", resultSet.getString("email"));
                        usuario.put("rol_descripcion", resultSet.getString("rol_descripcion"));
                        listaUsuarios.add(usuario);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    public List<String> obtenerRoles() {
        List<String> listaRoles = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {
            String sql = "SELECT descripcion FROM rol";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        listaRoles.add(resultSet.getString("descripcion"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaRoles;
    }

    public List<String> obtenerEstados() {
        List<String> listaEstados = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {
            String sql = "SELECT descripcion FROM estado";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        listaEstados.add(resultSet.getString("descripcion"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEstados;
    }
    

    public void actualizarRolUsuario(Map<String, Object> usuario) {
        // Obtener el nuevo rol seleccionado del Map

        String nuevoRol = (String) usuario.get("rol_descripcion");
        int idUsuario = (int) usuario.get("id_usuario");

        System.out.println("Nuevo Rol: " + nuevoRol + " ID Usuario: " + idUsuario);

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {
            String sql = "UPDATE usuario SET id_rol = (SELECT id_rol FROM rol WHERE descripcion = ?) WHERE id_usuario = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nuevoRol);
                preparedStatement.setInt(2, idUsuario);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualizarEstadoAnuncio(Map<String, Object> anuncio) {
        // Obtener el nuevo rol seleccionado del Map

        String nuevoEstado = (String) anuncio.get("descripcion_estado");
        int idAnuncio = (int) anuncio.get("id_anuncio_cliente");


        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {
            String sql = "UPDATE anuncio_cliente SET id_estado = (SELECT id_estado FROM estado WHERE descripcion = ?) WHERE id_anuncio_cliente = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, nuevoEstado);
                preparedStatement.setInt(2, idAnuncio);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> obtenerReservas() {

        List<Map<String, Object>> listaReservas = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {

            String sql = "SELECT " +
                    "r.id_reserva, " +
                    "u.nombre AS nombre_usuario, " +
                    "u.nombre AS nombre_seller, " +
                    "ss.titulo AS titulo_servicio, " +
                    "es.descripcion AS descripcion_estado " +
                    "FROM " +
                    "reserva r " +
                    "JOIN " +
                    "usuario u ON r.id_usuario = u.id_usuario " +
                    "JOIN " +
                    "seller s ON r.id_seller = s.id_seller " +
                    "JOIN " +
                    "seller_anuncio ss ON r.id_servicio = ss.id_servicio " +
                    "JOIN " +
                    "estado es ON r.id_estado = es.id_estado";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> reserva = new HashMap<>();
                        reserva.put("id_reserva", resultSet.getInt("id_reserva"));
                        reserva.put("nombre_usuario", resultSet.getString("nombre_usuario"));
                        reserva.put("nombre_seller", resultSet.getString("nombre_seller"));
                        reserva.put("titulo_servicio", resultSet.getString("titulo_servicio"));
                        reserva.put("descripcion_estado", resultSet.getString("descripcion_estado"));
                        listaReservas.add(reserva);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listaReservas;
    }

    
    public List<Map<String, Object>> obtenerAnunciosUsuarios() {
        List<Map<String, Object>> listaAnunciosUsuarios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {

            String sql = "SELECT ac.titulo AS titulo, ac.descripcion AS descripcion, ac.direccion AS direccion , ac.id_usuario AS id_usuario, u.nombre AS nombre, ac.id_anuncio_cliente AS id_anuncio_cliente, e.descripcion AS descripcion_estado " +
                    "FROM anuncio_cliente ac " +
                    "JOIN usuario u ON ac.id_usuario = u.id_usuario " + 
                    "JOIN estado e ON ac.id_estado = e.id_estado";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> anuncio = new HashMap<>();
                        anuncio.put("titulo", resultSet.getString("titulo"));
                        anuncio.put("descripcion", resultSet.getString("descripcion"));
                        anuncio.put("direccion", resultSet.getString("direccion"));
                        anuncio.put("id_usuario", resultSet.getInt("id_usuario"));
                        anuncio.put("nombre", resultSet.getString("nombre"));
                        anuncio.put("id_anuncio_cliente", resultSet.getInt("id_anuncio_cliente"));
                        anuncio.put("descripcion_estado", resultSet.getString("descripcion_estado"));
                        listaAnunciosUsuarios.add(anuncio);
                        System.out.println(" preparedStatement  :" + preparedStatement);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaAnunciosUsuarios;
    }

    public boolean eliminarAnuncioCliente(int id_anuncio_cliente) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {

            String sql = "DELETE FROM anuncio_cliente WHERE id_anuncio_cliente = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id_anuncio_cliente);
                preparedStatement.executeUpdate();
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
            return false;
        }
    }

    public boolean actualizarAnuncioCliente( String titulo, String descripcion, String direccion, int id_anuncio_cliente) {
    
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin", "1234");
    
            // Convertir java.util.Date a java.sql.Date
    
            // Consulta para insertar el nuevo usuario
            String consulta = "UPDATE anuncio_cliente SET titulo = ?, descripcion = ?, direccion = ? WHERE id_anuncio_cliente = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, titulo);
                preparedStatement.setString(2, descripcion);
                preparedStatement.setString(3, direccion);
                preparedStatement.setInt(4, id_anuncio_cliente);

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


    public List<Map<String, Object>> buscarAnuncios(String terminoBusqueda) {
        List<Map<String, Object>> listaAnunciosUsuarios = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                "1234")) {
            String sql = "SELECT ac.titulo AS titulo, ac.descripcion AS descripcion, ac.direccion AS direccion , ac.id_usuario AS id_usuario, u.nombre AS nombre "
                    +
                    "FROM anuncio_cliente ac " +
                    "JOIN usuario u ON ac.id_usuario = u.id_usuario " +
                    "WHERE lower(ac.titulo) LIKE ?"; // Utilizamos LOWER para hacer la búsqueda insensible a mayúsculas
                                                     // y minúsculas

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {


                preparedStatement.setString(1, "%" + terminoBusqueda.toLowerCase() + "%"); // Agregamos % para la
                                                                                           // búsqueda parcial
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Object> anuncio = new HashMap<>();
                        anuncio.put("titulo", resultSet.getString("titulo"));
                        anuncio.put("descripcion", resultSet.getString("descripcion"));
                        anuncio.put("direccion", resultSet.getString("direccion"));
                        anuncio.put("id_usuario", resultSet.getInt("id_usuario"));
                        anuncio.put("nombre", resultSet.getString("nombre"));
                        listaAnunciosUsuarios.add(anuncio);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de excepciones según sea necesario
        }
        return listaAnunciosUsuarios;
    }

    public boolean registrarAnuncioCliente(String titulo, String descripcion, String direccion) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                    "1234");
                    
            // Consulta para insertar el nuevo usuario
            String consulta = "INSERT INTO anuncio_cliente (id_usuario ,titulo, descripcion, direccion, id_estado) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setInt(1, 2);
                preparedStatement.setString(2, titulo);
                preparedStatement.setString(3, descripcion);
                preparedStatement.setString(4, direccion);
                preparedStatement.setInt(5, 1);
                
                // Ejecutar la inserción
                int filasAfectadas = preparedStatement.executeUpdate();
                System.out.println("Registro exitoso");
                return filasAfectadas > 0;
            }

        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
            return false;
        }
    }

}