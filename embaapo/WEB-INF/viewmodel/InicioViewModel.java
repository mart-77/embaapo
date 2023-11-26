package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class InicioViewModel {

    @Command
    public void login() {
        redirigir("/Login.zul");
    }

    @Command
    public void sing_in() {
        redirigir("/SignIn.zul");
    }

    @Command
    public void olvide_mi_contrasena() {
        redirigir("/OlvideContrasena.zul");
    }

    private void redirigir(String ruta) {
        Executions.sendRedirect(ruta);
    }
}
