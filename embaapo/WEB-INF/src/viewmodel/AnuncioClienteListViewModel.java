package viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.*;
import java.util.*;

public class AnuncioClienteListViewModel {
    
    private conexion connect;
    private List<Map<String, Object>> anuncios;
    private String terminoBusqueda = "";


    @Init
    public void initAnuncioCliente() {
        System.out.println("Init method called!");
        connect = new conexion();
        connect.crearConexion();
        anuncios = connect.obtenerAnunciosUsuarios();
        System.out.println("\n\n\n\n\n\n\n Anuncios: " + anuncios.size());
        anuncios = connect.buscarAnuncios(terminoBusqueda);
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


}
