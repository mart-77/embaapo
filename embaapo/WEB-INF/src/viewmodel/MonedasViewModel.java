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
    private String simbolo;
    private String usuario_mod;
    private static Instant fecha_mod = Instant.now();

    @Init
    public void initCalificar() {

        connect = new conexion();
        connect.crearConexion();
    }
    @NotifyChange({ "errorMessage" })

    @Command
    public void actualizar() {
        if (actualizarMoneda()) {
            Executions.sendRedirect("/Menu.zul");
        } else {
            errorMessage=("Error al actualizar");
        }

        }
    
    @Command
    public void agregar() {
        if (agregarMoneda()) {
            Executions.sendRedirect("/Menu.zul");
        } else {
            errorMessage=("Error al agregar");

        }
    }
     @Command
    public void eliminar() {
         if(eliminarMoneda()){
            Executions.sendRedirect("/Menu.zul");
        }else {
                        errorMessage=("Error al eliminar");

        }
    }



    public boolean actualizarMoneda() {
        // Establecer la conexión a la base de datos (reemplaza con tus propias
        // credenciales y URL)
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "postgres";
        String contraseña = "0077";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Preparar la consulta SQL para insertar en la tabla divisas
            Timestamp timestampMod = Timestamp.from(fecha_mod);
            String consulta = "UPDATE diviza SET nombre = ?, simbolo = ?, usuario_mod = ?, fecha_mod = ? WHERE id_diviza = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, simbolo);
                preparedStatement.setString(3, usuario_mod);
                preparedStatement.setObject(4, timestampMod);
                preparedStatement.setInt(5, 1);

          // Ejecutar la consulta de eliminación
          int filasEliminadas = preparedStatement.executeUpdate();

          if (filasEliminadas > 0) {
              // Mensaje de éxito si se eliminaron filas
              System.out.println("Moneda eliminada exitosamente de la tabla divisas.");
          } else {
              // Mensaje si no se encontraron filas para eliminar
              System.out.println("No se encontraron monedas para eliminar con el nombre y símbolo proporcionados.");
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
        String usuario = "postgres";
        String contraseña = "0077";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Preparar la consulta SQL para insertar en la tabla divisas
            Timestamp timestampMod = Timestamp.from(fecha_mod);
            String consulta = "INSERT INTO diviza (nombre, simbolo, usuario_mod, fecha_mod) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, simbolo);
                preparedStatement.setString(3, usuario_mod);
                preparedStatement.setObject(4, timestampMod);
                preparedStatement.setInt(5, 1);

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
public boolean eliminarMoneda() {
        // Establecer la conexión a la base de datos (reemplaza con tus propias
        // credenciales y URL)
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "postgres";
        String contraseña = "0077";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Preparar la consulta SQL para insertar en la tabla divisas
            String consulta = "DELETE FROM diviza WHERE nombre = ? AND simbolo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, simbolo);

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


    // Getters y Setters para nombre y simbolo

    public String getNombre() {
        return nombre;
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
/* 
     public static void main(String[] args) {
        MonedasViewModel viewModel = new MonedasViewModel();
        // Configurar datos de prueba (ajusta según tus necesidades)

        viewModel.nombre = "Dolares";
        viewModel.simbolo = "$";
        viewModel.usuario_mod = "admin@example.com";


        // Llamar a guardarMoneda y mostrar el resultado
        boolean monedaGuardada = viewModel.guardarMoneda();
        if (monedaGuardada) {
            System.out.println("La moneda se guardó exitosamente.");
        } else {
            System.out.println("Error al guardar la calificación.");
        }

    }
    /* */
}
