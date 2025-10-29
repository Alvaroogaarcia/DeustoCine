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
            
            // Tabla sesión
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS sesion (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " id_pelicula INTEGER NOT NULL," +
                " fecha TEXT NOT NULL," +
                " hora TEXT NOT NULL," +
                " sala TEXT NOT NULL," +
                " FOREIGN KEY(id_pelicula) REFERENCES pelicula(id)" +
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
            
            // Tabla entrada
            stmt.executeUpdate(
            	"CREATE TABLE IF NOT EXISTS entrada (" +
            	" id INTEGER PRIMARY KEY AUTOINCREMENT," +
            	" id_sesion INTEGER NOT NULL," +
            	" fila INTEGER," +
            	" butaca INTEGER," +
            	" estado TEXT NOT NULL DEFAULT 'LIBRE'," + 
            	" UNIQUE(id_sesion, fila, butaca)," +
            	" FOREIGN KEY(id_sesion) REFERENCES sesion(id)" +
            	");"
            );


            // Tabla compra/reserva
            stmt.executeUpdate(
            	"CREATE TABLE IF NOT EXISTS compra (" +
            	" id INTEGER PRIMARY KEY AUTOINCREMENT," +
            	" email_usuario TEXT NOT NULL," + 
            	" id_entrada INTEGER NOT NULL," +
            	" estado TEXT NOT NULL DEFAULT 'RESERVADA'," + 
            	" creado_en INTEGER NOT NULL," + 
            	" expira_en INTEGER NOT NULL," +
            	" UNIQUE(id_entrada) ON CONFLICT IGNORE," + 
            	" FOREIGN KEY(id_entrada) REFERENCES entrada(id)" +
            	");"
            );


            // Tabla valoracion
            stmt.executeUpdate(
            	"CREATE TABLE IF NOT EXISTS valoracion (" +
            	" id INTEGER PRIMARY KEY AUTOINCREMENT," +
            	" email_usuario TEXT NOT NULL," +
            	" id_pelicula INTEGER NOT NULL," +
            	" puntuacion INTEGER NOT NULL," + 
            	" comentario TEXT," +
            	" fecha INTEGER NOT NULL," + 
            	" UNIQUE(email_usuario, id_pelicula)," +
            	" FOREIGN KEY(id_pelicula) REFERENCES pelicula(id)" +
            	");"
            );


            // Tabla reventa
            stmt.executeUpdate(
            	"CREATE TABLE IF NOT EXISTS reventa (" +
            	" id INTEGER PRIMARY KEY AUTOINCREMENT," +
            	" id_entrada INTEGER NOT NULL," +
            	" vendedor_email TEXT NOT NULL," +
            	" precio REAL NOT NULL," +
            	" estado TEXT NOT NULL DEFAULT 'ACTIVA'," + 
            	" creado_en INTEGER NOT NULL," +
            	" FOREIGN KEY(id_entrada) REFERENCES entrada(id)" +
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

