package gui;

import javax.swing.JFrame;

public class CrearPelicula extends JFrame{

	public CrearPelicula() {
		// TODO Auto-generated constructor stub
		//Configuracion de la ventana
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Crear Pelicula");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrearPelicula cp = new CrearPelicula();
		cp.setVisible(true);

	}

}
