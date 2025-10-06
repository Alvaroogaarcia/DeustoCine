package gui;

import javax.swing.JFrame;

public class PagoEntrada extends JFrame{

	public PagoEntrada() {
		// TODO Auto-generated constructor stub
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Pago");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PagoEntrada pe = new PagoEntrada();
		pe.setVisible(true);

	}

}
