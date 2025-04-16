package Dao;

import Modelo.Colegio;
import Modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColegioDao {
    private Connection conn;

    public ColegioDao(Connection conn) {
        this.conn = conn;
    }

    // INSERTAR UN NUEVO COLEGIO
    public void insertar(Colegio colegio) {
        String sql = "INSERT INTO colegio (nombre_colegio, usuario_registra) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, colegio.getNombre());
            stmt.setInt(2, colegio.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    colegio.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CONSULTAR COLEGIO POR ID
    public Colegio obtenerPorId(int id) {
        String sql = "SELECT * FROM colegio WHERE id_colegio = ?";
        Colegio colegio = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
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

    public List<Colegio> obtenerTodos() {
        String sql = "SELECT * FROM colegio";
        List<Colegio> colegios = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("usuario_registra"));

                Colegio colegio = new Colegio(
                    rs.getInt("id_colegio"),
                    rs.getString("nombre_colegio"),
                    usuario
                );
                colegios.add(colegio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colegios;
    }

    public void actualizar(Colegio colegio) {
        String sql = "UPDATE colegio SET nombre_colegio = ?, usuario_registra = ? WHERE id_colegio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, colegio.getNombre());
            stmt.setInt(2, colegio.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(3, colegio.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM colegio WHERE id_colegio = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
