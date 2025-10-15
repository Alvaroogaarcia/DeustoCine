package dao;

import database.DBConnection;
import domain.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Comprueba si existe un usuario con el email dado
    private boolean existeEmail(String email) {
        String sql = "SELECT COUNT(1) AS cnt FROM usuario WHERE email = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("cnt") > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertar(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            throw new IllegalArgumentException("Usuario o email nulo");
        }

        if (existeEmail(usuario.getEmail())) {
            System.err.println("⚠️ El email ya existe: " + usuario.getEmail());
            return false; // no insertado
        }

        String sql = "INSERT INTO usuario(nombre, email, numTelefono, direccion, contrasenya) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getEmail());
                ps.setString(3, usuario.getNumTelefono());
                ps.setString(4, usuario.getDireccion());
                ps.setString(5, usuario.getContrasenya());
                ps.executeUpdate();
                System.out.println("Usuario insertado correctamente: " + usuario.getNombre());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error insertando usuario:");
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT nombre, email, numTelefono, direccion, contrasenya FROM usuario";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (Statement st = conn.createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setNumTelefono(rs.getString("numTelefono"));
                    u.setDireccion(rs.getString("direccion"));
                    u.setContrasenya(rs.getString("contrasenya"));
                    lista.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT nombre, email, numTelefono, direccion, contrasenya FROM usuario WHERE email = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Usuario u = new Usuario();
                        u.setNombre(rs.getString("nombre"));
                        u.setEmail(rs.getString("email"));
                        u.setNumTelefono(rs.getString("numTelefono"));
                        u.setDireccion(rs.getString("direccion"));
                        u.setContrasenya(rs.getString("contrasenya"));
                        return u;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean eliminarPorEmail(String email) {
        String sql = "DELETE FROM usuario WHERE email = ?";
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, email);
                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
