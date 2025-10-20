package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;

public class CrearPelicula extends JFrame {

    private JTextField txtTitulo, txtAnio, txtDuracion, txtGenero, txtAforo;

    public CrearPelicula() {
        setTitle("Crear Película");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Título:"), gbc);
        txtTitulo = new JTextField(20);
        gbc.gridx = 1; add(txtTitulo, gbc);

        // Año
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Año:"), gbc);
        txtAnio = new JTextField(20);
        gbc.gridx = 1; add(txtAnio, gbc);

        // Duración
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Duración (minutos):"), gbc);
        txtDuracion = new JTextField(20);
        gbc.gridx = 1; add(txtDuracion, gbc);

        // Género
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Género:"), gbc);
        txtGenero = new JTextField(20);
        gbc.gridx = 1; add(txtGenero, gbc);

        // Aforo
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Aforo:"), gbc);
        txtAforo = new JTextField(20);
        gbc.gridx = 1; add(txtAforo, gbc);

        // Botón Crear
        JButton btnCrear = new JButton("Crear Película");
        gbc.gridx = 1; gbc.gridy = 5;
        add(btnCrear, gbc);

        btnCrear.addActionListener(e -> crearPelicula());
    }

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

        String sql = "INSERT INTO pelicula (titulo, anio, duracion, genero, aforo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titulo);
            ps.setInt(2, anio);
            ps.setInt(3, duracion);
            ps.setString(4, genero);
            ps.setInt(5, aforo);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Película creada con éxito");
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
    }

    public static void main(String[] args) {
        new CrearPelicula().setVisible(true);
    }
}
