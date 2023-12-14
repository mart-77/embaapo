package viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.BindingParam;


import java.util.*;

public class DarRolesViewModel {
    
    private conexion connect;
    private List<Map<String, Object>> usuarios;
    private List<String> roles;

    @Init
    public void initDarRoles() {
        System.out.println("Init method called!");
        connect = new conexion();
        connect.crearConexion();
        usuarios = connect.obtenerUsuarios();
        roles = connect.obtenerRoles();
        System.out.println("\n\n\n\n\n\n\n Roles: " + roles.size());
       
        // Verificar que la lista de usuarios se haya llenado correctamente
        System.out.println("Total de usuarios en ViewModel: " + usuarios.size());
    }

    @Command
    @NotifyChange("usuarios")
    public void actualizarRolUsuario(@BindingParam("dato") Map<String, Object> usuario) {
        

        System.out.println("\n\n\n\n\n\n\n Command called");

        String nuevoRol = (String) usuario.get("rol_descripcion");
        int idUsuario = (int) usuario.get("id_usuario");

        System.out.println("Nuevo Rol: "+ nuevoRol +" ID Usuario: "+ idUsuario);

        connect.actualizarRolUsuario(usuario);
        usuarios = connect.obtenerUsuarios();   
        roles = connect.obtenerRoles(); // Actualizar la lista de usuarios después de cambiar el rol
    }


    

    public List<Map<String, Object>> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Map<String, Object>> usuarios) {
        this.usuarios = usuarios;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
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
