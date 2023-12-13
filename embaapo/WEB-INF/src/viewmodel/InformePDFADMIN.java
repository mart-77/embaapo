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
import java.sql.Statement;
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

public class InformePDFADMIN extends JFrame {
    private String errorMessage;

    public InformePDFADMIN() {
        initComponents();
    }

    @Command
    @NotifyChange({ "errorMessage" })
    public void AdminUsuarioPDF() {
        try {
            // Llamamos al método para crear y llenar el archivo PDF
            InformePDFADMIN informePDFGUIusuario = new InformePDFADMIN();
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
            PdfWriter.getInstance(document, new FileOutputStream("InformeUsuarios.pdf"));
            document.open();

            // Crear un párrafo para la imagen y el título
            Paragraph titleAndImage = new Paragraph();
            /*
             * // Añadir la imagen al párrafo
             * String imagePath ="logo.png";
             * Image image =
             * Image.getInstance(getClass().getClassLoader().getResource(imagePath));
             * image.setAlignment(Element.ALIGN_LEFT);
             * image.scaleAbsolute(100, 100); // ajusta el tamaño de la imagen según tus
             * necesidades
             * 
             * // Añadir espacio entre la imagen y el título
             * titleAndImage.add(image);
             * /*
             */
            // Añadir el título en negrita y un poco más grande al párrafo
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Informe de Usuario Registrados", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            titleAndImage.add(title);

            // Añadir el párrafo al documento
            document.add(titleAndImage);

            // Separador
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("---------------------------------------------"));

            // Obtener información de la base de datos y agregarla al PDF
            List<Usuario> usuarios = obtenerUsuariosDesdeBD();
            for (Usuario usuario : usuarios) {
                document.add(new Paragraph("Nombre del Usuario: " + usuario.getNombre()));
                document.add(new Paragraph("Apellido: " + usuario.getApellido()));
                document.add(new Paragraph("Telefono: " + usuario.getTelefono()));
                document.add(new Paragraph("---------------------------------------------"));
            }

            document.close();
            JOptionPane.showMessageDialog(this, "Informe de Usuarios Usuarios generado exitosamente.");
            // e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar el informe de Usuarios Disponibles.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ...
    /*
     * public static void main(String[] args) {
     * SwingUtilities.invokeLater(new Runnable() {
     * 
     * @Override
     * public void run() {
     * new InformePDFADMIN().setVisible(true);
     * }
     * });
     * }
     * /*
     */
    private static class Usuario {
        private String nombre;
        private String apellido;
        private int telefono;

        public Usuario(String nombre, String apellido, int telefono) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.telefono = telefono;
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
    }

    public List<Usuario> obtenerUsuariosDesdeBD() {
        List<Usuario> usuarios = new ArrayList<>();

        // Configurar la conexión a la base de datos
        String url = "jdbc:postgresql://localhost:5432/tp";
        String usuario = "postgres";
        String contraseña = "0077";

        try (Connection connection = DriverManager.getConnection(url, usuario, contraseña);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT nombre, apellido, telefono FROM usuario")) {

            while (resultSet.next()) {
                // Crear instancias de Servicio con datos de la base de datos
                usuarios.add(new Usuario(
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
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
        InformePDFADMIN test = new InformePDFADMIN();
        test.obtenerUsuariosDesdeBD();
        test.generarInformePDF();
    }

}
