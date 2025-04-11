package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Modelo.Reporte;
import util.Conexion;

public class ReporteDao {

    public boolean reportarElemento(Reporte reporte, String nuevoEstadoElemento) {
        Connection conexion = null;
        PreparedStatement insertarReporte = null;
        PreparedStatement actualizarEstado = null;

        try {
            conexion = Conexion.getConexion();
            conexion.setAutoCommit(false); // Para asegurar transacción

            // 1. Insertar el reporte
            String sqlReporte = "INSERT INTO reporte (descripcion, elemento_reportado, usuario_reporta) VALUES (?, ?, ?)";
            insertarReporte = conexion.prepareStatement(sqlReporte);
            insertarReporte.setString(1, reporte.getDescripcion());
            insertarReporte.setInt(2, reporte.getElementoReportado());
            insertarReporte.setInt(3, reporte.getUsuarioReporta());
            insertarReporte.executeUpdate();

            // 2. Actualizar estado del elemento
            String sqlUpdate = "UPDATE elementos SET estado = ? WHERE id = ?";
            actualizarEstado = conexion.prepareStatement(sqlUpdate);
            actualizarEstado.setString(1, nuevoEstadoElemento);
            actualizarEstado.setInt(2, reporte.getElementoReportado());
            actualizarEstado.executeUpdate();

            conexion.commit(); // Todo correcto, confirmamos cambios
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conexion != null) {
                    conexion.rollback(); // Deshacer cambios si algo falla
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;

        } finally {
            try {
                if (insertarReporte != null) insertarReporte.close();
                if (actualizarEstado != null) actualizarEstado.close();
                if (conexion != null) conexion.close();
            } catch (SQLException cierreEx) {
                cierreEx.printStackTrace();
            }
        }
    }
}
