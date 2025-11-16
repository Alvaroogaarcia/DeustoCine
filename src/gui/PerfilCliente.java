package gui;

import javax.swing.*;

import dao.UsuarioDAO;
import domain.Cliente;
import domain.Usuario;

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
        getContentPane().setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Título
        JLabel titulo = new JLabel("Perfil del Cliente");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        // Labels de información
        lblNombre = new JLabel("Nombre: ");
        lblEmail = new JLabel("Email: ");
        lblTelefono = new JLabel("Teléfono: ");
        lblDireccion = new JLabel("Dirección: ");
        lblCP = new JLabel("Código Postal: ");

        // Color de texto uniforme
        Color colorTexto = new Color(60, 60, 60);
        lblNombre.setForeground(colorTexto);
        lblEmail.setForeground(colorTexto);
        lblTelefono.setForeground(colorTexto);
        lblDireccion.setForeground(colorTexto);
        lblCP.setForeground(colorTexto);

        // Añadimos los labels
        add(lblNombre, gbc);
        gbc.gridy++;
        add(lblEmail, gbc);
        gbc.gridy++;
        add(lblTelefono, gbc);
        gbc.gridy++;
        add(lblDireccion, gbc);
        gbc.gridy++;
        add(lblCP, gbc);
        gbc.gridy++;

        // Botón para cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(70, 130, 180));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setPreferredSize(new Dimension(150, 30));

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

        // Cargar los datos del cliente desde la base de datos
        cargarDatosCliente(email);
    }

    // Método que carga los datos del cliente desde la base de datos
    private void cargarDatosCliente(String email) {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.buscarPorEmail(email);

        if (u != null && u instanceof Cliente) {
            Cliente cliente = (Cliente) u;
            lblNombre.setText("Nombre: " + cliente.getNombre());
            lblEmail.setText("Email: " + cliente.getEmail());
            lblTelefono.setText("Teléfono: " + cliente.getNumTelefono());

            // La dirección se guardó como "direccion;codigoPostal"
            String direccionCompleta = cliente.getDireccion();
            if (direccionCompleta != null && direccionCompleta.contains(";")) {
                String[] partes = direccionCompleta.split(";", 2);
                lblDireccion.setText("Dirección: " + partes[0]);
                lblCP.setText("Código Postal: " + (partes.length > 1 ? partes[1] : "N/A"));
            } else {
                lblDireccion.setText("Dirección: " + (direccionCompleta != null ? direccionCompleta : "N/A"));
                lblCP.setText("Código Postal: N/A");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron datos para este cliente.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Código comentado original para cargar datos desde CSV
    /*
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
    */

    // Método main para pruebas
    public static void main(String[] args) {
        new PerfilCliente("cliente@correo.com").setVisible(true);
    }
}

