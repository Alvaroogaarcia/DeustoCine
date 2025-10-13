package database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String DB_DIR = "resources" + File.separator + "data";
    private static final String DB_FILE = "deustocine.sqlite";
    private static final String URL = "jdbc:sqlite:" + DB_DIR + File.separator + DB_FILE;

    private static Connection conn = null;

    public static synchronized Connection getConnection() {
        if (conn == null) {
            try {
                ensureDatabaseDirectoryExists();
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(URL);

                // Activar foreign keys
                try (Statement st = conn.createStatement()) {
                    st.execute("PRAGMA foreign_keys = ON;");
                }

                System.out.println("Conectado correctamente a la base de datos: " + URL);
            } catch (Exception e) {
                System.err.println("Error al conectar con la base de datos SQLite.");
                e.printStackTrace();
            }
        }
        return conn;
    }

    private static void ensureDatabaseDirectoryExists() {
        File dir = new File(DB_DIR);
        if (!dir.exists()) {
            boolean ok = dir.mkdirs();
            if (ok) System.out.println("Carpeta creada: " + dir.getPath());
        }
    }

    public static synchronized void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
                System.out.println("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
