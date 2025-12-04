package gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DBConnection;
import dao.PeliculaDAO;
import domain.Pelicula;

public class CrearSesion extends JFrame {

    private JPanel panel;
    private JComboBox<Pelicula> cmbPeliculas;
    private JSpinner spnFecha;
    private JSpinner spnHora;
    private JComboBox<String> cmbSala;

    public CrearSesion() {

        // Ventana
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("Crear Sesión");
        this.setResizable(false);

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Título
        JLabel lblTitulo = new JLabel("Crear Sesión de Cine");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

     // --- Película ---
        JLabel lblPelicula = new JLabel("Película:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(lblPelicula, gbc);
        cmbPeliculas = new JComboBox<>(); 
        cargarPeliculas(); gbc.gridx = 1; panel.add(cmbPeliculas, gbc);
        gbc.gridx = 1; panel.add(cmbPeliculas, gbc);

        // --- Fecha ---
        JLabel lblFecha = new JLabel("Fecha de la sesión:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(lblFecha, gbc);
        spnFecha = new JSpinner(new SpinnerDateModel());
        spnFecha.setEditor(new JSpinner.DateEditor(spnFecha, "yyyy-MM-dd"));
        spnFecha.setValue(new Date());
        gbc.gridx = 1;
        panel.add(spnFecha, gbc);
       

        // --- Hora ---
        JLabel lblHora = new JLabel("Hora de la sesión:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(lblHora, gbc);

        spnHora = new JSpinner(new SpinnerDateModel());
        spnHora.setEditor(new JSpinner.DateEditor(spnHora, "HH:mm"));
        spnHora.setValue(new Date());
        gbc.gridx = 1;
        panel.add(spnHora, gbc);

        

        // --- Sala ---
        JLabel lblSala = new JLabel("Seleccione la sala:");
        gbc.gridx = 0; gbc.gridy = 4; 
        panel.add(lblSala, gbc); 
        cmbSala = new JComboBox<>(new String[]{"Sala 1", "Sala 2", "Sala 3", "Sala 4", "Sala 5"}); 
        gbc.gridx = 1; panel.add(cmbSala, gbc);
         
        
        
        

        // --- Botones ---
        JButton btnCrear = new JButton("Crear Sesión");
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(btnCrear, gbc);

        JButton btnAtras = new JButton("Atrás");
        gbc.gridx = 0;
        panel.add(btnAtras, gbc);

        this.add(panel);

        // Eventos
        btnAtras.addActionListener(e -> dispose());
        btnCrear.addActionListener(e -> crearSesion());
    }

    // Cargar películas y mostrar solo su nombre
    private void cargarPeliculas() {
        cmbPeliculas.addItem(new Pelicula(-1, "Seleccione película"));

        PeliculaDAO dao = new PeliculaDAO();
        List<Pelicula> lista = dao.listar();

        for (Pelicula p : lista) {
            cmbPeliculas.addItem(p);
        }
    }

    private void crearSesion() {

        Pelicula pelicula = (Pelicula) cmbPeliculas.getSelectedItem();

        if (pelicula == null || pelicula.getId() == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una película.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Fecha
        Date fechaDate = (Date) spnFecha.getValue();
        String fechaStr = new SimpleDateFormat("yyyy-MM-dd").format(fechaDate);

        // Hora
        Date horaDate = (Date) spnHora.getValue();
        String horaStr = new SimpleDateFormat("HH:mm").format(horaDate);

        String sala = (String) cmbSala.getSelectedItem();

        String sql = "INSERT INTO sesion (id_Pelicula, fecha, hora, sala) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pelicula.getId());
            ps.setString(2, fechaStr);
            ps.setString(3, horaStr);
            ps.setString(4, sala);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Sesión creada correctamente.");

            limpiarCampos();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear la sesión.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        cmbPeliculas.setSelectedIndex(0);
        cmbSala.setSelectedIndex(0);
        spnFecha.setValue(new Date());
        spnHora.setValue(new Date());
    }

    public static void main(String[] args) {
        new CrearSesion().setVisible(true);
    }
}
