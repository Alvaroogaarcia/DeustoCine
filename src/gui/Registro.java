package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import dao.UsuarioDAO;
import domain.Cliente;

import javax.swing.SpinnerDateModel;

public class Registro extends JFrame {
	
	private JPanel panel;

    public Registro() {
        // Configuración de la ventana
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Deusto Cine - Registro de Usuario");
        this.setResizable(false);

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Título
        JLabel lblTitulo = new JLabel("Registro de Usuario");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        // Campos del formulario
        JLabel lblUsuario = new JLabel("Nombre de Usuario:");
        gbc.gridx = 0; gbc.gridy = 1; panel.add(lblUsuario, gbc);
        JTextField txtUser = new JTextField(20); gbc.gridx = 1; panel.add(txtUser, gbc);

        JLabel lblEmail = new JLabel("Correo Electrónico:");
        gbc.gridx = 0; gbc.gridy = 2; panel.add(lblEmail, gbc);
        JTextField txtEmail = new JTextField(20); gbc.gridx = 1; panel.add(txtEmail, gbc);

        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
        gbc.gridx = 0; gbc.gridy = 3; panel.add(lblFechaNacimiento, gbc);
        JSpinner spinnerNacimiento = new JSpinner(new SpinnerDateModel());
        spinnerNacimiento.setEditor(new JSpinner.DateEditor(spinnerNacimiento, "dd-MM-yyyy"));
        spinnerNacimiento.setValue(new Date());
        gbc.gridx = 1; panel.add(spinnerNacimiento, gbc);

        JLabel lblTelefono = new JLabel("Número de Teléfono:");
        gbc.gridx = 0; gbc.gridy = 4; panel.add(lblTelefono, gbc);
        JTextField txtTelefono = new JTextField(20); gbc.gridx = 1; panel.add(txtTelefono, gbc);

        JLabel lblDireccion = new JLabel("Dirección:");
        gbc.gridx = 0; gbc.gridy = 5; panel.add(lblDireccion, gbc);
        JTextField txtDireccion = new JTextField(20); gbc.gridx = 1; panel.add(txtDireccion, gbc);

        JLabel lblCodigoPostal = new JLabel("Código Postal:");
        gbc.gridx = 0; gbc.gridy = 6; panel.add(lblCodigoPostal, gbc);
        JTextField txtCodigoPostal = new JTextField(20); gbc.gridx = 1; panel.add(txtCodigoPostal, gbc);

        JLabel lblContrasenya = new JLabel("Contraseña:");
        gbc.gridx = 0; gbc.gridy = 7; panel.add(lblContrasenya, gbc);
        JPasswordField txtContrasenya = new JPasswordField(20); gbc.gridx = 1; panel.add(txtContrasenya, gbc);

        JLabel lblConfirmarContrasenya = new JLabel("Confirmar Contraseña:");
        gbc.gridx = 0; gbc.gridy = 8; panel.add(lblConfirmarContrasenya, gbc);
        JPasswordField txtConfirmarContrasenya = new JPasswordField(20); gbc.gridx = 1; panel.add(txtConfirmarContrasenya, gbc);

        // Botones
        JButton btnCrearCuenta = new JButton("Crear Cuenta");
        gbc.gridx = 1; gbc.gridy = 9; panel.add(btnCrearCuenta, gbc);

        JButton btnAtras = new JButton("Atrás");
        gbc.gridx = 0; gbc.gridy = 9; panel.add(btnAtras, gbc);

        this.add(panel);

        // Acción botón Atrás
        btnAtras.addActionListener(e -> {
            new Login().setVisible(true);
            dispose();
        });

        // Acción botón Crear Cuenta
        btnCrearCuenta.addActionListener(e -> {
            String username = txtUser.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtTelefono.getText().trim();
            String address = txtDireccion.getText().trim();
            String postalCode = txtCodigoPostal.getText().trim();
            String password = new String(txtContrasenya.getPassword());
            String confirmPassword = new String(txtConfirmarContrasenya.getPassword());
            Date date = (Date) spinnerNacimiento.getValue();

            LocalDate birthDate = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();


            // Validaciones básicas
            if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() ||
                    postalCode.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
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

            if (!phone.matches("\\d{9,}")) {
                JOptionPane.showMessageDialog(panel, "El número de teléfono debe contener al menos 9 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Integer.parseInt(postalCode);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "El código postal debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calcular edad
          
            int year = LocalDate.now().getYear();
            int age = Calendar.getInstance().get(Calendar.YEAR) - year;

            if (age < 18) {
                // Menor de edad → pedir consentimiento
                showConsentDialog(username, password, email, phone, address, postalCode, birthDate);
            } else {
                // Mayor de edad → guardar directo
                registerUser(username, password, email, phone, address, postalCode, birthDate);
                
                dispose();
                new Login().setVisible(true);
            }
        });
    }

    // Guardar usuario en CSV
    private void registerUser(String username, String password, String email, String phone, String address, String postalCode, LocalDate birthDate) {
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("resources/data/usuarios.csv", true))) {
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(birthDate);
//            String fechaStr = String.format("%02d-%02d-%04d", cal.get(Calendar.DAY_OF_MONTH),
//                    cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
//
//            bw.write(username + ";" + password + ";" + email + ";" + phone + ";" + address + ";" + postalCode + ";" + fechaStr);
//            bw.newLine();
//            bw.flush();
//            System.out.println("Usuario guardado en CSV");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//            Calendar cal = Calendar.getInstance();
//    	String fechaStr = String.format("%02d-%02d-%04d", cal.get(Calendar.DAY_OF_MONTH),
//    			   cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
    	
    	Cliente cliente = new Cliente(username, email,  phone, address+";"+postalCode,  password,  birthDate.toString());
    	UsuarioDAO uDao = new UsuarioDAO();
    	boolean resultado = uDao.insertar(cliente);
    	
    	if (resultado == true) {
    		JOptionPane.showMessageDialog(panel, "Cuenta creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    	}else {
    		JOptionPane.showMessageDialog(panel, "Error al crear la cuenta.", "Fallo", JOptionPane.WARNING_MESSAGE);
    	}
    	
    	
    	
    }

    // Diálogo de consentimiento para menores
    private void showConsentDialog(String username, String password, String email, String phone, String address, String postalCode, LocalDate birthDate) {
        JDialog consentDialog = new JDialog(this, "Consentimiento del Tutor Legal", true);
        consentDialog.setSize(600, 220);
        consentDialog.setLayout(new BorderLayout());

        JTextArea termsArea = new JTextArea(
                "Autorización para el uso de la cuenta:\n\n" +
                        "1. El tutor legal autoriza al menor a crear y usar una cuenta en esta plataforma.\n" +
                        "2. El menor se compromete a utilizar la cuenta de manera responsable y adecuada.\n" +
                        "3. No se permitirá el uso de lenguaje ofensivo ni el incumplimiento de las normas de la plataforma.\n\n" +
                        "Al marcar la casilla, el tutor legal acepta los términos y condiciones y autoriza al menor a usar la cuenta."
        );
        termsArea.setLineWrap(true);
        termsArea.setWrapStyleWord(true);
        termsArea.setEditable(false);
        termsArea.setBackground(consentDialog.getBackground());
        termsArea.setMargin(new Insets(10, 10, 10, 10));
        consentDialog.add(termsArea, BorderLayout.CENTER);

        JPanel consentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JCheckBox consentCheckBox = new JCheckBox("Confirmo que soy el tutor legal y acepto los términos.");
        consentPanel.add(consentCheckBox);

        JButton acceptButton = new JButton("Aceptar");
        acceptButton.setPreferredSize(new Dimension(110, 35));
        acceptButton.addActionListener(e -> {
            if (consentCheckBox.isSelected()) {
                // Guardar usuario en CSV
                registerUser(username, password, email, phone, address, postalCode, birthDate);
                JOptionPane.showMessageDialog(consentDialog, "Cuenta creada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                consentDialog.dispose();
                dispose();
                new Login().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(consentDialog, "Debe aceptar los términos para continuar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        consentPanel.add(acceptButton);
        consentDialog.add(consentPanel, BorderLayout.SOUTH);

        consentDialog.setLocationRelativeTo(this);
        consentDialog.setVisible(true);
    }

    public static void main(String[] args) {
        new Registro().setVisible(true);
    }
}
