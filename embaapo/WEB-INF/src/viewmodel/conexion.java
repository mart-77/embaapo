package viewmodel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

public class conexion extends SelectorComposer<Component> {
    @Wire
    private static Textbox emailTextbox;
    @Wire
    private static Textbox passwordTextbox;
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
  
    static boolean validarCredenciales() {
        String email = (emailTextbox != null) ? emailTextbox.getValue() : null;
        String password = (passwordTextbox != null) ? passwordTextbox.getValue() : null;
        System.out.println("Entro");
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");
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


    public static void main(String[] args) {
        // Solicitar datos al usuario
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su email: ");
        String email = scanner.nextLine();
        System.out.println("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        // Ejecutar la autenticación
        if (validarCredenciales()) {
            System.out.println("¡Inicio de sesión exitoso!");
        } else {
            System.out.println("Credenciales incorrectas. Por favor, inténtalo de nuevo.");
        }
    }
}



    /* 
    public static void main(String[] arg){
        conexion cone= new conexion();
        cone.crearConexion();

        try {
            //Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tp", "postgres", "0077");

            // Consulta para verificar las credenciales
            String consulta = "SELECT * FROM usuario WHERE email = ? AND password = ?";
            try (PreparedStatement preparedStatement = cone.getConexion().prepareStatement(consulta)) {
                preparedStatement.setString(1,"" );
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
    }/* /* */
//}


