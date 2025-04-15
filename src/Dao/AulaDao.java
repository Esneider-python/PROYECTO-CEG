package Dao;

import Modelo.Aula;
import Modelo.Piso;
import Modelo.Usuario;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AulaDao {

    // Método para insertar un aula y recuperar su ID autogenerado
    public void insertar(Aula aula) {
        String sql = "INSERT INTO aulas (piso_id, usuario_id) VALUES (?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, aula.getPiso().getId());
            stmt.setInt(2, aula.getUsuarioRegistra().getIdUsuario());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    aula.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener un aula por su ID
    public Aula obtenerPorId(int id) {
        String sql = "SELECT * FROM aulas WHERE id_aula = ?";
        Aula aula = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Piso piso = new PisoDao().obtenerPorId(rs.getInt("piso_id"));
                    // Solo asignamos el ID de usuario, sin cargar datos adicionales
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("usuario_id"));

                    aula = new Aula(
                        rs.getInt("id_aula"),
                        piso,
                        usuario
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aula;
    }

    // Método para obtener todas las aulas
    public List<Aula> obtenerTodos() {
        String sql = "SELECT * FROM aulas";
        List<Aula> aulas = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Piso piso = new PisoDao().obtenerPorId(rs.getInt("piso_id"));
                // Solo asignamos el ID de usuario
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("usuario_id"));

                Aula aula = new Aula(
                    rs.getInt("id_aula"),
                    piso,
                    usuario
                );
                aulas.add(aula);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aulas;
    }

    // Método para actualizar un aula
    public void actualizar(Aula aula) {
        String sql = "UPDATE aulas SET piso_id = ?, usuario_id = ? WHERE id_aula = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, aula.getPiso().getId());
            stmt.setInt(2, aula.getUsuarioRegistra().getIdUsuario());
            stmt.setInt(3, aula.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar un aula por su ID
    public void eliminar(int id) {
        String sql = "DELETE FROM aulas WHERE id_aula = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
