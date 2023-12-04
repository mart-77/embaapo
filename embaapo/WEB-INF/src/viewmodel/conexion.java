package viewmodel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.zkoss.zk.ui.Component;

public class conexion  {

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
     
Esta función toma como parámetro una conexión y una consulta sql, y retorna
true si esa consulta muestra algún registro (por lo menos 1 fila)*
@param consultaSql
@return
*/
public boolean validarCredenciales(String email, String password) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin", "1234");
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

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin", "1234")) {


        String sql = "SELECT u.id_usuario, u.nombre, u.email, r.descripcion AS rol_descripcion " +
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

    public List<Map<String, Object>> obtenerReservas() {

        List<Map<String, Object>> listaReservas = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin", "1234")) {

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



    
}