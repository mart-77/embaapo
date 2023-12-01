package viewmodel;

import org.zkoss.bind.annotation.Init;

import java.util.List;
import java.util.Map;


public class DarRolesViewModel {
    private conexion connect;
    private List<Map<String, Object>> usuarios;

    @Init
    public void initDarRoles() {
        System.out.println("Init method called!");
        connect = new conexion();
        connect.crearConexion();
        usuarios = connect.obtenerUsuarios();
    
        // Verificar que la lista de usuarios se haya llenado correctamente
        System.out.println("Total de usuarios en ViewModel: " + usuarios.size());
    }

    public List<Map<String, Object>> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Map<String, Object>> usuarios) {
        this.usuarios = usuarios;
    }


public static void main(String[] args) {
        conexion conncect = new conexion();
        conncect.crearConexion();

        List<Map<String, Object>> usuarios = conncect.obtenerUsuarios();

        for (Map<String, Object> usuario : usuarios) {
            System.out.println("ID Usuario: " + usuario.get("id_usuario"));
            System.out.println("Nombre: " + usuario.get("nombre"));
            System.out.println("Email: " + usuario.get("email"));
            System.out.println("Rol Descripción: " + usuario.get("rol_descripcion"));
            System.out.println("------");
        }
    }
}



