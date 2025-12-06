package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection;
import domain.Cliente;

public class ClienteDAO {

    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, email, telefono, direccion, contrasenya, fechaNacimiento) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setString(3, cliente.getNumTelefono());
            pstmt.setString(4, cliente.getDireccion()); // Puedes concatenar el postal si quieres
            pstmt.setString(5, cliente.getContrasenya());
            pstmt.setString(6, cliente.getFechaNacimiento());

            int filas = pstmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Cliente buscarPorEmail(String email) {
        String sql = "SELECT * FROM cliente WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("telefono"),
                    rs.getString("direccion"),
                    rs.getString("contrasenya"),
                    rs.getString("fechaNacimiento")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
