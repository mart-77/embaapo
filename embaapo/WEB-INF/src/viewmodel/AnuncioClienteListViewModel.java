package viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.*;
import java.util.*;
import org.zkoss.zk.ui.Executions;


public class AnuncioClienteListViewModel {

    private conexion connect;
    private List<Map<String, Object>> anuncios;
    private String terminoBusqueda = "";

    @Init
    public void initAnuncioCliente() {
        System.out.println("Init Anuncio");
        connect = new conexion();
        connect.crearConexion();
        anuncios = connect.obtenerAnunciosUsuarios();
        System.out.println("\n\n\n\n\n\n\n Anuncios: " + anuncios.size());
        anuncios = connect.buscarAnuncios(terminoBusqueda);
    }

    @Command 
    public void misAnuncios() {
        redirigir("/GestionAnuncioCliente.zul");
    }

    @Command
    @NotifyChange("anuncios")
    public void buscar() {
        anuncios = connect.buscarAnuncios(terminoBusqueda);
    }

    public List<Map<String, Object>> getAnuncios() {
        return anuncios;
    }

    public void setAnuncios(List<Map<String, Object>> anuncios) {
        this.anuncios = anuncios;
    }

    public String getTerminoBusqueda() {
        return terminoBusqueda;
    }

    public void setTerminoBusqueda(String terminoBusqueda) {
        this.terminoBusqueda = terminoBusqueda;
    }

    public void redirigir(String ruta) {
        Executions.sendRedirect(ruta);
    }

}
