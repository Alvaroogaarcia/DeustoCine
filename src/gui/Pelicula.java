package gui;

import javax.swing.JFrame;

public class Pelicula extends JFrame{

	public Pelicula() {
		// TODO Auto-generated constructor stub
		
		//Configuracion de la ventana
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Pelicula");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Pelicula p = new Pelicula();
		p.setVisible(true);

	}

}
