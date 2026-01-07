package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conexion;
    private static final String URL = "jdbc:sqlite:resources/data/deustocine.sqlite";

    // Obtiene la conexi�n singleton (la crea si no existe o si est� cerrada)
    public static synchronized Connection getConnection() throws SQLException {
        try {
            if (conexion == null || conexion.isClosed()) {
                // Cargar driver no es estrictamente necesario en Java 6+, pero no hace da�o
                Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection(URL);
                // activar foreign keys cada vez que abrimos la conexi�n
                conexion.createStatement().execute("PRAGMA foreign_keys = ON;");
                System.out.println("Conectado correctamente a la base de datos: " + URL);
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite no encontrado.", e);
        }
        return conexion;
    }

    // Cierra la conexion (llamar solo al terminar la app)
    public static synchronized void closeConnection() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexi�n cerrada correctamente.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexion = null;
            }
        }
    }
}

