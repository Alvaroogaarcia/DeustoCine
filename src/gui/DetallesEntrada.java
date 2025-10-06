package gui;

import javax.swing.JFrame;

public class DetallesEntrada extends JFrame{

	public DetallesEntrada() {
		// TODO Auto-generated constructor stub
		//Configuracion de la ventana
				this.setLocationRelativeTo(null);
				this.setResizable(false);
				this.setSize(500,500);
				this.setTitle("Deusto Cine - Entrada");
	}
	
	public static void main(String[] args) {
        DetallesEntrada de = new DetallesEntrada();
        de.setVisible(true);
    }

}
