package viewmodel;

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

public static void main(String[] args) {
        conexion conncect = new conexion();
        conncect.crearConexion();

        List<Map<String, Object>> monedas = conncect.obtenerMonedas();

        for (Map<String, Object> moneda : monedas) {
            System.out.println("ID Diviza: " + moneda.get("id_diviza"));
            System.out.println("Nombre: " + moneda.get("nombre"));
            System.out.println("Simbolo: " + moneda.get("simbolo"));
            System.out.println("------");
        }
    }





    
}
