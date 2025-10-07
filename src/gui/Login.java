package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class Login extends JFrame {

    public Login() {
        // Configuración de la ventana
        this.setLocationRelativeTo(null);
        this.setSize(450, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Deusto Cine - Iniciar sesión");

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridLayout(3, 2, 10, 10));

        // Componentes
        JLabel lblUsuario = new JLabel("Usuario");
        JLabel lblContrasenya = new JLabel("Contraseña");
        JTextField txtUsuario = new JTextField();
        JPasswordField txtContrasenya = new JPasswordField();
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        JButton btnRegistrarse = new JButton("Registrarse");

        // Añadir componentes al panel
        panelPrincipal.add(lblUsuario);
        panelPrincipal.add(txtUsuario);
        panelPrincipal.add(lblContrasenya);
        panelPrincipal.add(txtContrasenya);
        panelPrincipal.add(btnIniciarSesion);
        panelPrincipal.add(btnRegistrarse);

        this.add(panelPrincipal);

        // Acción para el botón Registrarse
        btnRegistrarse.addActionListener(e -> mostrarDialogRegistro());
    }

    // Método para mostrar el diálogo de selección
    private void mostrarDialogRegistro() {
        JDialog dialog = new JDialog(this, "Seleccionar tipo de registro", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Panel con las opciones
        JPanel panelOpciones = new JPanel(new GridLayout(2, 1));
        JRadioButton rbUsuario = new JRadioButton("Usuario");
        JRadioButton rbEntidad = new JRadioButton("Entidad");
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbUsuario);
        grupo.add(rbEntidad);
        panelOpciones.add(rbUsuario);
        panelOpciones.add(rbEntidad);

        // Botón continuar
        JButton btnContinuar = new JButton("Continuar");
        btnContinuar.addActionListener(ev -> {
            if (rbUsuario.isSelected()) {
                Registro registroUsuario = new Registro();
                registroUsuario.setVisible(true);
                dialog.dispose();
                this.dispose(); // cierra Login
            } else if (rbEntidad.isSelected()) {
                RegistroEntidad registroEntidad = new RegistroEntidad();
                registroEntidad.setVisible(true);
                dialog.dispose();
                this.dispose(); // cierra Login
            } else {
                JOptionPane.showMessageDialog(dialog, "Debes seleccionar una opción",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        dialog.add(panelOpciones, BorderLayout.CENTER);
        dialog.add(btnContinuar, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}
