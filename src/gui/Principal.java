package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import domain.Usuario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class Principal extends JFrame{
	
	private Usuario u;

	public Principal(Usuario u) {
		// TODO Auto-generated constructor stub
		
		//Configuracion inicial de la ventana
		this.u = u;

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Principal");
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Panel principal con los botones
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 40));
		add(panel);

		// Boton para ir a la pantalla de compra de entradas
		JButton btnComprarEntradas = new JButton("Comprar entradas");
		btnComprarEntradas.setPreferredSize(new Dimension(200, 40));
		panel.add(btnComprarEntradas);

	/*	// Abre la ventana de compra de entradas
		btnComprarEntradas.addActionListener(e -> {
		    CompraEntradas ce = new CompraEntradas();
		    ce.setVisible(true);
		});*/
		
	}
	
	
	

}
