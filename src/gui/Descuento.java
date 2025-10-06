package gui;

import javax.swing.JFrame;

public class Descuento extends JFrame{

	public Descuento() {
		// TODO Auto-generated constructor stub
		//Configuracion de la ventana
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Descuento");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Descuento d = new Descuento();
		d.setVisible(true);

	}

}
