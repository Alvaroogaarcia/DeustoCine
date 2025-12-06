package gui;

import javax.swing.*;

import dao.ClienteDAO;
import dao.EntidadDAO;
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
    	
    	//Configuracion de la ventana
        setTitle("Deusto Cine - Iniciar Sesión");
        setSize(400, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        
        //Utilizamos Gridbagcontraints para organizar la ventana
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
        
        txtEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String email = txtEmail.getText().trim();
                
                if (email.isEmpty()) {
                    txtEmail.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                } else if (email.contains("@") && email.indexOf("@") < email.lastIndexOf(".")) {
                    txtEmail.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
                } else {
                    txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                }
            }
        });
        
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
        
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnLogin.doClick(); 
                }
            }
        });

        JButton btnRegistrar = new JButton("Registrarse");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(btnRegistrar, gbc);
        
        // Acción del boton de iniciar sesión
        btnLogin.addActionListener(e -> {
            String tipo = (String) cmbTipoUsuario.getSelectedItem();
            String email = txtEmail.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

      
            Usuario u = loginBD(email, password, tipo);
            if (u != null) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso como " + tipo + ".");
                dispose();

                if (u instanceof Cliente){
                    new Principal(u).setVisible(true);
                } else {
                    new PerfilEntidad((Entidad) u).setVisible(true);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        });

        // Accion del boton de registro
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
    
    // Metodo que valida el inicio de sesion desde la base de datos
    private Usuario loginBD(String email, String password, String tipo) {
        if (tipo.equals("Cliente")) {
            ClienteDAO cDao = new ClienteDAO();
            Cliente c = cDao.buscarPorEmail(email); // Método que debes crear en ClienteDao
            if (c != null && c.getContrasenya().equals(password)) {
                return c;
            }
        } else { // Entidad
            EntidadDAO eDao = new EntidadDAO();
            Entidad e = eDao.buscarPorEmail(email); // Método que debes crear en EntidadDao
            if (e != null && e.getContrasenya().equals(password)) {
                return e;
            }
        }
        return null;
    }





    public static void main(String[] args) {
        String dbPath = "resources/data/deustocine.sqlite";
        File dbFile = new File(dbPath);

        // Si no existe la BD, la inicializamos
        if (!dbFile.exists()) {
            System.out.println("Base de datos no encontrada. Creando...");
            DBInitializer.initialize();
        } else {
            System.out.println("Base de datos encontrada");
        }

        
        new Login().setVisible(true);
    }

}
