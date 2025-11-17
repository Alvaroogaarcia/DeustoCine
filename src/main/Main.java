package main;

import javax.swing.SwingUtilities;

import database.DBInitializer;
import gui.Login;

public class Main {

	public static void main(String[] args) {
		DBInitializer.initialize();

        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });

	}

}
