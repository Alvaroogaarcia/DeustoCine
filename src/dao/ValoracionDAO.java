package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.DBConnection;
import domain.Valoracion;


public class ValoracionDAO {
    
   
    public boolean insertar(String emailUsuario, int idPelicula, int puntuacion, String comentario) {
        String sql = "INSERT INTO valoracion (email_usuario, id_pelicula, puntuacion, comentario, fecha) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, emailUsuario);
            pstmt.setInt(2, idPelicula);
            pstmt.setInt(3, puntuacion);
            pstmt.setString(4, comentario);
            pstmt.setLong(5, System.currentTimeMillis());
            
            int filas = pstmt.executeUpdate();
            System.out.println("✓ Valoración insertada para película ID: " + idPelicula);
            return filas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar valoración:");
            e.printStackTrace();
            return false;
        }
    }
    
  
    public boolean actualizar(String emailUsuario, int idPelicula, int puntuacion, String comentario) {
        String sql = "UPDATE valoracion SET puntuacion = ?, comentario = ?, fecha = ? " +
                     "WHERE email_usuario = ? AND id_pelicula = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, puntuacion);
            pstmt.setString(2, comentario);
            pstmt.setLong(3, System.currentTimeMillis());
            pstmt.setString(4, emailUsuario);
            pstmt.setInt(5, idPelicula);
            
            int filas = pstmt.executeUpdate();
            System.out.println("✓ Valoración actualizada");
            return filas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar valoración:");
            e.printStackTrace();
            return false;
        }
    }
    
  
    public Valoracion obtenerValoracion(String emailUsuario, int idPelicula) {
        String sql = "SELECT * FROM valoracion WHERE email_usuario = ? AND id_pelicula = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, emailUsuario);
            pstmt.setInt(2, idPelicula);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Valoracion(
                    rs.getString("email_usuario"),
                    rs.getInt("id_pelicula"),
                    rs.getInt("puntuacion"),
                    rs.getString("comentario"),
                    rs.getLong("fecha")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener valoración:");
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Lista todas las valoraciones de una película
     * @param idPelicula ID de la película
     * @return Lista de valoraciones ordenadas por fecha descendente
     */
    public List<Valoracion> listarPorPelicula(int idPelicula) {
        List<Valoracion> lista = new ArrayList<>();
        String sql = "SELECT * FROM valoracion WHERE id_pelicula = ? ORDER BY fecha DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPelicula);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                lista.add(new Valoracion(
                    rs.getString("email_usuario"),
                    rs.getInt("id_pelicula"),
                    rs.getInt("puntuacion"),
                    rs.getString("comentario"),
                    rs.getLong("fecha")
                ));
            }
            
            System.out.println("✓ " + lista.size() + " valoraciones encontradas");
            
        } catch (SQLException e) {
            System.err.println("Error al listar valoraciones:");
            e.printStackTrace();
        }
        
        return lista;
    }
    
   
    public double obtenerPromedioValoracion(int idPelicula) {
        String sql = "SELECT AVG(puntuacion) as promedio FROM valoracion WHERE id_pelicula = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPelicula);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("promedio");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al calcular promedio:");
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
   
    public int contarValoraciones(int idPelicula) {
        String sql = "SELECT COUNT(*) as total FROM valoracion WHERE id_pelicula = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idPelicula);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al contar valoraciones:");
            e.printStackTrace();
        }
        
        return 0;
    }
  
    public boolean yaValoro(String emailUsuario, int idPelicula) {
        return obtenerValoracion(emailUsuario, idPelicula) != null;
    }
   
    public boolean eliminar(String emailUsuario, int idPelicula) {
        String sql = "DELETE FROM valoracion WHERE email_usuario = ? AND id_pelicula = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, emailUsuario);
            pstmt.setInt(2, idPelicula);
            
            int filas = pstmt.executeUpdate();
            return filas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar valoración:");
            e.printStackTrace();
            return false;
        }
    }
}