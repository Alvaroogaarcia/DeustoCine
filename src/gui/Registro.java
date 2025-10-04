package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;


public class Registro extends JFrame{

	public Registro() {
		// TODO Auto-generated constructor stub
		
		// Configuracion de la ventana
		this.setSize(600,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Deusto Cine - Registro");
		this.setResizable(false);
		
		// Configuracion del panel principal
		 JPanel panel = new JPanel();
	     panel.setLayout(new GridBagLayout());
	     GridBagConstraints gbc = new GridBagConstraints();
	     gbc.insets = new Insets(8, 8, 8, 8);
	     
	     // Titulo de la ventana
	     JLabel lblTitulo = new JLabel("Registro de Usuario");
	     gbc.gridx = 0;
	     gbc.gridy = 0;
	     gbc.gridwidth = 2; 
	     panel.add(lblTitulo, gbc);

	     // Ajustar el GridBagConstraints para el resto de componentes
	     gbc.gridwidth = 1; 

	     // Etiqueta y campo para el nombre de usuario
	     JLabel lblUsuario = new JLabel("Nombre de Usuario:");
	     gbc.gridx = 0;
	     gbc.gridy = 1;
	     panel.add(lblUsuario, gbc);

	     JTextField txtUser = new JTextField(20);
	     txtUser.setPreferredSize(new Dimension(100,25));
	     gbc.gridx = 1;
	     gbc.gridy = 1;
	     panel.add(txtUser, gbc);

	     // Etiqueta y campo para el correo electrónico
	     JLabel lblEmail = new JLabel("Correo Electrónico:");
	     gbc.gridx = 0;
	     gbc.gridy = 2; 
	     panel.add(lblEmail, gbc);

	     JTextField txtEmail = new JTextField(20);
	     txtEmail.setPreferredSize(new Dimension(100,25));
	     gbc.gridx = 1;
	     gbc.gridy = 2;
	     panel.add(txtEmail, gbc);

	     // Etiqueta y campo para la fecha de nacimiento
	     JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento:");
	        
	     gbc.gridx = 0;
	     gbc.gridy = 3; 
	     panel.add(lblFechaNacimiento, gbc);

	     // Configuración del selector de fecha de nacimiento con JSpinner
	     JSpinner SpinnerNacimiento = new JSpinner(new SpinnerDateModel());
	     JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(SpinnerNacimiento, "dd-MM-yyyy");
	     dateEditor.setPreferredSize(new Dimension(90, 25));
	     SpinnerNacimiento.setEditor(dateEditor);
	     SpinnerNacimiento.setValue(new Date()); 
	     gbc.gridx = 1;
	     gbc.gridy = 3;
	     panel.add(SpinnerNacimiento, gbc);

	     // Etiqueta y campo para el número de teléfono
	     JLabel lbltelefono = new JLabel("Número de Teléfono:");
	     gbc.gridx = 0;
	     gbc.gridy = 4; 
	     panel.add(lbltelefono, gbc);

	     JTextField txtTelefono = new JTextField(20);
	     txtTelefono.setPreferredSize(new Dimension(100,25));
	     gbc.gridx = 1;
	     gbc.gridy = 4;
	     panel.add(txtTelefono, gbc);

	     // Etiqueta y campo para la dirección
	     JLabel lblDireccion = new JLabel("Dirección:");
	     gbc.gridx = 0;
	     gbc.gridy = 5; 
	     panel.add(lblDireccion, gbc);

	     JTextField txtDireccion = new JTextField(20);
	     txtDireccion.setPreferredSize(new Dimension(100,25));
	     gbc.gridx = 1;
	     gbc.gridy = 5;
	     panel.add(txtDireccion, gbc);

	     // Etiqueta y campo para el código postal
	     JLabel lblCodigoPostal = new JLabel("Código Postal:");
	     gbc.gridx = 0;
	     gbc.gridy = 6; 
	     panel.add(lblCodigoPostal, gbc);

	     JTextField txtCodigoPostal = new JTextField(20);
	     txtCodigoPostal.setPreferredSize(new Dimension(100,25));
	     gbc.gridx = 1;
	     gbc.gridy = 6;
	     panel.add(txtCodigoPostal, gbc);
	        
	     // Etiqueta y campo para la foto de usuario 
	     JLabel lblImagen = new JLabel("Foto de usuario:");
	     gbc.gridx = 0;
	     gbc.gridy = 7;
	     panel.add(lblImagen, gbc);
	        
	     Color softColor = new Color(0, 51, 102);
	     JButton btnCargarFoto = new JButton("Cargar Foto");
	        
	     gbc.gridx = 1;
	     gbc.gridy = 7; 
	     panel.add(btnCargarFoto, gbc);
	       

	     // Etiqueta y campo para la contraseña
	     JLabel lblContraseña = new JLabel("Contraseña:");
	     gbc.gridx = 0;
	     gbc.gridy = 8; 
	     panel.add(lblContraseña, gbc);

	     JPasswordField txtContraseña = new JPasswordField(20);
	     gbc.gridx = 1;
	     gbc.gridy = 8;
	     panel.add(txtContraseña, gbc);

	     // Etiqueta y campo para confirmar la contraseña
	     JLabel lblConfirmarContraseña = new JLabel("Confirmar Contraseña:");
	     gbc.gridx = 0;
	     gbc.gridy = 9; 
	     panel.add(lblConfirmarContraseña, gbc);

	     JPasswordField txtConfirmarContraseña = new JPasswordField(20);
	     gbc.gridx = 1;
	     gbc.gridy = 9;
	     panel.add(txtConfirmarContraseña, gbc);


	     
	     // Botón de Crear Cuenta
	     JButton btnCrearCuenta = new JButton("Crear Cuenta");
	            
	        
	     gbc.gridx = 1; 
	     gbc.gridy = 12; 
	     gbc.insets = new Insets(10, 20, 10, 20); 
	     panel.add(btnCrearCuenta, gbc);
	        
	     // Botón de Atrás
	     JButton btnAtras = new JButton("Atrás");
	     

	        
	     gbc.gridx = 0; 
	     gbc.gridy = 12; 
	     gbc.insets = new Insets(10, 20, 10, 20); 
	     panel.add(btnAtras, gbc);
	        
	     this.add(panel);
	     
	     // Accion de volver a la ventana de iniciar sesion
	     btnAtras.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Login l = new Login();
				l.setVisible(true);
				dispose();
				
			}
		});
	        
		
		
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Registro r = new Registro();
		r.setVisible(true);

	}

}
