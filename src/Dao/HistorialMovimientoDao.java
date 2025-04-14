package Dao;

import Modelo.HistorialMovimiento;
import util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HistorialMovimientoDao {

    public boolean registrarMovimiento(HistorialMovimiento movimiento) {
        String sql = "INSERT INTO historial_movimientos (tipo_elemento, aula_origen, aula_destino, usuario_movio) VALUES (?, ?, ?, ?)";
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            
            stmt.setString(1, movimiento.getTipoElemento());
            stmt.setInt(2, movimiento.getAulaOrigen());
            stmt.setInt(3, movimiento.getAulaDestino());
            stmt.setInt(4, movimiento.getUsuarioMovio());

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
