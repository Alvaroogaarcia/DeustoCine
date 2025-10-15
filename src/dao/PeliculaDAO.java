package dao;

import database.DBConnection;
import domain.Pelicula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeliculaDAO {

    public void insertar(Pelicula pelicula) {
        String sql = "INSERT INTO pelicula(titulo, anio, duracion, genero, sinopsis) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, pelicula.getTitulo());
                pstmt.setInt(2, pelicula.getAnio());
                pstmt.setInt(3, pelicula.getDuracion());
                pstmt.setString(4, pelicula.getGenero());
                pstmt.setString(5, pelicula.getSinopsis());
                pstmt.executeUpdate();
                System.out.println("Película insertada correctamente: " + pelicula.getTitulo());
            }
        } catch (SQLException e) {
            System.err.println("Error insertando película:");
            e.printStackTrace();
        }
        // NO cerramos la conexión aquí
    }

    public List<Pelicula> listar() {
        List<Pelicula> lista = new ArrayList<>();
        String sql = "SELECT id, titulo, anio, duracion, genero, sinopsis FROM pelicula";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Pelicula p = new Pelicula();
                    p.setId(rs.getInt("id"));
                    p.setTitulo(rs.getString("titulo"));
                    p.setAnio(rs.getInt("anio"));
                    p.setDuracion(rs.getInt("duracion"));
                    p.setGenero(rs.getString("genero"));
                    p.setSinopsis(rs.getString("sinopsis"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error listando películas:");
            e.printStackTrace();
        }
        return lista;
    }

    public Pelicula buscarPorId(int id) {
        String sql = "SELECT id, titulo, anio, duracion, genero, sinopsis FROM pelicula WHERE id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Pelicula p = new Pelicula();
                        p.setId(rs.getInt("id"));
                        p.setTitulo(rs.getString("titulo"));
                        p.setAnio(rs.getInt("anio"));
                        p.setDuracion(rs.getInt("duracion"));
                        p.setGenero(rs.getString("genero"));
                        p.setSinopsis(rs.getString("sinopsis"));
                        return p;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizar(Pelicula pelicula) {
        String sql = "UPDATE pelicula SET titulo=?, anio=?, duracion=?, genero=?, sinopsis=? WHERE id=?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, pelicula.getTitulo());
                ps.setInt(2, pelicula.getAnio());
                ps.setInt(3, pelicula.getDuracion());
                ps.setString(4, pelicula.getGenero());
                ps.setString(5, pelicula.getSinopsis());
                ps.setInt(6, pelicula.getId());
                ps.executeUpdate();
                System.out.println("Película actualizada: " + pelicula.getTitulo());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM pelicula WHERE id = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Película eliminada con id=" + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

