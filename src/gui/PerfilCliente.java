package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class PerfilCliente extends JFrame {

    private String emailCliente;
    private JLabel lblNombre, lblEmail, lblTelefono, lblDireccion, lblCP;

    public PerfilCliente(String email) {
        this.emailCliente = email;

        // Configuración de la ventana
        setTitle("Deusto Cine - Perfil Cliente");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Título
        JLabel titulo = new JLabel("Perfil del Cliente");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Campos de información
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

        lblCP = new JLabel("Código Postal: ");
        add(lblCP, gbc);
        gbc.gridy++;

        // Botón para cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCerrarSesion, gbc);

        // Acción del botón
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        // Cargar los datos del cliente
        cargarDatosCliente(email);
    }

    /**
     * Carga los datos del cliente desde el archivo CSV en base a su correo.
     */
    private void cargarDatosCliente(String email) {
        File file = new File("resources/data/usuarios.csv");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo de usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos.length >= 6 && datos[2].equals(email)) {
                    lblNombre.setText("Nombre: " + datos[0]);
                    lblEmail.setText("Email: " + datos[2]);
                    lblTelefono.setText("Teléfono: " + datos[3]);
                    lblDireccion.setText("Dirección: " + datos[4]);
                    lblCP.setText("Código Postal: " + datos[5]);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "No se encontraron datos para este cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método main solo para pruebas
    public static void main(String[] args) {
        new PerfilCliente("cliente@correo.com").setVisible(true);
    }
}
