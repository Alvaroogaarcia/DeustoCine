package gui;

import javax.swing.*;

import domain.Entidad;
import domain.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class PerfilEntidad extends JFrame {
    
    private String emailEntidad;
    private Entidad entidad;
    private JLabel lblNombre, lblEmail, lblTelefono, lblDireccion, lblNif;

    public PerfilEntidad(Entidad entidad) {
        this.emailEntidad = entidad.getEmail();
        this.entidad =  entidad;

        // Configuración de la ventana
        setTitle("Deusto Cine - Perfil Entidad");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titulo = new JLabel("Perfil de Entidad");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        lblNombre = new JLabel("Nombre: ");
        add(lblNombre, gbc);
        gbc.gridy++;

        lblEmail = new JLabel("Email: ");
        add(lblEmail, gbc);
        gbc.gridy++;

        lblTelefono = new JLabel("Teléfono: ");
        add(lblTelefono, gbc);
        gbc.gridy++;

        lblDireccion = new JLabel("Dirección: ");
        add(lblDireccion, gbc);
        gbc.gridy++;

        lblNif = new JLabel("NIF: ");
        add(lblNif, gbc);
        gbc.gridy++;
        
        JButton btnCrearPelicula = new JButton("Crear Pelicula");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCrearPelicula, gbc);

        JButton btnCrearSesion = new JButton("Crear Sesión de Cine");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCrearSesion, gbc);

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        gbc.gridy++;
        add(btnCerrarSesion, gbc);

        // Cargar datos desde la BD
        cargarDatosEntidad();
        
     // Acción para crear pelicula (más adelante abrirá una nueva ventana)
        btnCrearPelicula.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				CrearPelicula c = new CrearPelicula();
				c.setVisible(true);
				
			}
		});

        // Acción para crear sesión (más adelante abrirá una nueva ventana)
        btnCrearSesion.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrir ventana para crear sesión de cine...");
        });

        // Cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
    }
    
    private void cargarDatosEntidad() {
    	lblNombre.setText("Nombre: " + entidad.getNombre());
      lblEmail.setText("Email: " + entidad.getEmail());
      lblTelefono.setText("Teléfono: " + entidad.getNumTelefono());
      lblDireccion.setText("Dirección: " + entidad.getDireccion());
      lblNif.setText("NIF: " + entidad.getNif());
      return;
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

   

}
