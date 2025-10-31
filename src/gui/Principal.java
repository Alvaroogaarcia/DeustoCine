package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.Usuario;

import java.awt.BorderLayout;
import javax.swing.JMenuBar;

public class Principal extends JFrame{
	
	private Usuario u;

	public Principal(Usuario u) {
		// TODO Auto-generated constructor stub
		this.u = u;

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Principal");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
	}
	
	
	

}
