package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import java.sql.*;
import java.time.Instant;

public class RegistrarAnuncioSeller {
    private String errorMessage;
    private String titulo;
    private String descripcion;
    private int tarifa;
    private Instant fecha_insersion = Instant.now();
    private Instant fecha_mod = Instant.now();
    private conexion connect;

    @Init
    public void initRegistro() {
        System.out.println("Init ejecutado");
        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    @NotifyChange({ "errorMessage" })
    public void registrar() {
        System.out.println("Entro en el registar:");
     //   if (!validarDatosRegistro(titulo, descripcion, tarifa)) {
          //  errorMessage = "Datos de registro no válidos.";
            if (registrarEnBaseDeDatos()) {
                // Registro exitoso, redirigir a la página de inicio de sesión
                Executions.sendRedirect("ServicioList.zul");
            } else {
                errorMessage = "Usuario o contraseña incorrectos";

                // Error al registrar en la base de datos, manejar según sea necesario
            }
       // }
    }

    public boolean validarDatosRegistro(String titulo, String descripcion, int tarifa) {
        System.out.println("Entro en la validacion:");
        System.out.println("titulo: " + titulo);
        System.out.println("descripcion: " + descripcion);
        System.out.println("tarifa: " + tarifa);
    
        // Verificar que todos los campos sean obligatorios
        if (titulo == null || titulo.isEmpty() ||
            descripcion == null || descripcion.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.:");
            errorMessage = "Todos los campos son obligatorios.";
            return false;
        }
    
        // Verificar que la tarifa esté en el rango de 0 a 500 si se proporciona
        if (tarifa < 0 || tarifa > 500) {
            errorMessage = "La tarifa debe estar en el rango de 0 a 500.";
            System.out.println("La tarifa debe estar en el rango de 0 a 500.:");
            return false;
        }
    
        // Todos los criterios de validación han pasado
        return true;
    }
    

    private boolean registrarEnBaseDeDatos() {

        System.out.println("Entro en el guardado:");
        System.out.println("titulo: " + titulo);
        System.out.println("descripcion: " + descripcion);
        System.out.println("tarifa: " + tarifa);

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "martin",
                    "1234");
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

    public void setFecha_insersion(Instant fecha_insersion) {
        this.fecha_insersion = fecha_insersion;
    }

    public void setFecha_mod(Instant fecha_mod) {
        this.fecha_mod = fecha_mod;
    }

    public Instant getFecha_insersion() {
        return fecha_insersion;
    }

    public Instant getFecha_mod() {
        return fecha_mod;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public static void main(String[] args) {
        // Crear una instancia de conexión
        conexion connect = new conexion();

        // Datos de prueba para el registro
        String titulo = "Título de prueba";
        String descripcion = "Descripción de prueba";
        int tarifa = 100;

        // Llamar a la función de registro
        boolean registroExitoso = connect.registrarEnBaseDeDatos(titulo, descripcion, tarifa);

        // Imprimir el resultado
        if (registroExitoso) {
            System.out.println("Registro exitoso en la base de datos.");
        } else {
            System.out.println("Error al intentar registrar en la base de datos.");
        }
    }
    /*
     * public static void main(String[] args) {
     * validarDatosRegistroTest();
     * }
     * 
     * public static void validarDatosRegistroTest() {
     * // Caso 1: Todos los campos proporcionados correctamente
     * boolean resultadoCaso1 = validarDatosRegistro("Título", "Descripción", 100);
     * System.out.println("Caso 1: " + (resultadoCaso1 ? "Éxito" : errorMessage));
     * 
     * // Caso 2: Tarifa fuera del rango
     * boolean resultadoCaso2 = validarDatosRegistro("Título", "Descripción", 600);
     * System.out.println("Caso 2: " + (resultadoCaso2 ? "Éxito" : errorMessage));
     * 
     * // Caso 3: Título nulo
     * boolean resultadoCaso3 = validarDatosRegistro(null, "Descripción", 200);
     * System.out.println("Caso 3: " + (resultadoCaso3 ? "Éxito" : errorMessage));
     * 
     * // Caso 4: Descripción vacía
     * boolean resultadoCaso4 = validarDatosRegistro("Título", "", 300);
     * System.out.println("Caso 4: " + (resultadoCaso4 ? "Éxito" : errorMessage));
     * 
     * // Caso 5: Todos los campos nulos
     * boolean resultadoCaso5 = validarDatosRegistro(null, null, null);
     * System.out.println("Caso 5: " + (resultadoCaso5 ? "Éxito" : errorMessage));
     * }
     * /*
     */
}
