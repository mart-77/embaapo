package viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Executions;

public class RegistroACViewModel {

private conexion connect;
private String titulo;
private String descripcion;
private String direccion;
private String errorMessage;

@Init
public void InitRegistroAC() {
    System.out.println("Init method called!");
    connect = new conexion();
    connect.crearConexion();
}

@Command
@NotifyChange({ "errorMessage" })

public void registrar() {
    if (connect.registrarAnuncioCliente(titulo, descripcion, direccion)) {
        errorMessage = "Registro Exitoso";
        redirigir("/GestionAnuncioCliente.zul");
    } else {
        errorMessage = "Registro fallido";
    }
}


// Validaciones


// Getters y Setters
public void setTitulo(String titulo) {
    this.titulo = titulo;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public void setDireccion(String direccion) {
    this.direccion = direccion;
}

public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
}

public String getTitulo() {
    return titulo;
}

public String getDescripcion() {
    return descripcion;
}

public String getDireccion() {
    return direccion;
}

public String getErrorMessage() {
    return errorMessage;
}

public void redirigir(String ruta) {
    Executions.sendRedirect(ruta);
}

}