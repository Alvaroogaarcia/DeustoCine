package dao;

import database.DBConnection;
import database.DBInitializer;
import domain.Pelicula;
import domain.Usuario;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        try {
            // Aseguramos tablas
            DBInitializer.initialize();

            PeliculaDAO pdao = new PeliculaDAO();
            UsuarioDAO udao = new UsuarioDAO();

            // Insertar pel�culas de prueba
            Pelicula p1 = new Pelicula();
            p1.setTitulo("Matrix");
            p1.setAnio(1999);
            p1.setDuracion(136);
            p1.setGenero("Ciencia Ficci�n");
            p1.setAforo(25);
            pdao.insertar(p1);

            // Insertar usuario (comprueba si ya existe)
            Usuario u1 = new Usuario("Juan", "juan@example.com", "600123456", "Calle Falsa 123", "1234");
            boolean inserted = udao.insertar(u1);
            if (!inserted) {
                System.out.println("No se insert� el usuario (posible duplicado).");
            }

            // Listar
            List<Pelicula> peliculas = pdao.listar();
            System.out.println("\nPel�culas en BD:");
            for (Pelicula p : peliculas) System.out.println(p);

            List<Usuario> usuarios = udao.listar();
            System.out.println("\nUsuarios en BD:");
            for (Usuario u : usuarios) System.out.println(u);

        } finally {
            // Cerrar la conexi�n una sola vez al final
            DBConnection.closeConnection();
        }
    }
}
