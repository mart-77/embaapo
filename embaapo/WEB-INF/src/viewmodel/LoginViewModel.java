package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import viewmodel.conexion;
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
        if (conexion.validarCredenciales()) {
           
        // Redirigir a otra pantalla
        Executions.sendRedirect("Menu.zul");
    
        } else {
             errorMessage = "Usuario o contraseña incorrectos";
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
