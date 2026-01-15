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

            // Tabla usuario (empresa/admin)
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS usuario (" +
                " nombre TEXT NOT NULL," +
                " email TEXT NOT NULL UNIQUE," +
                " numTelefono TEXT," +
                " direccion TEXT," +
                " contrasenya TEXT," +
                " nif TEXT," +
                " fechaNacimiento TEXT" +
                ");"
            );

            // Insertar empresas por defecto
            stmt.executeUpdate(
                "INSERT OR IGNORE INTO usuario (nombre, email, numTelefono, direccion, contrasenya, nif, fechaNacimiento) VALUES " +
                "('Empresa CineDeusto SA', 'contacto@cineempresa.com', '944123456', 'Bilbao', '1234', 'A12345678', NULL), " +
                "('Servicios Culturales Donosti SL', 'info@servcultural.com', '943987654', 'San Sebastián', '1234', 'B87654321', NULL);"
            );
            
            // Tabla Cliente (USUARIOS NORMALES) - AQUÍ ESTÁ EL CAMBIO DEL SALDO
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS cliente (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nombre TEXT NOT NULL," +
                " email TEXT UNIQUE NOT NULL," +
                " telefono TEXT," +
                " direccion TEXT," +
                " contrasenya TEXT NOT NULL," +
                " fechaNacimiento TEXT," +
                " saldo REAL DEFAULT 0.0" +  // <--- CAMPO NUEVO
                ");"
            );
            
            // Insertar un cliente de prueba para que puedas entrar sin registrarte
            stmt.executeUpdate(
                "INSERT OR IGNORE INTO cliente (nombre, email, telefono, direccion, contrasenya, fechaNacimiento, saldo) VALUES " +
                "('Cliente Prueba', 'test@test.com', '600000000', 'Calle Falsa 123', '1234', '2000-01-01', 0.0);"
            );

            // Tabla Entidad
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS entidad (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " nombre TEXT NOT NULL," +
                " email TEXT UNIQUE NOT NULL," +
                " telefono TEXT," +
                " direccion TEXT," +
                " contrasenya TEXT NOT NULL," +
                " nif TEXT"+
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
                " sinopsis TEXT," +
                " aforo INTEGER," +
                " imagen TEXT" +
                ");"
            );
            
            // Tabla sesión
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS sesion (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " id_entidad INTEGER NOT NULL,"+        
                " id_pelicula INTEGER NOT NULL," +
                " fecha TEXT NOT NULL," +
                " hora TEXT NOT NULL," +
                " sala TEXT NOT NULL," +
                " FOREIGN KEY(id_entidad) REFERENCES entidad(id),"+
                " FOREIGN KEY(id_pelicula) REFERENCES pelicula(id)" +
                ");"
            );

            // Tabla descuento
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS descuento (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " id_entidad INTEGER NOT NULL,"+
                " codigo TEXT NOT NULL UNIQUE," +
                " porcentaje REAL NOT NULL," +
                " FOREIGN KEY(id_entidad) REFERENCES entidad(id)"+
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
            
            //Tabla peliculas compradas
            stmt.executeUpdate(
            	    "CREATE TABLE IF NOT EXISTS pelicula_comprada (" +
            	    " id INTEGER PRIMARY KEY AUTOINCREMENT," +
            	    " email_cliente TEXT NOT NULL," +
            	    " id_pelicula INTEGER NOT NULL," +
            	    " fecha_compra INTEGER NOT NULL," +
            	    " precio_pagado REAL NOT NULL," +
            	    " FOREIGN KEY(id_pelicula) REFERENCES pelicula(id)" +
            	    ");"
            	);
         // entidad de ejemplo 
            stmt.executeUpdate(
                "INSERT OR IGNORE INTO entidad (id, nombre, email, telefono, direccion, contrasenya, nif) VALUES " +
                "(1, 'Cines Deusto', 'cines@deusto.com', '944000000', 'Bilbao, España', '1234', 'B12345678');"
            );

            // películas de ejemplo
            stmt.executeUpdate(
                "INSERT OR REPLACE INTO pelicula (id, titulo, anio, duracion, genero, sinopsis, aforo, imagen) VALUES " +
                "(1, 'Django', 2012, 165, 'Western', 'Un esclavo liberado se une a un cazarrecompensas.', 100, 'django.jpeg'), " +
                "(2, 'Titanic', 1997, 195, 'Drama', 'Una historia de amor en el famoso trasatlántico.', 150, 'titanic.jpeg'), " +
                "(3, 'Inception', 2010, 148, 'Ciencia Ficción', 'Un ladrón que roba secretos del subconsciente.', 120, 'inception.jpeg'), " +
                "(4, 'El Padrino', 1972, 175, 'Drama', 'La historia de una familia mafiosa.', 100, 'el_padrino.jpeg'), " +
                "(5, 'Forrest Gump', 1994, 142, 'Drama', 'La vida extraordinaria de un hombre simple.', 130, 'forest.jpeg'), " +
                "(6, 'El Señor de los Anillos', 2001, 178, 'Fantasía', 'Un hobbit debe destruir un anillo mágico.', 140, 'anillos.jpeg'), " +
                "(7, 'Cars', 2006, 117, 'Animación', 'Un coche de carreras aprende sobre la amistad.', 100, 'cars.jpeg'), " +
                "(8, 'Transformers', 2007, 144, 'Acción', 'Robots alienígenas luchan en la Tierra.', 110, 'Transformers1.jpeg');"
            );

            // sesiones de ejemplo 
            stmt.executeUpdate(
                "INSERT OR REPLACE INTO sesion (id_entidad, id_pelicula, fecha, hora, sala) VALUES " +
                "(1, 1, '2026-01-18', '18:00', 'Sala 1'), " +
                "(1, 1, '2026-01-18', '21:00', 'Sala 1'), " +
                "(1, 2, '2026-01-18', '17:30', 'Sala 2'), " +
                "(1, 2, '2026-01-19', '20:00', 'Sala 2'), " +
                "(1, 3, '2026-01-18', '19:00', 'Sala 3'), " +
                "(1, 3, '2026-01-19', '22:00', 'Sala 3'), " +
                "(1, 4, '2026-01-19', '18:30', 'Sala 1'), " +
                "(1, 4, '2026-01-20', '21:30', 'Sala 1'), " +
                "(1, 5, '2026-01-19', '17:00', 'Sala 4'), " +
                "(1, 5, '2026-01-20', '20:00', 'Sala 4'), " +
                "(1, 6, '2026-01-19', '19:30', 'Sala 2'), " +
                "(1, 6, '2026-01-20', '22:30', 'Sala 2'), " +
                "(1, 7, '2026-01-20', '16:00', 'Sala 5'), " +
                "(1, 7, '2026-01-21', '18:00', 'Sala 5'), " +
                "(1, 8, '2026-01-20', '20:30', 'Sala 3'), " +
                "(1, 8, '2026-01-21', '23:00', 'Sala 3');"
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

            System.out.println("Tablas verificadas correctamente en la nueva DB.");

        } catch (SQLException e) {
            System.err.println("Error inicializando la BD:");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
