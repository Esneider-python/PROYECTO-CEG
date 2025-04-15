package Dao;

import Modelo.Sede;
import Modelo.Colegio;
import Modelo.Usuario;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SedeDao {

    // Método para insertar una sede y recuperar su ID
    public void insertar(Sede sede) {
        String sql = "INSERT INTO sede (nombre_sede, colegio_id, usuario_id) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, sede.getNombre());
            stmt.setInt(2, sede.getColegio().getId());
            stmt.setInt(3, sede.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    sede.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener una sede por su ID
    public Sede obtenerPorId(int id) {
        String sql = "SELECT * FROM sede WHERE id_sede = ?";
        Sede sede = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Colegio colegio = obtenerColegioPorId(rs.getInt("colegio_id"));
                    Usuario usuario = obtenerUsuarioPorId(rs.getInt("usuario_id"));
                    sede = new Sede(
                        rs.getInt("id_sede"),
                        rs.getString("nombre_sede"),
                        colegio,
                        usuario
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sede;
    }

    // Método para obtener todas las sedes
    public List<Sede> obtenerTodos() {
        String sql = "SELECT * FROM sede";
        List<Sede> sedes = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Colegio colegio = obtenerColegioPorId(rs.getInt("colegio_id"));
                Usuario usuario = obtenerUsuarioPorId(rs.getInt("usuario_id"));
                Sede sede = new Sede(
                    rs.getInt("id_sede"),
                    rs.getString("nombre_sede"),
                    colegio,
                    usuario
                );
                sedes.add(sede);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sedes;
    }

    // Método para actualizar una sede
    public void actualizar(Sede sede) {
        String sql = "UPDATE sede SET nombre_sede = ?, colegio_id = ?, usuario_id = ? WHERE id_sede = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sede.getNombre());
            stmt.setInt(2, sede.getColegio().getId());
            stmt.setInt(3, sede.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(4, sede.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar una sede
    public void eliminar(int id) {
        String sql = "DELETE FROM sede WHERE id_sede = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para obtener un Colegio por su ID
    private Colegio obtenerColegioPorId(int colegioId) {
        String sql = "SELECT * FROM colegio WHERE id_colegio = ?";
        Colegio colegio = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, colegioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("usuario_registra"));
                    colegio = new Colegio(
                        rs.getInt("id_colegio"),
                        rs.getString("nombre_colegio"),
                        usuario
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colegio;
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
                        null // nombreRol opcional
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
