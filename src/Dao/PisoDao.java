
package Dao;

import Modelo.Piso;
import Modelo.Bloque;
import Modelo.Usuario;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PisoDao {

    // Método para insertar un piso y recuperar su ID autogenerado
    public void insertar(Piso piso) {
        String sql = "INSERT INTO pisos (numero_piso, bloque_id, usuario_id) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, piso.getNumeroPiso());
            stmt.setInt(2, piso.getBloque().getId());
            stmt.setInt(3, piso.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    piso.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener un piso por su ID
    public Piso obtenerPorId(int id) {
        String sql = "SELECT * FROM pisos WHERE id_piso = ?";
        Piso piso = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bloque bloque = new BloqueDao().obtenerPorId(rs.getInt("bloque_id"));
                    Usuario usuario = obtenerUsuarioPorId(rs.getInt("usuario_id"));
                    piso = new Piso(
                        rs.getInt("id_piso"),
                        rs.getInt("numero_piso"),
                        bloque,
                        usuario
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return piso;
    }

    // Método para obtener todos los pisos
    public List<Piso> obtenerTodos() {
        String sql = "SELECT * FROM pisos";
        List<Piso> pisos = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bloque bloque = new BloqueDao().obtenerPorId(rs.getInt("bloque_id"));
                Usuario usuario = obtenerUsuarioPorId(rs.getInt("usuario_id"));
                Piso piso = new Piso(
                    rs.getInt("id_piso"),
                    rs.getInt("numero_piso"),
                    bloque,
                    usuario
                );
                pisos.add(piso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pisos;
    }

    // Método para actualizar un piso
    public void actualizar(Piso piso) {
        String sql = "UPDATE pisos SET numero_piso = ?, bloque_id = ?, usuario_id = ? WHERE id_piso = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, piso.getNumeroPiso());
            stmt.setInt(2, piso.getBloque().getId());
            stmt.setInt(3, piso.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(4, piso.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un piso por su ID
    public void eliminar(int id) {
        String sql = "DELETE FROM pisos WHERE id_piso = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para obtener un Usuario por su ID
    private Usuario obtenerUsuarioPorId(int usuarioId) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        Usuario usuario = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("cedula"),
                        rs.getString("contrasena"),
                        rs.getInt("rol_id"),
                        null
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}

