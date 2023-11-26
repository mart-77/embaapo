package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class LogoutViewModel {

    @Command
    public void logout() {
        // Lógica de cierre de sesión (limpiar datos de la sesión, redirigir a la página de inicio, etc.)
        
        // Redirigir a la página de inicio de sesión
        Executions.sendRedirect("/inicio.zul");
    }
}
