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
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Título
        JLabel titulo = new JLabel("Perfil de Entidad");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Labels de datos
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

        // Añadimos los labels al panel
        add(lblNombre, gbc);
        gbc.gridy++;
        add(lblEmail, gbc);
        gbc.gridy++;
        add(lblTelefono, gbc);
        gbc.gridy++;
        add(lblDireccion, gbc);
        gbc.gridy++;
        add(lblNif, gbc);
        gbc.gridy++;

        // Botones
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

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(panelBotones, gbc);

        // Cargar datos desde la BD
        cargarDatosEntidad();

        // Accion para crear pelicula 
        btnCrearPelicula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearPelicula c = new CrearPelicula(entidad);
                c.setVisible(true);
            }
        });

        // Accion para crear sesión 
        btnCrearSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearSesion cs = new CrearSesion();
                cs.setVisible(true);
            }
        });

        // Accion para ver las peliculas
        btnVerPeliculas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TablaPeliculasEntidad().setVisible(true);
            }
        });

        // Cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
    }
    
    //Metodo que carga los datos de la entidad desde la base de datos
    private void cargarDatosEntidad() {
        lblNombre.setText("Nombre: " + entidad.getNombre());
        lblEmail.setText("Email: " + entidad.getEmail());
        lblTelefono.setText("Teléfono: " + entidad.getNumTelefono());
        lblDireccion.setText("Dirección: " + entidad.getDireccion());
        lblNif.setText("NIF: " + entidad.getNif());
    }
}


//    private void cargarDatosEntidad(String email) {
//        File file = new File("resources/data/entidades.csv");
//        if (!file.exists()) {
//            JOptionPane.showMessageDialog(this, "No se encontró el archivo de entidades.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String linea;
//            while ((linea = br.readLine()) != null) {
//                String[] datos = linea.split(";");
//                if (datos.length >= 6 && datos[2].equals(email)) {
//                    lblNombre.setText("Nombre: " + datos[0]);
//                    lblEmail.setText("Email: " + datos[2]);
//                    lblTelefono.setText("Teléfono: " + datos[3]);
//                    lblDireccion.setText("Dirección: " + datos[4]);
//                    lblNif.setText("NIF: " + datos[5]);
//                    return;
//                }
//            }
//            JOptionPane.showMessageDialog(this, "No se encontraron datos para esta entidad.", "Aviso", JOptionPane.WARNING_MESSAGE);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

   


