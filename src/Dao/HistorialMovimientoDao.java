package Dao;

import Modelo.HistorialMovimiento;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistorialMovimientoDao {

    // REGISTRAR MOVIMIENTO
    public boolean registrarMovimiento(HistorialMovimiento movimiento) {
        String sql = "INSERT INTO historial_movimientos (tipo_elemento, aula_origen, aula_destino, usuario_movio) VALUES (?, ?, ?, ?)";
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, movimiento.getTipoElemento());
            stmt.setInt(2, movimiento.getAulaOrigen());
            stmt.setInt(3, movimiento.getAulaDestino());
            stmt.setInt(4, movimiento.getUsuarioMovio());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // OBTENER TODOS LOS MOVIMIENTOS
    public List<HistorialMovimiento> obtenerTodos() {
        List<HistorialMovimiento> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM historial_movimientos";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                HistorialMovimiento mov = new HistorialMovimiento(
                        rs.getInt("id_historial"),
                        rs.getString("tipo_elemento"),
                        rs.getInt("aula_origen"),
                        rs.getInt("aula_destino"),
                        rs.getInt("usuario_movio"),
                        rs.getTimestamp("fecha_movimiento")
                );
                movimientos.add(mov);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movimientos;
    }

    // OBTENER MOVIMIENTOS POR ID
    public HistorialMovimiento obtenerPorId(int id) {
        String sql = "SELECT * FROM historial_movimientos WHERE id_historial = ?";
        HistorialMovimiento movimiento = null;

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    movimiento = new HistorialMovimiento(
                            rs.getInt("id_historial"),
                            rs.getString("tipo_elemento"),
                            rs.getInt("aula_origen"),
                            rs.getInt("aula_destino"),
                            rs.getInt("usuario_movio"),
                            rs.getTimestamp("fecha_movimiento")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return movimiento;
    }

    // ACTUALIZAR MOVIMIENTO
    public boolean actualizarMovimiento(HistorialMovimiento movimiento) {
        String sql = "UPDATE historial_movimientos SET tipo_elemento = ?, aula_origen = ?, aula_destino = ?, usuario_movio = ? WHERE id_historial = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, movimiento.getTipoElemento());
            stmt.setInt(2, movimiento.getAulaOrigen());
            stmt.setInt(3, movimiento.getAulaDestino());
            stmt.setInt(4, movimiento.getUsuarioMovio());
            stmt.setInt(5, movimiento.getIdHistorial());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ELIMINAR MOVIMIENTO
    public boolean eliminarMovimiento(int id) {
        String sql = "DELETE FROM historial_movimientos WHERE id_historial = ?";

        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
