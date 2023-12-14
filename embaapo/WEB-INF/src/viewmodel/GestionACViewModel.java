package viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.*;
import java.util.*;
import org.zkoss.zk.ui.Executions;
import org.zkoss.bind.annotation.BindingParam;

public class GestionACViewModel {

    private conexion connect;
    private List<Map<String, Object>> anuncios;
    private List<String> estados;
    

    @Init
    public void initGestionAnuncio() {
        System.out.println("Init Gestion");
        connect = new conexion();
        connect.crearConexion();
        anuncios = connect.obtenerAnunciosUsuarios();
        estados = connect.obtenerEstados();
        System.out.println("\n\n\n\n\n\n\n Anuncios: " + anuncios.size());
    }

    @Command
    @NotifyChange("anuncios")
    public void actualizarEstadoAnuncio(@BindingParam("dato") Map<String, Object> anuncio) {
        

        System.out.println("\n\n\n\n\n\n\n Command called");

        String nuevoEstado = (String) anuncio.get("descripcion_estado");
        int idAnuncio = (int) anuncio.get("id_anuncio_cliente");

        System.out.println("Nuevo Rol: "+ nuevoEstado +" ID Usuario: "+ idAnuncio);

        connect.actualizarEstadoAnuncio(anuncio);
        anuncios = connect.obtenerAnunciosUsuarios();   
        estados = connect.obtenerEstados(); // Actualizar la lista de usuarios después de cambiar el rol
    }

    @Command 
    public void agregarAnuncio() {
        redirigir("/RegistrarAnuncioCliente.zul");
    }

    @Command 
    public void actualizarAnuncio(){
        redirigir("/EditarAnuncioCliente.zul");
    }

    @Command
    public void eliminarAnuncio(){
        redirigir("/EliminarAnuncioCliente.zul");
    }

    

    public List<Map<String, Object>> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(List<Map<String, Object>> anuncios) {
        this.anuncios = anuncios;
    }


    public void redirigir(String ruta) {
        Executions.sendRedirect(ruta);
    }


    public void setEstados(List<String> estados) {
        this.estados = estados;
    }

    public List<String> getEstados() {
        return estados;
    }
}
