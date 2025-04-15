package Dao;

import Modelo.ElementoEliminado;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ElementoEliminadoDao {

    // REGISTRAR ELEMENTO ELIMINADO
    public boolean registrarEliminacion(ElementoEliminado elemento) {
        String sql = "INSERT INTO elementos_eliminados (elemento_id, motivo_eliminacion, usuario_elimino) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, elemento.getElementoId());
            stmt.setString(2, elemento.getMotivoEliminacion());
            stmt.setInt(3, elemento.getUsuarioElimino());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // OBTENER ELEMENTO ELIMINADO POR ID
    public ElementoEliminado obtenerPorId(int id) {
        String sql = "SELECT * FROM elementos_eliminados WHERE id_elemento_eliminado = ?";
        ElementoEliminado eliminado = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                eliminado = new ElementoEliminado(
                    rs.getInt("id_elemento_eliminado"),
                    rs.getInt("elemento_id"),
                    rs.getString("motivo_eliminacion"),
                    rs.getTimestamp("fecha_hora_eliminacion"),
                    rs.getInt("usuario_elimino")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }

    // OBTENER TODOS LOS ELEMENTOS ELIMINADOS
    public List<ElementoEliminado> obtenerTodos() {
        String sql = "SELECT * FROM elementos_eliminados ORDER BY fecha_hora_eliminacion DESC";
        List<ElementoEliminado> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ElementoEliminado eliminado = new ElementoEliminado(
                    rs.getInt("id_elemento_eliminado"),
                    rs.getInt("elemento_id"),
                    rs.getString("motivo_eliminacion"),
                    rs.getTimestamp("fecha_hora_eliminacion"),
                    rs.getInt("usuario_elimino")
                );
                lista.add(eliminado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ELIMINAR ELEMENTO ELIMINADO POR ID (opcional)
    public boolean eliminarPorId(int id) {
        String sql = "DELETE FROM elementos_eliminados WHERE id_elemento_eliminado = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ACTUALIZAR MOTIVO DE ELIMINACION (opcional)
    public boolean actualizarMotivo(int id, String nuevoMotivo) {
        String sql = "UPDATE elementos_eliminados SET motivo_eliminacion = ? WHERE id_elemento_eliminado = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoMotivo);
            stmt.setInt(2, id);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
