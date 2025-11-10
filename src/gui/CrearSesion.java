package gui;

import javax.swing.JFrame;

public class CrearSesion extends JFrame{

	public CrearSesion() {
		// TODO Auto-generated constructor stub
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Crear sesion");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CrearSesion s = new CrearSesion();
		s.setVisible(true);

	}

}
