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

public class InformeEXCELGUIUsuario extends JFrame {
    private String errorMessage;
    public InformeEXCELGUIUsuario() {
        initComponents();
    }
 @Command
    @NotifyChange({"errorMessage"})
    public void generarInformeExcel(String nombreArchivo) {
        try {
            // Llamamos al método para crear y llenar el archivo XLSX
            InformeEXCELGUIUsuario informeExcelGUIADMIN = new InformeEXCELGUIUsuario();
            informeExcelGUIADMIN.generarInformeExcel("Lista de Sellers.xls");

            // Redirigir a la página deseada después de generar el informe
            Executions.sendRedirect("TuPagina.zul");

            // Puedes realizar otras acciones después de generar el informe si es necesario
            errorMessage = "Informe de Sellers Disponibles generado exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage = "Error al generar el informe de Sellers Disponibles.";
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Informe de Sellers Disponibles");

        JButton generarInformeButton = new JButton("Generar Informe de Sellers Disponibles");
        generarInformeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  generarInformeExcel();
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

    
    private static class Seller {
        private String nombre;
        private String oficio;
        private int cedula;
        private Date fecha_nacimiento;
        private String direccion;

        public Seller(String nombre, String oficio, int cedula, Date fecha_nacimiento, String direccion) {
            this.nombre = nombre;
            this.oficio = oficio;
            this.cedula = cedula;
            this.fecha_nacimiento = fecha_nacimiento;
            this.direccion = direccion;
        }

        public String getNombre() {
            return nombre;
        }

        public String getOficio() {
            return oficio;
        }

        public int getCedula() {
            return cedula;
        }

        public Date getFecha_nacimiento() {
            return fecha_nacimiento;
        }

        public String getDireccion() {
            return direccion;
        }
    }

    private static List<Seller> obtenerSellersDesdeBD() throws SQLException {
        List<Seller> sellers = new ArrayList<>();

        // Configuración de la conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "martin";
        String contraseña = "1234";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Consulta SQL para obtener Seller
            String sql = "SELECT nombre, cedula, fecha_nacimiento, direccion, oficio FROM seller";

            try (PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    // Crear instancias de Servicio con datos de la base de datos
                    sellers.add(new Seller(
                            resultSet.getString("nombre"),
                            resultSet.getString("oficio"),
                            resultSet.getInt("cedula"),
                            resultSet.getDate("fecha_nacimiento"),
                            resultSet.getString("direccion")));
                }
            }
        }

        return sellers;
    }

    public static void main(String[] args) {
        // Llamamos al método para crear y llenar el archivo XLSX
        crearYRellenarArchivoXLSX("mi_archivo.xls");
    }

    public static void crearYRellenarArchivoXLSX(String nombreArchivo) {
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
        List<Seller> sellers = obtenerSellersDesdeBD();
        int rowNum = 0;

        // Crear la primera fila que contendrá los encabezados
        Label headerLabel1 = new Label(0, rowNum, "Nombre del Seller");
        Label headerLabel2 = new Label(1, rowNum, "Oficio");
        Label headerLabel3 = new Label(2, rowNum, "Cedula");
        Label headerLabel4 = new Label(3, rowNum, "Fecha de Nacimiento");
        Label headerLabel5 = new Label(4, rowNum, "Direccion");

        sheet.addCell(headerLabel1);
        sheet.addCell(headerLabel2);
        sheet.addCell(headerLabel3);
        sheet.addCell(headerLabel4);
        sheet.addCell(headerLabel5);

        rowNum++;

        // Agregar datos a la hoja
        for (Seller seller : sellers) {
            sheet.addCell(new Label(0, rowNum, seller.getNombre()));
            sheet.addCell(new Label(1, rowNum, seller.getOficio()));
            sheet.addCell(new Label(2, rowNum, String.valueOf(seller.getCedula())));
            sheet.addCell(new Label(3, rowNum, seller.getFecha_nacimiento().toString())); // Convierte la fecha a String
            sheet.addCell(new Label(4, rowNum, seller.getDireccion()));
            rowNum++;
        }
    }

}