package gui;

import javax.swing.*;
import dao.ClienteDAO;
import domain.Cliente;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; // Import necesario para listas

public class PerfilCliente extends JFrame {

    private String emailCliente;
    private JLabel lblNombre, lblEmail, lblTelefono, lblDireccion, lblFechaN;
    private Cliente cliente;
    private JButton btnVolver;
    private JLabel lblSaldo;

    public PerfilCliente(Cliente cliente) {
        this.cliente = cliente;

        // Configuración de la ventana
        setTitle("Deusto Cine - Perfil Cliente");
        setSize(500, 500); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); 
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
        lblFechaN = new JLabel("Fecha de nacimiento: ");
        lblSaldo = new JLabel("Saldo: ");

        // Color de texto uniforme
        Color colorTexto = new Color(60, 60, 60);
        lblNombre.setForeground(colorTexto);
        lblEmail.setForeground(colorTexto);
        lblTelefono.setForeground(colorTexto);
        lblDireccion.setForeground(colorTexto);
        lblFechaN.setForeground(colorTexto);
        lblSaldo.setForeground(colorTexto); // Aseguramos color al saldo también

        // Añadimos los labels
        add(lblNombre, gbc);
        gbc.gridy++;
        add(lblEmail, gbc);
        gbc.gridy++;
        add(lblTelefono, gbc);
        gbc.gridy++;
        add(lblDireccion, gbc);
        gbc.gridy++;
        add(lblFechaN, gbc);
        gbc.gridy++;
        add(lblSaldo, gbc);
        gbc.gridy++;

        // --- SECCIÓN DE BOTONES ---
        
        // Estilo común para botones
        Dimension buttonSize = new Dimension(180, 30);
        Color buttonColor = new Color(70, 130, 180);
        Color textColor = Color.WHITE;

        // 1. Botón Cerrar Sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(buttonColor);
        btnCerrarSesion.setForeground(textColor);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setPreferredSize(buttonSize);
        
        // 2. Botón Volver
        btnVolver = new JButton("Volver");
        btnVolver.setBackground(buttonColor);
        btnVolver.setForeground(textColor);
        btnVolver.setFocusPainted(false);
        btnVolver.setPreferredSize(buttonSize);

        // 3. Botón Recargar Saldo
        JButton btnRecargar = new JButton("Añadir saldo");
        btnRecargar.setBackground(buttonColor);
        btnRecargar.setForeground(textColor);
        btnRecargar.setFocusPainted(false);
        btnRecargar.setPreferredSize(buttonSize);

        // 4. NUEVO BOTÓN: Ver Películas Compradas
        JButton btnVerPeliculas = new JButton("Mis Películas");
        btnVerPeliculas.setBackground(new Color(60, 179, 113)); // Un verde para diferenciarlo, o usa buttonColor
        btnVerPeliculas.setForeground(textColor);
        btnVerPeliculas.setFocusPainted(false);
        btnVerPeliculas.setPreferredSize(buttonSize);

        // Añadir botones al layout (Centrados)
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Orden de botones
        gbc.gridy++;
        add(btnRecargar, gbc);
        
        gbc.gridy++;
        add(btnVerPeliculas, gbc); // Añadimos el nuevo botón aquí

        gbc.gridy++;
        add(btnVolver, gbc);
        
        gbc.gridy++;
        add(btnCerrarSesion, gbc);
        

        // --- ACCIONES DE LOS BOTONES ---

        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
        
        btnVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Principal p = new Principal(cliente);
                p.setVisible(true);
            }
        });
        
        btnRecargar.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(
                this,
                "Introduce el importe a recargar (€):"
            );

            if (input == null) return;

            try {
                double importe = Double.parseDouble(input);
                if (importe <= 0) throw new NumberFormatException();

                cliente.setSaldo(cliente.getSaldo() + importe);
                new ClienteDAO().actualizarSaldo(cliente);

                lblSaldo.setText("Saldo: " + String.format("%.2f €", cliente.getSaldo()));
                JOptionPane.showMessageDialog(this, "Saldo añadido correctamente");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Importe inválido");
            }
        });

        
        btnVerPeliculas.addActionListener(e -> {
            try {
                
                ClienteDAO dao = new ClienteDAO();
                
             
                List<String> peliculas = dao.obtenerPeliculasDeCliente(cliente.getEmail()); 
                
                if (peliculas == null || peliculas.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No has comprado ninguna película todavía.");
                } else {
                    // Convertimos la lista en un String bonito para mostrarlo
                    StringBuilder mensaje = new StringBuilder("Tus películas compradas:\n\n");
                    for (String peli : peliculas) {
                        mensaje.append("- ").append(peli).append("\n");
                    }
                    
                    // Mostramos la lista en un cuadro de diálogo con scroll por si son muchas
                    JTextArea textArea = new JTextArea(mensaje.toString());
                    textArea.setEditable(false);
                    textArea.setOpaque(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(350, 200));
                    
                    JOptionPane.showMessageDialog(this, scrollPane, "Historial de Compras", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al recuperar las películas: " + ex.getMessage());
            }
        });

        // Cargar los datos del cliente desde la base de datos
        cargarDatosCliente(cliente);
    }

    // Método que carga los datos del cliente desde la base de datos
    private void cargarDatosCliente(Cliente cliente) {
        lblNombre.setText("Nombre: " + cliente.getNombre());
        lblEmail.setText("Email: " + cliente.getEmail());
        lblTelefono.setText("Teléfono: " + cliente.getNumTelefono());
        lblDireccion.setText("Dirección: " + cliente.getDireccion());
        lblFechaN.setText("NIF: " + cliente.getFechaNacimiento());
        Double saldoObj = cliente.getSaldo();
        double saldo = (saldoObj != null) ? saldoObj : 0.0;
        lblSaldo.setText("Saldo: " + String.format("%.2f €", saldo));
    }
}