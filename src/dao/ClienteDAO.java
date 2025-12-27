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
            pstmt.setString(4, cliente.getDireccion());
            pstmt.setString(5, cliente.getContrasenya());
            pstmt.setString(6, cliente.getFechaNacimiento());

            int filas = pstmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void actualizarSaldo(Cliente c) {
        String sql = "UPDATE cliente SET saldo = ? WHERE email = ?";
        // PreparedStatement
    }

    public void actualizarSaldoYCompras(Cliente cliente) {
        String sql = "UPDATE clientes SET saldo = ?, peliculas_compradas = ? WHERE id = ?";

        try (Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlite:resources/data/deustocine.sqlite");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, cliente.getSaldo());

            // Convierte la lista de películas compradas a texto separado por comas
            StringBuilder sb = new StringBuilder();
            for (Integer idPeli : cliente.getIdPeliculasCompradas()) {
                if (sb.length() > 0) sb.append(",");
                sb.append(idPeli);
            }
            pstmt.setString(2, sb.toString());
          //  pstmt.setInt(3, cliente.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al actualizar saldo y compras del cliente");
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
