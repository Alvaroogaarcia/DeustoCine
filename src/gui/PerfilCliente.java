package gui;

import javax.swing.*;

import dao.ClienteDAO;
import dao.UsuarioDAO;
import domain.Cliente;
import domain.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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
        lblFechaN = new JLabel("Fecha de nacimiento ");
        lblSaldo = new JLabel("Saldo: ");
       

        
        

        // Color de texto uniforme
        Color colorTexto = new Color(60, 60, 60);
        lblNombre.setForeground(colorTexto);
        lblEmail.setForeground(colorTexto);
        lblTelefono.setForeground(colorTexto);
        lblDireccion.setForeground(colorTexto);
        lblFechaN.setForeground(colorTexto);
        
        

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

        // Botones cerrar sesión y volver
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(70, 130, 180));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setPreferredSize(new Dimension(150, 30));
        
        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(70, 130, 180));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);
        btnVolver.setPreferredSize(new Dimension(150, 30));

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnCerrarSesion, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnVolver, gbc);
        
        JButton btnRecargar = new JButton("Añadir saldo");
        add(btnRecargar);

        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnRecargar, gbc);

        // Acción del botón
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
        
        btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
        lblSaldo.setText(String.format("%.2f €", saldo));



    }

   

}

