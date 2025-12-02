package gui;

import javax.swing.*;
import domain.Entidad;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PerfilEntidad extends JFrame {
    
    private String emailEntidad;
    private Entidad entidad;
    private JLabel lblNombre, lblEmail, lblTelefono, lblDireccion, lblNif;

    public PerfilEntidad(Entidad entidad) {
        this.emailEntidad = entidad.getEmail();
        this.entidad = entidad;

        // Configuración de la ventana
        setTitle("Deusto Cine - Perfil Entidad");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Usamos BorderLayout
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // PANEL DE DATOS
        // =========================
        JPanel panelDatos = new JPanel(new GridBagLayout());
        panelDatos.setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        int fila = 0;

        // Título
        JLabel titulo = new JLabel("Perfil de Entidad");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(50, 50, 50));

        gbc.gridx = 0;
        gbc.gridy = fila++;
        panelDatos.add(titulo, gbc);

        // Labels
        lblNombre = new JLabel("Nombre: ");
        lblEmail = new JLabel("Email: ");
        lblTelefono = new JLabel("Teléfono: ");
        lblDireccion = new JLabel("Dirección: ");
        lblNif = new JLabel("NIF: ");

        Color colorTexto = new Color(60, 60, 60);
        lblNombre.setForeground(colorTexto);
        lblEmail.setForeground(colorTexto);
        lblTelefono.setForeground(colorTexto);
        lblDireccion.setForeground(colorTexto);
        lblNif.setForeground(colorTexto);

        gbc.gridy = fila++; panelDatos.add(lblNombre, gbc);
        gbc.gridy = fila++; panelDatos.add(lblEmail, gbc);
        gbc.gridy = fila++; panelDatos.add(lblTelefono, gbc);
        gbc.gridy = fila++; panelDatos.add(lblDireccion, gbc);
        gbc.gridy = fila++; panelDatos.add(lblNif, gbc);

        // Añadir panelDatos arriba
        add(panelDatos, BorderLayout.NORTH);

        // PANEL DE BOTONES
        // =========================
        JButton btnCrearPelicula = new JButton("Crear Película");
        JButton btnCrearSesion = new JButton("Crear Sesión de Cine");
        JButton btnCrearDescuentos = new JButton("Crear Descuento");
        JButton btnVerPeliculas = new JButton("Ver Películas");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(new Color(230, 230, 230));
        panelBotones.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        Color azul = new Color(70, 130, 180);
        Color blanco = Color.WHITE;

        for (JButton b : new JButton[] { 
            btnCrearPelicula, btnCrearSesion, btnCrearDescuentos, btnVerPeliculas, btnCerrarSesion 
        }) {
            b.setBackground(azul);
            b.setForeground(blanco);
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(150, 30));
        }

        panelBotones.add(btnCrearPelicula);
        panelBotones.add(btnCrearSesion);
        panelBotones.add(btnCrearDescuentos);
        panelBotones.add(btnVerPeliculas);
        panelBotones.add(btnCerrarSesion);

        panelBotones.setBorder(BorderFactory.createTitledBorder("Acciones"));

        // AÑADIR EL PANEL DE BOTONES AL CENTRO (CORRECTO)
        add(panelBotones, BorderLayout.CENTER);

        // =========================
        // Cargar datos
        // =========================
        cargarDatosEntidad();

        // Accion para crear película 
        btnCrearPelicula.addActionListener(e -> {
            CrearPelicula c = new CrearPelicula(entidad);
            c.setVisible(true);
        });

        // Accion para crear sesión 
        btnCrearSesion.addActionListener(e -> {
            CrearSesion cs = new CrearSesion();
            cs.setVisible(true);
        });
        
        btnCrearDescuentos.addActionListener(e -> {
            Descuento d = new Descuento();
            d.setVisible(true);
        });

        // Accion para ver las peliculas
        btnVerPeliculas.addActionListener(e -> {
            new TablaPeliculasEntidad().setVisible(true);
        });

        // Cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
    }
    
    // Metodo carga datos
    private void cargarDatosEntidad() {
        lblNombre.setText("Nombre: " + entidad.getNombre());
        lblEmail.setText("Email: " + entidad.getEmail());
        lblTelefono.setText("Teléfono: " + entidad.getNumTelefono());
        lblDireccion.setText("Dirección: " + entidad.getDireccion());
        lblNif.setText("NIF: " + entidad.getNif());
    }
}


   


