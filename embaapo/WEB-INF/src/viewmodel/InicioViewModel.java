package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

public class InicioViewModel {

    @Command
    public void login() {
        logToConsole("Entro");
    redirigir("embaapo/Login.zul");
        logToConsole("Salio");
    }

    @Command
    public void sing_in() {
        redirigir("/embaapo/SignIn.zul");
    }

    @Command
    public void olvide_mi_contrasena() {
        redirigir("/embaapo/OlvideContrasena.zul");
    }

    private void redirigir(String ruta) {
        Executions.sendRedirect(ruta);
    }
    private void logToConsole(String message) {
        Clients.evalJavaScript("console.log('" + message + "');");
    }
}