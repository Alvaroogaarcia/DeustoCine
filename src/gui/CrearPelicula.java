package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;
import domain.Entidad;

public class CrearPelicula extends JFrame {

    private JTextField txtTitulo, txtAnio, txtDuracion, txtGenero, txtAforo;
    private JLabel lblImagenPreview;
    private String rutaImagenSeleccionada = null;
    private Entidad entidad; 

    public CrearPelicula(Entidad entidad) {
        this.entidad = entidad;
                
        //Configuracion de la ventana
        setTitle("Crear Película");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        
        // Utilizamos GridBagContraints para organizar la ventana
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //JLabels
        JLabel lblTitulo = new JLabel("Título:");
        gbc.gridx = 0; gbc.gridy = 0; add(lblTitulo, gbc);
        txtTitulo = new JTextField(20); gbc.gridx = 1; add(txtTitulo, gbc);

        JLabel lblAnio = new JLabel("Año:");
        gbc.gridx = 0; gbc.gridy = 1; add(lblAnio, gbc);
        txtAnio = new JTextField(20); gbc.gridx = 1; add(txtAnio, gbc);

        JLabel lblDuracion = new JLabel("Duración (min):");
        gbc.gridx = 0; gbc.gridy = 2; add(lblDuracion, gbc);
        txtDuracion = new JTextField(20); gbc.gridx = 1; add(txtDuracion, gbc);

        JLabel lblGenero = new JLabel("Género:");
        gbc.gridx = 0; gbc.gridy = 3; add(lblGenero, gbc);
        txtGenero = new JTextField(20); gbc.gridx = 1; add(txtGenero, gbc);

        JLabel lblAforo = new JLabel("Aforo:");
        gbc.gridx = 0; gbc.gridy = 4; add(lblAforo, gbc);
        txtAforo = new JTextField(20); gbc.gridx = 1; add(txtAforo, gbc);

        // Imagen
        JLabel lblImagen = new JLabel("Imagen:");
        gbc.gridx = 0; gbc.gridy = 5; add(lblImagen, gbc);

        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen...");
        gbc.gridx = 1; add(btnSeleccionarImagen, gbc);

        lblImagenPreview = new JLabel("Sin imagen");
        lblImagenPreview.setPreferredSize(new Dimension(150, 200));
        lblImagenPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagenPreview.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 1; gbc.gridy = 6; add(lblImagenPreview, gbc);
        

        //Actiion listener sobre el boton para elegir la imagen
        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());

        // Boton crear pelicula
        JButton btnCrear = new JButton("Crear Película");
        gbc.gridx = 1; gbc.gridy = 7; add(btnCrear, gbc);
        btnCrear.addActionListener(e -> crearPelicula());
    }

    //Metodo para seleccionar la imagen de la pelicula
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg"));

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            rutaImagenSeleccionada = archivoSeleccionado.getAbsolutePath();

            ImageIcon iconoOriginal = new ImageIcon(rutaImagenSeleccionada);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                    lblImagenPreview.getWidth(), lblImagenPreview.getHeight(), Image.SCALE_SMOOTH);
            lblImagenPreview.setIcon(new ImageIcon(imagenEscalada));
            lblImagenPreview.setText("");
        }
    }

    //Metodo para crear la pelicula
    private void crearPelicula() {
        String titulo = txtTitulo.getText().trim();
        String anioStr = txtAnio.getText().trim();
        String duracionStr = txtDuracion.getText().trim();
        String genero = txtGenero.getText().trim();
        String aforoStr = txtAforo.getText().trim();

        if (titulo.isEmpty() || anioStr.isEmpty() || duracionStr.isEmpty() || genero.isEmpty() || aforoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int anio, duracion, aforo;
        try {
            anio = Integer.parseInt(anioStr);
            duracion = Integer.parseInt(duracionStr);
            aforo = Integer.parseInt(aforoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año, duración y aforo deben ser números", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO pelicula (titulo, anio, duracion, genero, aforo, imagen) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titulo);
            ps.setInt(2, anio);
            ps.setInt(3, duracion);
            ps.setString(4, genero);
            ps.setInt(5, aforo);
            ps.setString(6, rutaImagenSeleccionada);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Película creada con éxito 🎬");

            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Desea crear otra película?",
                    "Crear otra",
                    JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                limpiarCampos();
            } else {
                this.dispose();
                PerfilEntidad perfil = new PerfilEntidad(entidad);
                perfil.setVisible(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la película", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que limpia los campos una vez creada la pelicula
    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAnio.setText("");
        txtDuracion.setText("");
        txtGenero.setText("");
        txtAforo.setText("");
        lblImagenPreview.setIcon(null);
        lblImagenPreview.setText("Sin imagen");
        rutaImagenSeleccionada = null;
    }
}
