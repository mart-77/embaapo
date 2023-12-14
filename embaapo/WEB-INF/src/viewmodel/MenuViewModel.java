package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;

public class MenuViewModel {
    @Command
    public void logout() {
        // Lógica de cierre de sesión (limpiar datos de la sesión, redirigir a la página de inicio, etc.)
        
        // Redirigir a la página de inicio de sesión
        Executions.sendRedirect("/inicio.zul");
    }

    @Command
    public void generarInformePDF() {
        // Lógica para generar el informe PDF
        InformePDFGUIusuario informePDF = new InformePDFGUIusuario();
        informePDF.generarInformePDF();
    }

    @Command
    public void generarInformeExcel() {
        // Lógica para generar el informe Excel
        InformeEXCELGUIUsuario informeExcel = new InformeEXCELGUIUsuario();
        informeExcel.generarInformeExcel("Lista Sellers.xls");
    }
    @Command
    public void generarInformePDFADMIN() {
        System.out.print("Boton de generar informe PDF presionado");
        // Lógica para generar el informe PDF
        InformePDFADMIN informePDF = new InformePDFADMIN();
        informePDF.AdminUsuarioPDF();
    }

    @Command
    public void generarInformeExcelADMIN() {
        // Lógica para generar el informe Excel
        System.out.print("Boton de generar informe Excel presionado");
        InformeExcelADMIN informeExcel = new InformeExcelADMIN();
        informeExcel.generarInformeExcel("Lista Usuarios.xls");
    }
}
