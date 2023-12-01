package viewmodel;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Init;

public class ReservaViewModel {
    private conexion connect;
    private List<Map<String, Object>> reservas;

    @Init
    public void initReserva() {
        connect = new conexion();
        connect.crearConexion();
        reservas = connect.obtenerReservas();
}

public List<Map<String, Object>> getReservas(){
    return reservas;
}

public void setReservas(List<Map<String, Object>> reservas) {
    this.reservas = reservas;
}

public static void main(String[] args) {
    conexion conncect = new conexion();
    conncect.crearConexion();

    List<Map<String, Object>> reservas = conncect.obtenerReservas();

    for (Map<String, Object> reserva : reservas) {
        System.out.println("ID Reserva: " + reserva.get("id_reserva"));
        System.out.println("Nombre Usuario: " + reserva.get("nombre_usuario"));
        System.out.println("Nombre Seller: " + reserva.get("nombre_seller"));
        System.out.println("Titulo Servicio: " + reserva.get("titulo_servicio"));
        System.out.println("Estado: " + reserva.get("descripcion_estado"));
        System.out.println("------");
    }
}
}



