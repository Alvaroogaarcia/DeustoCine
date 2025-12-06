package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;
import domain.Entidad;

public class EntidadDAO {

    public boolean insertar(Entidad entidad) {
        String sql = "INSERT INTO entidad (nombre, email, telefono, direccion, contrasenya, nif) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entidad.getNombre());
            pstmt.setString(2, entidad.getEmail());
            pstmt.setString(3, entidad.getNumTelefono());
            pstmt.setString(4, entidad.getDireccion());
            pstmt.setString(5, entidad.getContrasenya());
            pstmt.setString(6, entidad.getNif());

            int filas = pstmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Entidad buscarPorEmail(String email) {
        String sql = "SELECT * FROM entidad WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Entidad(
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("contrasenya"),
                    rs.getString("nif")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
