package database;

public class TestDB {
    public static void main(String[] args) {
        try {
            // Crea tablas si no existen
            DBInitializer.initialize();
            // No más acciones aquí. Cierra la conexión al final:
        } finally {
            // Cerrar la conexión global (al terminar la ejecución)
            DBConnection.closeConnection();
        }
    }
}
