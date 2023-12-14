package viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.*;
import java.util.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.bind.annotation.BindingParam;
public class EliminarACViewModel {
    
    private conexion connect;
    private int id_anuncio_cliente;
    private String errorMessage;

   

    @Init
    public void initEliminarAnuncio() {
        System.out.println("Init Eliminar");
        connect = new conexion();
        connect.crearConexion();
    }

    @Command 
    @NotifyChange({"errorMessage"})
    public void eliminarAnuncioCliente() {
    
        if(connect.eliminarAnuncioCliente(id_anuncio_cliente)) {
            errorMessage = "Anuncio Eliminado";
            redirigir("/GestionAnuncioCliente.zul");
        } else {
            errorMessage = "Error";
        }
    }

     public int getId_anuncio_cliente() {
        return id_anuncio_cliente;
    }

    public void setId_anuncio_cliente(int id_anuncio_cliente) {
        this.id_anuncio_cliente = id_anuncio_cliente;
    }

    public void redirigir(String ruta) {
        Executions.sendRedirect(ruta);
    }
}
