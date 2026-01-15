package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conexion;
    private static String URL;

    static {
        // Detectar si estamos en JAR o en desarrollo
        File dbFile = new File("resources/data/deustocine.sqlite");
        
        if (dbFile.exists()) {
            // Modo desarrollo (Eclipse)
            URL = "jdbc:sqlite:resources/data/deustocine.sqlite";
        } else {
            // Modo JAR - crear BD en carpeta del usuario
            String userHome = System.getProperty("user.home");
            File appDir = new File(userHome, "DeustoCine");
            
            // Crear carpeta si no existe
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            
            URL = "jdbc:sqlite:" + appDir.getAbsolutePath() + "/deustocine.sqlite";
            System.out.println("📁 Base de datos en: " + appDir.getAbsolutePath());
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("org.sqlite.JDBC");
                conexion = DriverManager.getConnection(URL);
                conexion.createStatement().execute("PRAGMA foreign_keys = ON;");
                System.out.println("Conectado correctamente a la base de datos: " + URL);
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver SQLite no encontrado.", e);
        }
        return conexion;
    }

    public static synchronized void closeConnection() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexion = null;
            }
        }
    }
}

