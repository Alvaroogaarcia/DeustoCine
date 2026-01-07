package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import dao.ClienteDAO;
import dao.EntidadDAO;
import database.DBInitializer;
import domain.Cliente;
import domain.Entidad;

public class Login extends JFrame {

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbTipoUsuario;

    public Login() {
        
        // --- CONFIGURACIÓN DE LA VENTANA ---
        setTitle("Deusto Cine - Iniciar Sesión");
        setSize(400, 320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        // Configuración del GridBag para organizar los elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Márgenes
        
        // --- FILA 0: TIPO DE USUARIO ---
        JLabel lblTipo = new JLabel("Tipo de usuario:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblTipo, gbc);
        
        cmbTipoUsuario = new JComboBox<>(new String[] {"Cliente", "Entidad"});
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(cmbTipoUsuario, gbc);
        
        // --- FILA 1: EMAIL ---
        JLabel lblEmail = new JLabel("Correo Electrónico:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblEmail, gbc);
        
        txtEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtEmail, gbc);
        
        // Validación visual del email 
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
        
        // --- FILA 2: CONTRASEÑA ---
        JLabel lblPassword = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblPassword, gbc);
        
        txtPassword = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(txtPassword, gbc);
        
        // Listener para que al pulsar ENTER en la contraseña se haga click en el botón
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                   
                }
            }
        });

        // --- FILA 3: BOTONES ---
        JButton btnLogin = new JButton("Iniciar Sesión");
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(btnLogin, gbc);
        
        // Asignamos la acción del ENTER al botón ahora que está creado
        getRootPane().setDefaultButton(btnLogin);

        JButton btnRegistrar = new JButton("Registrarse");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(btnRegistrar, gbc);
        
        
        
        // LÓGICA PRINCIPAL DEL LOGIN
     
        btnLogin.addActionListener(e -> {
            String emailIngresado = txtEmail.getText().trim();
            String contrasenaIngresada = new String(txtPassword.getPassword());
            String tipoUsuario = (String) cmbTipoUsuario.getSelectedItem(); 

            // 1. CASO CLIENTE
            if (tipoUsuario.equals("Cliente")) {
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = clienteDAO.buscarPorEmail(emailIngresado);

                if (cliente != null && cliente.getContrasenya().equals(contrasenaIngresada)) {
                    // Login correcto: abrimos ventana principal de Clientes
                    Principal principal = new Principal(cliente);
                    principal.setVisible(true);
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente o contraseña incorrectos", "Error de Login", JOptionPane.ERROR_MESSAGE);
                }

            } 
            // 2. CASO ENTIDAD
            else {
                EntidadDAO entidadDAO = new EntidadDAO();
                Entidad entidad = entidadDAO.buscarPorEmail(emailIngresado);

                if (entidad != null && entidad.getContrasenya().equals(contrasenaIngresada)) {
                    // Login correcto: abrimos PerfilEntidad pasando el objeto entidad
                    PerfilEntidad perfil = new PerfilEntidad(entidad);
                    perfil.setVisible(true);
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(this, "Entidad o contraseña incorrectos", "Error de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

       
        // LÓGICA DE REGISTRO
        
        btnRegistrar.addActionListener(e -> {
            String tipo = (String) cmbTipoUsuario.getSelectedItem();
            dispose(); // Cerramos login
            
            if (tipo.equals("Cliente")) {
                new Registro().setVisible(true);
            } else {
                new RegistroEntidad().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        // Inicialización de la BD al arrancar
        String dbPath = "resources/data/deustocine.sqlite";
        File dbFile = new File(dbPath);

        if (!dbFile.exists()) {
            System.out.println("Base de datos no encontrada. Creando...");
            DBInitializer.initialize();
        } else {
            System.out.println("Base de datos encontrada. Iniciando aplicación...");
        }

        // Ejecutar la ventana en el hilo de eventos de Swing
        EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}