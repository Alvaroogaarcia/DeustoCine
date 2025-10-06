package gui;

import javax.swing.JFrame;

public class PerfilCliente extends JFrame{

	public PerfilCliente() {
		// TODO Auto-generated constructor stub
		
		//Configuracion de la ventana
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Perfil");
	}
	
	public static void main(String[] args) {
        PerfilCliente pc = new PerfilCliente();
        pc.setVisible(true);
    }

}
