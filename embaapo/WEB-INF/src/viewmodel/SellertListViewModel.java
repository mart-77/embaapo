package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

public class SellertListViewModel {
     private  String nombre;
    private  int cedula;
    private  Date nacimiento;
    private  String direccion;
    private  String oficio;
    private  Instant fecha_insersion = Instant.now();
    private  Instant fecha_mod = Instant.now();
    private conexion connect;
    private List<Map<String, Object>> sellers;

    @Init
    public void initSeller() {
        System.out.println("\n\n\n\n\n\n\nInit method called!");
        connect = new conexion();
        connect.crearConexion();
        sellers = connect.obtenerSellers();
    }
     @Command
    public void actualizar() {
      //  if (!validarDatosUsuario(  nombre,  cedula,  nacimiento,  direccion,  oficio )) {
            if (actualizarSeller()) {
                // Registro exitoso, redirigir a la página de inicio de sesión
                Executions.sendRedirect("Menu.zul");
            } else {


                // Error al registrar en la base de datos, manejar según sea necesario
            }
    //   }
    }

    private boolean actualizarSeller() {
        System.out.println("Datos ingresados:");
        System.out.println("Nombre: " + nombre);
        System.out.println("Cédula: " + cedula);
        System.out.println("Nacimiento: " + nacimiento);
        System.out.println("Dirección: " + direccion);
        System.out.println("Oficio: " + oficio);
        System.out.println("Fecha de Insersión: " + fecha_insersion);
    
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
            Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
            Timestamp timestampMod = Timestamp.from(fecha_mod);
    
            // Convertir java.util.Date a java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(nacimiento.getTime());
    
            // Consulta para insertar el nuevo usuario
            String consulta = "UPDATE seller SET cedula = ?, fecha_nacimiento = ?, direccion = ?, oficio = ?, fecha_mod = ? WHERE nombre = ?";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setInt(2, cedula);
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setString(4, direccion);
                preparedStatement.setString(5, oficio);
                preparedStatement.setObject(6, timestampInsersion);
                preparedStatement.setObject(7, timestampMod);
    
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
    
 public static void main(String[] args) {
        conexion conncect = new conexion();
        conncect.crearConexion();

       List<Map<String, Object>> sellers = conncect.obtenerSellers();

        for (Map<String, Object> seller : sellers) {
            System.out.println("ID Seller: " + seller.get("id_seller"));
            System.out.println("Nombre: " + seller.get("nombre"));
            System.out.println("Oficio: " + seller.get("oficio"));
            System.out.println("------");
        }
    }
 
    

    
   

    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
    }

    public conexion getConnect() {
        return connect;
    }

    public void setConnect(conexion connect) {
        this.connect = connect;
    }

    public List<Map<String, Object>> getSellers() {
        return sellers;
    }

    public void setSellers(List<Map<String, Object>> sellers) {
        this.sellers = sellers;
    }
   public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }
    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }
  public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
   public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
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


}
