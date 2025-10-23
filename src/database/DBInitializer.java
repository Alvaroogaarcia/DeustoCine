package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {

    public static void initialize() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DBConnection.getConnection();
            stmt = conn.createStatement();

            // Tabla usuario (con UNIQUE en email)
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS usuario (" +
                " nombre TEXT NOT NULL," +
                " email TEXT NOT NULL UNIQUE," +
                " numTelefono TEXT," +
                " direccion TEXT," +
                " contrasenya TEXT" +
                ");"
            );

            // Tabla pelicula
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS pelicula (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " titulo TEXT NOT NULL," +
                " anio INTEGER," +
                " duracion INTEGER," +
                " genero TEXT," +
                " sinopsis TEXT" +
                ");"
            );
            
            // Tabla descuento
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS descuento (" +
                " id TEXT PRIMARY KEY," +
                " codigo TEXT NOT NULL UNIQUE," +
                " porcentaje REAL NOT NULL" +
                ");"
            );

            

            
            

            System.out.println("Tablas creadas (si no exist�an).");

        } catch (SQLException e) {
            System.err.println("Error inicializando la BD:");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                // NO cerramos la conexi�n aqu� � ser� cerrada al final del programa
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

