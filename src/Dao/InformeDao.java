package Dao;

import Modelo.Informe;
import util.Conexion;

import java.sql.*;

public class InformeDao {

    // METODO PARA INSERTAR UN NUEVO INFORME
    public boolean insertarInforme(Informe informe) {
        String sql = "INSERT INTO informes (tipo_informe, usuario_generador) VALUES (?, ?)";
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, informe.getTipoInforme());
            stmt.setInt(2, informe.getUsuarioGenerador());

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // METODO PARA OBTENER UN INFORME POR SU ID
    public Informe obtenerInformePorId(int id) {
        String sql = "SELECT * FROM informes WHERE id_informe = ?";
        Informe informe = null;
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    informe = new Informe(
                            rs.getInt("id_informe"),
                            rs.getString("tipo_informe"),
                            rs.getTimestamp("fecha_generacion"),
                            rs.getInt("usuario_generador"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return informe;
    }

    // METODO PARA OBTENER TODOS LOS INFORMES
    public ResultSet obtenerTodosLosInformes() {
        String sql = "SELECT * FROM informes";
        ResultSet rs = null;
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // METODO PARA ACTUALIZAR UN INFORME
    public boolean actualizarInforme(Informe informe) {
        String sql = "UPDATE informes SET tipo_informe = ?, usuario_generador = ? WHERE id_informe = ?";
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, informe.getTipoInforme());
            stmt.setInt(2, informe.getUsuarioGenerador());
            stmt.setInt(3, informe.getIdInforme());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // METODO PARA ELIMINAR UN INFORME
    public boolean eliminarInforme(int idInforme) {
        String sql = "DELETE FROM informes WHERE id_informe = ?";
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idInforme);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
