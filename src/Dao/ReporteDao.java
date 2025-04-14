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
            conexion.setAutoCommit(false); // Iniciar transacción

            // Insertar reporte
            String sqlReporte = "INSERT INTO reporte (descripcion, elemento_reportado, usuario_reporta) VALUES (?, ?, ?)";
            insertarReporte = conexion.prepareStatement(sqlReporte);
            insertarReporte.setString(1, reporte.getDescripcion());
            insertarReporte.setInt(2, reporte.getElementoReportado());
            insertarReporte.setInt(3, reporte.getUsuarioReporta());
            insertarReporte.executeUpdate();

            // Actualizar estado del elemento
            String sqlUpdate = "UPDATE elementos SET estado = ? WHERE id_elemento = ?";
            actualizarEstado = conexion.prepareStatement(sqlUpdate);
            actualizarEstado.setString(1, nuevoEstadoElemento);
            actualizarEstado.setInt(2, reporte.getElementoReportado());
            actualizarEstado.executeUpdate();

            conexion.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            System.err.println("Error al reportar el elemento: " + e.getMessage());
            try {
                if (conexion != null) {
                    conexion.rollback(); // Revertir cambios
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;

        } finally {
            try {
                if (insertarReporte != null)
                    insertarReporte.close();
                if (actualizarEstado != null)
                    actualizarEstado.close();
                if (conexion != null)
                    conexion.close();
            } catch (SQLException cierreEx) {
                cierreEx.printStackTrace();
            }
        }
    }
}
