package dao;

import database.DBConnection;
import domain.Sesion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SesionDAO {

    public void insertar(Sesion sesion) {

        String sql = "INSERT INTO sesion (id_entidad, id_pelicula, fecha, hora, sala) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sesion.getIdEntidad());
            ps.setInt(2, sesion.getIdPelicula());
            ps.setString(3, sesion.getFecha());
            ps.setString(4, sesion.getHora());
            ps.setString(5, sesion.getSala());

            ps.executeUpdate();
            System.out.println("Sesión insertada correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Sesion> listar() {

        List<Sesion> lista = new ArrayList<>();
        String sql = "SELECT * FROM sesion";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Sesion s = new Sesion();
                s.setId(rs.getInt("id"));
                s.setIdEntidad(rs.getInt("id_entidad"));
                s.setIdPelicula(rs.getInt("id_pelicula"));
                s.setFecha(rs.getString("fecha"));
                s.setHora(rs.getString("hora"));
                s.setSala(rs.getString("sala"));
                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
