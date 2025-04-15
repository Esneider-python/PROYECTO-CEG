package Dao;

import Modelo.Colegio;
import Modelo.Usuario;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColegioDao {

    // Método para insertar un colegio y recuperar su ID autogenerado
    public void insertar(Colegio colegio) {
        String sql = "INSERT INTO colegio (nombre_colegio, usuario_registra) VALUES (?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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

    // Método para obtener un colegio por su ID
    public Colegio obtenerPorId(int id) {
        String sql = "SELECT * FROM colegio WHERE id_colegio = ?";
        Colegio colegio = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

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

    // Método para obtener todos los colegios
    public List<Colegio> obtenerTodos() {
        String sql = "SELECT * FROM colegio";
        List<Colegio> colegios = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
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

    // Método para actualizar los datos de un colegio
    public void actualizar(Colegio colegio) {
        String sql = "UPDATE colegio SET nombre_colegio = ?, usuario_registra = ? WHERE id_colegio = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, colegio.getNombre());
            stmt.setInt(2, colegio.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(3, colegio.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un colegio por su ID
    public void eliminar(int id) {
        String sql = "DELETE FROM colegio WHERE id_colegio = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
