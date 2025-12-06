package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import dao.EntidadDAO;
import dao.UsuarioDAO;
import domain.Entidad;

public class RegistroEntidad extends JFrame {

    public RegistroEntidad() {
        // Configuración de la ventana
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Deusto Cine - Registro de Entidad");
        this.setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Registro de Entidad");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        // Campos del formulario
        JLabel lblNombre = new JLabel("Nombre de la Entidad:");
        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblNombre, gbc);
        JTextField txtNombre = new JTextField(20); gbc.gridx = 1; panel.add(txtNombre, gbc);

        JLabel lblEmail = new JLabel("Correo Electrónico:");
        gbc.gridx = 0; gbc.gridy = 2; panel.add(lblEmail, gbc);
        JTextField txtEmail = new JTextField(20); gbc.gridx = 1; panel.add(txtEmail, gbc);

        JLabel lblTelefono = new JLabel("Número de Teléfono:");
        gbc.gridx = 0; gbc.gridy = 3; panel.add(lblTelefono, gbc);
        JTextField txtTelefono = new JTextField(20); gbc.gridx = 1; panel.add(txtTelefono, gbc);

        JLabel lblDireccion = new JLabel("Dirección:");
        gbc.gridx = 0; gbc.gridy = 4; panel.add(lblDireccion, gbc);
        JTextField txtDireccion = new JTextField(20); gbc.gridx = 1; panel.add(txtDireccion, gbc);

        JLabel lblNIF = new JLabel("NIF:");
        gbc.gridx = 0; gbc.gridy = 5; panel.add(lblNIF, gbc);
        JTextField txtNIF = new JTextField(20); gbc.gridx = 1; panel.add(txtNIF, gbc);

        JLabel lblContrasenya = new JLabel("Contrasenya:");
        gbc.gridx = 0; gbc.gridy = 6; panel.add(lblContrasenya, gbc);
        JPasswordField txtContrasenya = new JPasswordField(20); gbc.gridx = 1; panel.add(txtContrasenya, gbc);

        JLabel lblConfirmarContrasenya = new JLabel("Confirmar Contrasenya:");
        gbc.gridx = 0; gbc.gridy = 7; panel.add(lblConfirmarContrasenya, gbc);
        JPasswordField txtConfirmarContrasenya = new JPasswordField(20); gbc.gridx = 1; panel.add(txtConfirmarContrasenya, gbc);

        // Botones
        JButton btnCrearCuenta = new JButton("Crear Cuenta");
        gbc.gridx = 1; gbc.gridy = 8; panel.add(btnCrearCuenta, gbc);

        JButton btnAtras = new JButton("Atrás");
        gbc.gridx = 0; gbc.gridy = 8; panel.add(btnAtras, gbc);

        this.add(panel);

        // Acción botón Atrás
        btnAtras.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        // Acción botón Crear Cuenta
        btnCrearCuenta.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String direccion = txtDireccion.getText().trim();
            String nif = txtNIF.getText().trim();
            String password = new String(txtContrasenya.getPassword());
            String confirmPassword = new String(txtConfirmarContrasenya.getPassword());

            // Validaciones
            if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty() || direccion.isEmpty() ||
                    nif.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@") || (!email.endsWith(".com") && !email.endsWith(".es"))) {
                JOptionPane.showMessageDialog(panel, "Ingrese un correo electrónico válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(panel, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!telefono.matches("\\d{10,}")) {
                JOptionPane.showMessageDialog(panel, "El número de teléfono debe contener al menos 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!nif.matches("[0-9A-Za-z]+")) {
                JOptionPane.showMessageDialog(panel, "Ingrese un NIF válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Registrar entidad en CSV
            registrarEntidad(nombre, password, email, telefono, direccion, nif);
            JOptionPane.showMessageDialog(panel, "Cuenta creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new Login().setVisible(true);
        });
    }

    // Método para guardar en CSV
//    private void registrarEntidad(String nombre, String password, String email, String telefono, String direccion, String nif) {
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/data/entidades.csv", true))) {
//            bw.write(nombre + ";" + password + ";" + email + ";" + telefono + ";" + direccion + ";" + nif);
//            bw.newLine();
//            bw.flush();
//            System.out.println("Entidad guardada en CSV");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    private void registrarEntidad(String nombre, String password, String email, String telefono, String direccion, String nif) {
        Entidad entidad = new Entidad(nombre, email, telefono, direccion, password, nif);
        EntidadDAO eDao = new EntidadDAO();
        boolean resultado = eDao.insertar(entidad);
        
        if (resultado) {
            System.out.println("Entidad guardada en BD correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error: El email ya existe o hubo un problema.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new RegistroEntidad().setVisible(true);
    }
}
