package viewmodel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    /**
     * Esta función toma como parámetro una conexión y una consulta sql, y retorna
     * true si esa consulta muestra algún registro (por lo menos 1 fila)
     *
     * @param consultaSql
     * @return
     */
    public boolean existeAlgunRegistro(String consultaSql) {
        // Variable para saber la cantidad de registros
        int cantidadRegistros = 0;

        try {
            // Se crea el Statement para ejecutar la consulta
            try (PreparedStatement statement = conexion.prepareStatement(consultaSql)) {
                // Se ejecuta la consulta y se guarda el resultado en un ResultSet
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Se recorren todos los registros
                    while (resultSet.next() && cantidadRegistros < 1) {
                        // Aumenta en 1 la cantidad de registros
                        cantidadRegistros++;
                    }
                }
            }
        } catch (SQLException e) { // En el caso de que haya una excepción, lo toma
            e.printStackTrace(); // Muestra el mensaje de error
        }

        // Se verifica cuánto vale cantidadRegistros
        return cantidadRegistros > 0;
    }
    public static void main(String[] arg){
        conexion cone= new conexion();
        cone.crearConexion();
        try {
            //Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");

            // Consulta para verificar las credenciales
            String consulta = "SELECT * FROM usuario WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = cone.getConexion().prepareStatement(consulta)) {
                
                preparedStatement.setString(1,"admin@example.com" );
                preparedStatement.setString(2, "000");

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if(resultSet.next()){
                                    System.out.println("Se encontro");

                    }else {
                                    System.out.println("No se encontro");

                    }
                    //return resultSet.next();

                }
                System.out.println("Entro");
            }
        } catch (SQLException e) {
            // Manejo de excepciones (registra o maneja según sea necesario)
            e.printStackTrace();
        }
         System.out.println("Termino");
    }



}


