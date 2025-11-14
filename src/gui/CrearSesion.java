package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import database.DBConnection;
import dao.PeliculaDAO;
import domain.Pelicula;
import domain.Sesion;

public class CrearSesion extends JFrame {

    private JComboBox<Pelicula> cmbPeliculas;
    private JTextField txtFecha, txtHora, txtSala;

    public CrearSesion() {
    	
    	//Configuracion de la ventana
        setTitle("Crear Sesión");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

     // Utilizamos GridBagContraints para organizar la ventana
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // JLabels
        JLabel lblPelicula = new JLabel("Película:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblPelicula, gbc);

        cmbPeliculas = new JComboBox<>();
        gbc.gridx = 1;
        add(cmbPeliculas, gbc);
        cargarPeliculas();

        
        JLabel lblFecha = new JLabel("Fecha (YYYY-MM-DD):");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblFecha, gbc);

        txtFecha = new JTextField(20);
        gbc.gridx = 1;
        add(txtFecha, gbc);

        
        JLabel lblHora = new JLabel("Hora (HH:MM):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblHora, gbc);

        txtHora = new JTextField(20);
        gbc.gridx = 1;
        add(txtHora, gbc);

        
        JLabel lblSala = new JLabel("Sala:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblSala, gbc);

        txtSala = new JTextField(20);
        gbc.gridx = 1;
        add(txtSala, gbc);

        // Botón Crear sesion
        JButton btnCrear = new JButton("Crear Sesión");
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(btnCrear, gbc);

        btnCrear.addActionListener(e -> crearSesion());
    }

    //Metodo que carga las peliculas
    private void cargarPeliculas() {
        PeliculaDAO dao = new PeliculaDAO();
        List<Pelicula> peliculas = dao.listar();
        for (Pelicula p : peliculas) {
            cmbPeliculas.addItem(p);
        }
    }

    //Metodo que crea la sesion de cine
    private void crearSesion() {
        Pelicula pelicula = (Pelicula) cmbPeliculas.getSelectedItem();
        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();
        String sala = txtSala.getText().trim();

        if (pelicula == null || fecha.isEmpty() || hora.isEmpty() || sala.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear objeto Sesion
        Sesion sesion = new Sesion(pelicula.getId(), fecha, hora, sala);

        // Guardar en base de datos
        String sql = "INSERT INTO sesion (idPelicula, fecha, hora, sala) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sesion.getIdPelicula());
            ps.setString(2, sesion.getFecha());
            ps.setString(3, sesion.getHora());
            ps.setString(4, sesion.getSala());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Sesión creada con éxito");
            limpiarCampos();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar la sesión", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Metodo que limpia los campos una vez creada la sesion de cine
    private void limpiarCampos() {
        txtFecha.setText("");
        txtHora.setText("");
        txtSala.setText("");
    }

    public static void main(String[] args) {
        new CrearSesion().setVisible(true);
    }
}
