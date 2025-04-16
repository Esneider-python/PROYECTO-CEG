package Dao;

import Modelo.Sede;
import Modelo.Colegio;
import Modelo.Usuario;
import util.Conexion;

import java.sql.*;

public class SedeDao {

    // INSERTAR SEDE
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

    // OBTENER SEDE POR ID
    public Sede obtenerPorId(int id) {
        String sql = "SELECT * FROM sede WHERE id_sede = ?";
        Sede sede = null;

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // PASAMOS LA CONEXIÓN EXISTENTE A LOS DAO
                    ColegioDao colegioDao = new ColegioDao(conn);  // Pasamos la conexión aquí
                    UsuarioDao usuarioDao = new UsuarioDao(conn);  // También pasamos la conexión a UsuarioDao

                    Colegio colegio = colegioDao.obtenerPorId(rs.getInt("colegio_id"));
                    Usuario usuario = usuarioDao.obtenerUsuarioPorId(rs.getInt("usuario_id"));

                    sede = new Sede(
                            rs.getInt("id_sede"),
                            rs.getString("nombre_sede"),
                            colegio,
                            usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sede;
    }

    // ACTUALIZAR SEDE
    public boolean actualizar(Sede sede) {
        String sql = "UPDATE sede SET nombre_sede = ?, colegio_id = ?, usuario_id = ? WHERE id_sede = ?";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sede.getNombre());
            stmt.setInt(2, sede.getColegio().getId());
            stmt.setInt(3, sede.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(4, sede.getId());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR SEDE
    public boolean eliminar(int id) {
        String sql = "DELETE FROM sede WHERE id_sede = ?";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
