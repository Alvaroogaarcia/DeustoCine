package gui;

import javax.swing.JFrame;

public class PerfilEntidad extends JFrame{

	public PerfilEntidad() {
		// TODO Auto-generated constructor stub
		
		//Configuracion de la ventana
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(500,500);
		this.setTitle("Deusto Cine - Perfil");
		
	}
	
	
	public static void main(String[] args) {
        PerfilEntidad pe = new PerfilEntidad();
        pe.setVisible(true);
    }

}
