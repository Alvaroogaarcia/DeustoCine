package gui;

import javax.swing.*;

import dao.UsuarioDAO;
import database.DBInitializer;
import domain.Cliente;
import domain.Entidad;
import domain.Usuario;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Login extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbTipoUsuario;

    public Login() {
        setTitle("Deusto Cine - Iniciar Sesión");
        setSize(400, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        
        JLabel lblTipo = new JLabel("Tipo de usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTipo, gbc);
        
        cmbTipoUsuario = new JComboBox<>(new String[] {"Cliente", "Entidad"});
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(cmbTipoUsuario, gbc);
        
        JLabel lblEmail = new JLabel("Correo Electrónico:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblEmail, gbc);
        
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtEmail, gbc);
        
        JLabel lblPassword = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtPassword, gbc);
        
        JButton btnLogin = new JButton("Iniciar Sesión");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(btnLogin, gbc);

        JButton btnRegistrar = new JButton("Registrarse");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(btnRegistrar, gbc);
        
        // Acción del botón de iniciar sesión
        btnLogin.addActionListener(e -> {
            String tipo = (String) cmbTipoUsuario.getSelectedItem();
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

           // String filePath = tipo.equals("Cliente") ? "resources/data/usuarios.csv" : "resources/data/entidades.csv";
            Usuario u = loginBD( email, password);
            if (u != null) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso como " + tipo + ".");
                dispose();

                if (u instanceof Cliente){
//                    new PerfilCliente(email).setVisible(true);
                	new Principal(u).setVisible(true);
                } else {
                    new PerfilEntidad((Entidad)u).setVisible(true);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Botón de registro
        btnRegistrar.addActionListener(e -> {
            String tipo = (String) cmbTipoUsuario.getSelectedItem();
            dispose();
            if (tipo.equals("Cliente")) {
                new Registro().setVisible(true);
            } else {
                new RegistroEntidad().setVisible(true);
            }
        });
    }
    
    private Usuario loginBD(String email, String password) {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.buscarPorEmail(email);

        if (u == null) return null;

     
        return u.getContrasenya().equals(password)? u : null;
    }


    private boolean login(String filePath, String email, String password) {
        File file = new File(filePath);
        if (!file.exists()) return false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    String storedEmail = parts[2];
                    String storedPassword = parts[1];
                    if (storedEmail.equals(email) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        String dbPath = "resources/data/deustocine.sqlite";
        File dbFile = new File(dbPath);

        // Si NO existe la BD -> inicializamos
        if (!dbFile.exists()) {
            System.out.println("Base de datos no encontrada. Creando...");
            DBInitializer.initialize();
        } else {
            System.out.println("Base de datos encontrada");
        }

        // Mostrar login
        new Login().setVisible(true);
    }

}
