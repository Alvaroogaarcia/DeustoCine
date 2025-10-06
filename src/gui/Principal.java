package gui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Principal extends JFrame{

	public Principal() {
		// TODO Auto-generated constructor stub
		
		//Configuracion de la ventana
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Principal");
		
	}
	
	public static void main(String[] args) {
        Principal p = new Principal();
        p.setVisible(true);
    }

}
