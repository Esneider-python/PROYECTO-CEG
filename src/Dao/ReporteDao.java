package Dao;

import Modelo.Reporte;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteDao {

    // REPORTAR ELEMENTO
    public boolean reportarElemento(Reporte reporte, String nuevoEstadoElemento) {
        Connection conexion = null;
        PreparedStatement insertarReporte = null;
        PreparedStatement actualizarEstado = null;

        try {
            conexion = Conexion.getConexion();
            conexion.setAutoCommit(false);

            String sqlReporte = "INSERT INTO reporte (descripcion, elemento_reportado, usuario_reporta) VALUES (?, ?, ?)";
            insertarReporte = conexion.prepareStatement(sqlReporte);
            insertarReporte.setString(1, reporte.getDescripcion());
            insertarReporte.setInt(2, reporte.getElementoReportado());
            insertarReporte.setInt(3, reporte.getUsuarioReporta());
            insertarReporte.executeUpdate();

            String sqlUpdate = "UPDATE elementos SET estado = ? WHERE id_elemento = ?";
            actualizarEstado = conexion.prepareStatement(sqlUpdate);
            actualizarEstado.setString(1, nuevoEstadoElemento);
            actualizarEstado.setInt(2, reporte.getElementoReportado());
            actualizarEstado.executeUpdate();

            conexion.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al reportar el elemento: " + e.getMessage());
            try {
                if (conexion != null)
                    conexion.rollback();
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

    // OBTENER TODOS
    public List<Reporte> obtenerTodos() {
        List<Reporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM reporte";

        try (Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reporte reporte = new Reporte(
                        rs.getString("descripcion"),
                        rs.getInt("elemento_reportado"),
                        rs.getInt("usuario_reporta"));
                reporte.setIdReporte(rs.getInt("id_reporte"));
                reporte.setFechaHoraReporte(rs.getTimestamp("fecha_reporte"));
                lista.add(reporte);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // OBTENER POR ID
    public Reporte obtenerPorId(int id) {
        String sql = "SELECT * FROM reporte WHERE id_reporte = ?";
        Reporte reporte = null;

        try (Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reporte = new Reporte(
                            rs.getString("descripcion"),
                            rs.getInt("elemento_reportado"),
                            rs.getInt("usuario_reporta"));
                    reporte.setIdReporte(rs.getInt("id_reporte"));
                    reporte.setFechaHoraReporte(rs.getTimestamp("fecha_reporte"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reporte;
    }

    // ELIMINAR REPORTE
    public boolean eliminarReporte(int idReporte) {
        String sql = "DELETE FROM reporte WHERE id_reporte = ?";

        try (Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idReporte);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // OBTENER POR USUARIO
    public List<Reporte> obtenerPorUsuario(int idUsuario) {
        List<Reporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM reporte WHERE usuario_reporta = ?";

        try (Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Reporte reporte = new Reporte(
                            rs.getString("descripcion"),
                            rs.getInt("elemento_reportado"),
                            rs.getInt("usuario_reporta"));
                    reporte.setIdReporte(rs.getInt("id_reporte"));
                    reporte.setFechaHoraReporte(rs.getTimestamp("fecha_reporte"));
                    lista.add(reporte);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ACTUALIZARREPORTE
    public boolean actualizarReporte(Reporte reporte) {
        String sql = "UPDATE reporte SET descripcion = ?, elemento_reportado = ?, usuario_reporta = ? WHERE id_reporte = ?";

        try (Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, reporte.getDescripcion());
            ps.setInt(2, reporte.getElementoReportado());
            ps.setInt(3, reporte.getUsuarioReporta());
            ps.setInt(4, reporte.getIdReporte());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
