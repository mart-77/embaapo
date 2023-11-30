package viewmodel;


import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import java.sql.*;
import java.time.Instant;
import java.util.Scanner;

public class RegistroSellerViewModel {
    private static String nombre;
    private static int cedula;
    private static String nacimiento;
    private static String direccion;
    private static String oficio;
    private static Instant fecha_insersion = Instant.now();
    private static Instant fecha_mod = Instant.now();



    private String errorMessage ;
    private conexion connect;

    @Init
    public void initRegistro() {
        connect = new conexion();
        connect.crearConexion();
    }
     @NotifyChange({"errorMessage"})
    @Command
    public void registrarS() {
       // if (validarDatos()) {
            // Si los datos son válidos, registrar en la base de datos
            if (registrarSeller()) {
                // Registro exitoso, redirigir a la página de inicio de sesión
                Executions.sendRedirect("Menu.zul");
            } else {
                errorMessage = "Usuario o contraseña incorrectos";


                // Error al registrar en la base de datos, manejar según sea necesario
            }
       // }
    }

    private boolean validarDatos() {
        // Agrega aquí lógica de validación según tus necesidades
        return true; // Retornar true si los datos son válidos
    }

    private static boolean registrarSeller() {
       
        System.out.println("Datos ingresados:");
        System.out.println("Nombre: " + nombre);
        System.out.println("cedula: " + cedula);
         System.out.println("nacimiento: " + nacimiento);
        System.out.println("direccion: " + direccion);
         System.out.println("oficio: " + oficio);
          System.out.println("fecha_insersion: " + fecha_insersion);
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
                Timestamp timestampInsersion = Timestamp.from(fecha_insersion);
            Timestamp timestampMod = Timestamp.from(fecha_mod);
            // Consulta para insertar el nuevo usuario
            String consulta = "INSERT INTO seller (nombre,cedula,fecha_nacimiento,direccion,oficio,fecha_insersion,fecha_mod) VALUES (?, ?, ?, ?, ?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setInt(2, cedula);
                preparedStatement.setString(3, nacimiento);
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
        // Solicitar datos al usuario
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre: ");
         nombre = scanner.next();
        System.out.println("Ingrese su cedula: ");
         cedula = scanner.nextInt();
        System.out.println("Ingrese su nacimiento: ");
         nacimiento = scanner.next();
        System.out.println("Ingrese su direcion: ");
         direccion = scanner.next();
        System.out.println("Ingrese su oficio: ");
         oficio = scanner.next();
      


        // Ejecutar la autenticación
        if (registrarSeller()) {
            System.out.println("Se creo en la base de datos");
        } else {
            System.out.println("No se creo en la base de datos.Por favor, inténtalo de nuevo.");
        }
    }
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }
    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
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
