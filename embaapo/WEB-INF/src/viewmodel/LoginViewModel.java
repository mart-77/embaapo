package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

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
        System.out.println("Email y Password: " + email + password);
        if (connect.validarCredenciales(email, password)) {
                    Sessions.getCurrent().setAttribute("email", email);

            // Obtener el id_rol del usuario

            // Redirigir según el id_rol o el correo
            redirigirSegunCredenciales( email);
            System.out.println("email: " + email  );

        } else {
            errorMessage = "Usuario o contraseña incorrectos";
        }
    }
            private static void redirigirSegunCredenciales( String email) {
                if ("admin@example.com".equalsIgnoreCase(email)) {
                    Executions.sendRedirect("/MenuAdmin.zul");
                } else {
                    Executions.sendRedirect("/Menu.zul");
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