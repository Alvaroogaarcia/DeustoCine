package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;

public class CrearPelicula extends JFrame {

    private JTextField txtTitulo, txtAnio, txtDuracion, txtGenero, txtAforo;
    private JLabel lblImagenPreview;
    private String rutaImagenSeleccionada = null;

    public CrearPelicula() {
        setTitle("Crear Película");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Campo Título ---
        JLabel lblTitulo = new JLabel("Título:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTitulo, gbc);
        txtTitulo = new JTextField(20);
        gbc.gridx = 1;
        add(txtTitulo, gbc);

        // --- Campo Año ---
        JLabel lblAnio = new JLabel("Año:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblAnio, gbc);
        txtAnio = new JTextField(20);
        gbc.gridx = 1;
        add(txtAnio, gbc);

        // --- Campo Duración ---
        JLabel lblDuracion = new JLabel("Duración (min):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblDuracion, gbc);
        txtDuracion = new JTextField(20);
        gbc.gridx = 1;
        add(txtDuracion, gbc);

        // --- Campo Género ---
        JLabel lblGenero = new JLabel("Género:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblGenero, gbc);
        txtGenero = new JTextField(20);
        gbc.gridx = 1;
        add(txtGenero, gbc);

        // --- Campo Aforo ---
        JLabel lblAforo = new JLabel("Aforo:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblAforo, gbc);
        txtAforo = new JTextField(20);
        gbc.gridx = 1;
        add(txtAforo, gbc);

        // --- Imagen ---
        JLabel lblImagen = new JLabel("Imagen:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lblImagen, gbc);

        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen...");
        gbc.gridx = 1;
        add(btnSeleccionarImagen, gbc);

        lblImagenPreview = new JLabel();
        lblImagenPreview.setPreferredSize(new Dimension(150, 200));
        lblImagenPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblImagenPreview.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagenPreview.setText("Sin imagen");
        gbc.gridx = 1;
        gbc.gridy = 6;
        add(lblImagenPreview, gbc);

        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());

        // --- Botón Crear ---
        JButton btnCrear = new JButton("Crear Película");
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(btnCrear, gbc);

        btnCrear.addActionListener(e -> crearPelicula());
    }

    /**
     * Permite seleccionar una imagen y muestra una vista previa
     */
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes (*.jpg, *.png, *.jpeg)", "jpg", "png", "jpeg"));

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            rutaImagenSeleccionada = archivoSeleccionado.getAbsolutePath();

            // Mostrar vista previa escalada
            ImageIcon iconoOriginal = new ImageIcon(rutaImagenSeleccionada);
            Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(
                    lblImagenPreview.getWidth(), lblImagenPreview.getHeight(), Image.SCALE_SMOOTH);
            lblImagenPreview.setIcon(new ImageIcon(imagenEscalada));
            lblImagenPreview.setText("");
        }
    }

    /**
     * Inserta la película en la base de datos
     */
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

            limpiarCampos();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la película", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CrearPelicula().setVisible(true));
    }
}
