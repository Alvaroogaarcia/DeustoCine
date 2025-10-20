package dao;

import database.DBConnection;
import domain.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    /**
     * Inserta una nueva película en la base de datos
     */
    public void insertar(Pelicula pelicula) {
        String sql = "INSERT INTO pelicula(titulo, anio, duracion, genero, aforo, imagen) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pelicula.getTitulo());
            pstmt.setInt(2, pelicula.getAnio());
            pstmt.setInt(3, pelicula.getDuracion());
            pstmt.setString(4, pelicula.getGenero());
            pstmt.setInt(5, pelicula.getAforo());
            pstmt.setString(6, pelicula.getImagen());
            pstmt.executeUpdate();

            System.out.println("✅ Película insertada correctamente: " + pelicula.getTitulo());

        } catch (SQLException e) {
            System.err.println("❌ Error insertando película:");
            e.printStackTrace();
        }
    }

    /**
     * Devuelve una lista con todas las películas de la base de datos
     */
    public List<Pelicula> listar() {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, anio, duracion, genero, aforo, imagen FROM pelicula";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pelicula p = new Pelicula();
                p.setId(rs.getInt("id"));
                p.setTitulo(rs.getString("titulo"));
                p.setAnio(rs.getInt("anio"));
                p.setDuracion(rs.getInt("duracion"));
                p.setGenero(rs.getString("genero"));
                p.setAforo(rs.getInt("aforo"));
                p.setImagen(rs.getString("imagen"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error listando películas:");
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Busca una película por su ID
     */
    public Pelicula buscarPorId(int id) {
        String sql = "SELECT id, titulo, anio, duracion, genero, aforo, imagen FROM pelicula WHERE id = ?";
        Pelicula p = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Pelicula();
                    p.setId(rs.getInt("id"));
                    p.setTitulo(rs.getString("titulo"));
                    p.setAnio(rs.getInt("anio"));
                    p.setDuracion(rs.getInt("duracion"));
                    p.setGenero(rs.getString("genero"));
                    p.setAforo(rs.getInt("aforo"));
                    p.setImagen(rs.getString("imagen"));
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error buscando película con ID=" + id);
            e.printStackTrace();
        }

        return p;
    }

    /**
     * Actualiza los datos de una película existente
     */
    public void actualizar(Pelicula pelicula) {
        String sql = "UPDATE pelicula SET titulo=?, anio=?, duracion=?, genero=?, aforo=?, imagen=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pelicula.getTitulo());
            ps.setInt(2, pelicula.getAnio());
            ps.setInt(3, pelicula.getDuracion());
            ps.setString(4, pelicula.getGenero());
            ps.setInt(5, pelicula.getAforo());
            ps.setString(6, pelicula.getImagen());
            ps.setInt(7, pelicula.getId());

            ps.executeUpdate();
            System.out.println("✅ Película actualizada correctamente: " + pelicula.getTitulo());

        } catch (SQLException e) {
            System.err.println("❌ Error actualizando película:");
            e.printStackTrace();
        }
    }

    /**
     * Elimina una película por su ID
     */
    public void eliminar(int id) {
        String sql = "DELETE FROM pelicula WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("🗑️ Película eliminada con ID=" + id);

        } catch (SQLException e) {
            System.err.println("❌ Error eliminando película con ID=" + id);
            e.printStackTrace();
        }
    }
}
