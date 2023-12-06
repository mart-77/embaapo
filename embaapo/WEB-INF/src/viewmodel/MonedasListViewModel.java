package viewmodel;

import java.util.List;

import java.util.*;

import org.zkoss.bind.annotation.Init;


public class MonedasListViewModel {
       private conexion connect;

    private List<Map<String, Object>> monedas;

    @Init
    public void initCalificar() {
        System.out.println("\n\n\n\n\n\n\nInit method called!");
        connect = new conexion();
        connect.crearConexion();
        monedas = connect.obtenerMonedas();

        // Verificar que la lista de usuarios se haya llenado correctamente
       // System.out.println("Total de usuarios en ViewModel: " + calificaciones.size());
    }

    public List<Map<String, Object>> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<Map<String, Object>> monedas) {
        this.monedas = monedas;
    }







    
}
