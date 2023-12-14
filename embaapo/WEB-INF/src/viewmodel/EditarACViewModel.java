package viewmodel;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;


public class EditarACViewModel {
    
private conexion connect;
private String titulo;
private String descripcion;
private String direccion;
private int id_anuncio_cliente;
private int id_usuario;
private int id_estado;
private String errorMessage;


@Init
public void initEditar() {
    connect = new conexion();
    connect.crearConexion();
}

@Command
@NotifyChange({"errorMessage"})
public void actualizarAnuncioCliente() {


    if (connect.actualizarAnuncioCliente( titulo, descripcion, direccion, id_anuncio_cliente)) {
        errorMessage = "Anuncio actualizado";
    } else {
        errorMessage = "Error";
    }


}

public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
}

public String getErrorMessage() {
    return errorMessage;
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

public int getId_anuncio_cliente() {
    return id_anuncio_cliente;
}

public void setTitulo(String titulo) {
    this.titulo = titulo;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

public void setDireccion(String direccion) {
    this.direccion = direccion;
}

public void setId_anuncio_cliente(int id_anuncio_cliente) {
    this.id_anuncio_cliente = id_anuncio_cliente;
}

}
