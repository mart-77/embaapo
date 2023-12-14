package viewmodel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
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

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

//import org.w3c.dom.Document;
//import org.zkoss.zhtml.Font;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class InformePDFGUIusuario extends JFrame {
    private String errorMessage;

    public InformePDFGUIusuario() {
        initComponents();
    }
    @Command
    @NotifyChange({"errorMessage"})
    public void UsuarioSellerPDF() {
        try {
            // Llamamos al método para crear y llenar el archivo PDF
            InformePDFGUIusuario informePDFGUIusuario = new InformePDFGUIusuario();
            informePDFGUIusuario.generarInformePDF();

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
        setTitle("Informe de Seller Disponibles");

        JButton generarInformeButton = new JButton("Generar Informe de Sellers Disponibles");
        generarInformeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarInformePDF();
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

    // ...
    // ...

    void generarInformePDF() {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("InformeSellers1.pdf"));
            document.open();

            // Crear un párrafo para la imagen y el título
            Paragraph titleAndImage = new Paragraph();
            /* 
            // Añadir la imagen al párrafo
            String imagePath ="logo.png";
            Image image = Image.getInstance(getClass().getClassLoader().getResource(imagePath));
            image.setAlignment(Element.ALIGN_LEFT);
            image.scaleAbsolute(100, 100); // ajusta el tamaño de la imagen según tus necesidades

            // Añadir espacio entre la imagen y el título
            titleAndImage.add(image);
/* */
            // Añadir el título en negrita y un poco más grande al párrafo
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Informe de Sellers Disponibles", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            titleAndImage.add(title);

            // Añadir el párrafo al documento
            document.add(titleAndImage);

            // Separador
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("---------------------------------------------"));

            // Obtener información de la base de datos y agregarla al PDF
            List<Seller> sellers = obtenerSellersDesdeBD();
            for (Seller seller : sellers) {
                document.add(new Paragraph("Nombre del Seller: " + seller.getNombre()));
                document.add(new Paragraph("Oficio: " + seller.getOficio()));
                document.add(new Paragraph("Cedula: " + seller.getCedula()));
                document.add(new Paragraph("---------------------------------------------"));
            }

            document.close();
            JOptionPane.showMessageDialog(this, "Informe de Sellers Disponibles generado exitosamente.");
            // e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar el informe de Sellers Disponibles.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ...

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InformePDFGUIusuario().setVisible(true);
            }
        });
    }

    private static class Seller {
        private String nombre;
        private String oficio;
        private int cedula;

        public Seller(String nombre, String oficio, int cedula) {
            this.nombre = nombre;
            this.oficio = oficio;
            this.cedula = cedula;
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
    }

    private List<Seller> obtenerSellersDesdeBD() {
        List<Seller> sellers = new ArrayList<>();

        // Configuración de la conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "martin";
        String contraseña = "1234";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña)) {
            // Consulta SQL para obtener sellers
            String sql = "SELECT nombre, cedula, oficio FROM seller";

            try (PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    // Crear instancias de Seller con datos de la base de datos
                    sellers.add(new Seller(
                            resultSet.getString("nombre"),
                            resultSet.getString("oficio"),
                            resultSet.getInt("cedula")));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sellers;
    }
/*
    public static void main(String[] args) {
        InformePDFGUIusuario test = new InformePDFGUIusuario();
        test.obtenerSellersDesdeBD();
        test.generarInformePDF();
    }
/* */



    
}
