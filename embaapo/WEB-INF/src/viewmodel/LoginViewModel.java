package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginViewModel {

    private String email;
    private String password;
    private String errorMessage ;

    
    @Command
    @NotifyChange({"errorMessage"})
    public void login() {
        if (validarCredenciales()) {
           
        // Redirigir a otra pantalla
        Executions.sendRedirect("Menu.zul");
    
        } else {
             errorMessage = "Usuario o contraseña incorrectos";
        }
    }

    private boolean validarCredenciales() {
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
    //Getters y Setters
     public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geterrorMessage() {
        return errorMessage;
    }
     public void seterrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    

   
}
