package viewmodel;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;

import java.util.*;

public class ServicioListViewModel {
    private String terminoBusqueda = "";

    private conexion connect;
    private List<Map<String, Object>> servicios;

    @Init
    public void initServicio() {
        System.out.println("\n\n\n\n\n\n\nInit method called!");
        connect = new conexion();
        connect.crearConexion();
        servicios = connect.obtenerServicios();
        servicios = connect.buscarServicios(terminoBusqueda);
    }

    @Command
    @NotifyChange("servicios")
    public void buscar() {
        // Lógica de búsqueda aquí...
        servicios = connect.buscarServicios(terminoBusqueda);
    }

    public List<Map<String, Object>> getServicios() {
        return servicios;
    }

    public void setServicios(List<Map<String, Object>> servicios) {
        this.servicios = servicios;
    }

    public void setTerminoBusqueda(String terminoBusqueda) {
        this.terminoBusqueda = terminoBusqueda;
    }

    public String getTerminoBusqueda() {
        return terminoBusqueda;
    }

}