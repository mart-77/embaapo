package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

public class LoginViewModel{

    private String errorMessage ;
    private String email;
    private String password;
    private conexion connect;

    @Init
    public void initLogin() {
        connect = new conexion();
        connect.crearConexion();
    }

    @Command
    @NotifyChange({"errorMessage"})
    public void login() {
        System.out.println("Email y Password: " + email + password );
        if (connect.validarCredenciales(email, password)) {

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