package viewmodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class InformeExcelADMIN {
    private String errorMessage;
/* 
    public void InformeEXCELGUIUsuario() {
        initComponents();
    }
/* */
    @Command
    @NotifyChange({ "errorMessage" })
    public void generarInformeExcel(String nombreArchivo) {
        try {
            // Llamamos al método para crear y llenar el archivo XLSX
            InformeEXCELGUIUsuario informeExcelGUIADMIN = new InformeEXCELGUIUsuario();
            informeExcelGUIADMIN.generarInformeExcel("Lista de Usuarios.xls");

            // Redirigir a la página deseada después de generar el informe
            Executions.sendRedirect("TuPagina.zul");

            // Puedes realizar otras acciones después de generar el informe si es necesario
            errorMessage = "Informe de Usuarios Registrados generado exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "Error al generar el informe de Usuarios Disponibles.";
        }
    }
/* 
    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Informe de Usuarios Disponibles");

        JButton generarInformeButton = new JButton("Generar Informe de Usuarios Disponibles");
        generarInformeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // generarInformeExcel();
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(generarInformeButton)
                                .addContainerGap(150, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(generarInformeButton)
                                .addContainerGap(100, Short.MAX_VALUE)));

        pack();
        setLocationRelativeTo(null);
    }
/* */
    public  static class Usuario {
        private int id_usuario;
        private String nombre;
        private String apellido;
        private String email;
        private int telefono;

        public Usuario(int id_usuario, String nombre, String apellido, String email, int telefono) {
            this.id_usuario = id_usuario;
            this.nombre = nombre;
            this.apellido = apellido;
            this.email = email;
            this.telefono = telefono;
        }

        public int getId_usuario() {
            return id_usuario;
        }

        public String getNombre() {
            return nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public int getTelefono() {
            return telefono;
        }

        public String getEmail() {
            return email;
        }

    }

    public static List<Usuario> obtenerUsuariosDesdeBD() {
        List<Usuario> usuarios = new ArrayList<>();

        // Configurar la conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "postgres";
        String contraseña = "0077";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT id_usuario, nombre, apellido, telefono,email FROM usuario")) {

            while (resultSet.next()) {
                // Crear instancias de Servicio con datos de la base de datos
                usuarios.add(new Usuario(
                        resultSet.getInt("id_usuario"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                         resultSet.getString("email"),
                        resultSet.getInt("telefono")));

            }
        } catch (SQLException e) {
            // Manejar la excepción de manera significativa, como lanzar una excepción
            // personalizada o mostrar un mensaje de error
            e.printStackTrace();
        }
        return usuarios;

    }

    public static void main(String[] args) {
        // Llamamos al método para crear y llenar el archivo XLSX
        crearYRellenarArchivoXLSX("Informe Excel.xls");
    }

    public static  void crearYRellenarArchivoXLSX(String nombreArchivo) {
        try {
            // Creamos un libro de trabajo (Workbook)
            WritableWorkbook workbook = Workbook.createWorkbook(new File(nombreArchivo));

            // Creamos una hoja en el libro
            WritableSheet sheet = workbook.createSheet("MiHoja", 0);

            // Rellenamos datos en la hoja
            agregarDatos(sheet);

            // Escribimos y cerramos el libro
            workbook.write();
            workbook.close();

            System.out.println("Archivo XLS creado y llenado con éxito.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static  void agregarDatos(WritableSheet sheet) throws Exception {
        // Obtener información de la base de datos y agregarla al archivo Excel
        List<Usuario> usuarios = obtenerUsuariosDesdeBD();
        int rowNum = 0;

        // Crear la primera fila que contendrá los encabezados
        Label headerLabel1 = new Label(0, rowNum, "Id_Usuario");
        Label headerLabel2 = new Label(1, rowNum, "Nombre");
        Label headerLabel3 = new Label(2, rowNum, "Apellido");
        Label headerLabel4 = new Label(3, rowNum, "Email");
        Label headerLabel5 = new Label(4, rowNum, "Telefono");

        sheet.addCell(headerLabel1);
        sheet.addCell(headerLabel2);
        sheet.addCell(headerLabel3);
        sheet.addCell(headerLabel4);
        sheet.addCell(headerLabel5);

        rowNum++;

        // Agregar datos a la hoja
        for (Usuario usuario : usuarios) {
            sheet.addCell(new Label(0, rowNum, String.valueOf(usuario.getId_usuario())));
            sheet.addCell(new Label(1, rowNum, usuario.getNombre()));
            sheet.addCell(new Label(2, rowNum, usuario.getApellido())); 
            sheet.addCell(new Label(3, rowNum, usuario.getEmail()));
            sheet.addCell(new Label(4, rowNum, String.valueOf(usuario.getTelefono())));
                                                                                       
            rowNum++;
        }
    }

}
