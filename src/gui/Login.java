package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{

	public Login() {
		// TODO Auto-generated constructor stub
		
		// Configuracion de la ventana	
		this.setLocationRelativeTo(null);
		this.setSize(450,200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Deusto Cine - Iniciar sesion");
		
		//Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new GridLayout(3,2,10,10));
		
		//Componentes de la ventana
		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		JButton btnRegistrarse = new JButton("Registrarse");
		JLabel lblUsuario = new JLabel("Usuario");
		JLabel lblContraseña = new JLabel("Contraseña");
		JTextField txtUsuario = new JTextField();
		JPasswordField txtContraseña = new JPasswordField();
		
		panelPrincipal.add(lblUsuario);
		panelPrincipal.add(txtUsuario);
		panelPrincipal.add(lblContraseña);
		panelPrincipal.add(txtContraseña);
		panelPrincipal.add(btnIniciarSesion);
		panelPrincipal.add(btnRegistrarse);
		
		this.add(panelPrincipal);
		
		//Accion de mover a la ventana de registro
		
		btnRegistrarse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Registro r = new Registro();
				r.setVisible(true);
				dispose();
				
			}
		});
		
		
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Login l = new Login();
		l.setVisible(true);

	}

}
