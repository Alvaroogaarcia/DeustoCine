package gui;

import javax.swing.JFrame;

public class CompraEntradas extends JFrame{

	public CompraEntradas() {
		// TODO Auto-generated constructor stub
		
		//Configuracion de la ventana
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Compra Entrada");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CompraEntradas ce = new CompraEntradas();
		ce.setVisible(true);

	}

}
