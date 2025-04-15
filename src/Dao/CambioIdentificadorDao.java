package Dao;

import Modelo.CambioIdentificador;
import util.Conexion;

import java.sql.*;

public class CambioIdentificadorDao {

    // METODO PARA INSERTAR UN CAMBIO DE IDENTIFICADOR
    public boolean insertarCambioIdentificador(CambioIdentificador cambioIdentificador) {
        String sql = "INSERT INTO cambios_identificador (id_elemento, identificador_anterior, tipo_identificador_anterior, "
                +
                "identificador_nuevo, tipo_identificador_nuevo, usuario_modifica) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, cambioIdentificador.getIdElemento());
            stmt.setString(2, cambioIdentificador.getIdentificadorAnterior());
            stmt.setString(3, cambioIdentificador.getTipoIdentificadorAnterior());
            stmt.setString(4, cambioIdentificador.getIdentificadorNuevo());
            stmt.setString(5, cambioIdentificador.getTipoIdentificadorNuevo());
            stmt.setInt(6, cambioIdentificador.getUsuarioModifica());

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // METODO PARA OBTENER UN CAMBIO DE IDENTIFICADOR POR SU ID
    public CambioIdentificador obtenerCambioIdentificadorPorId(int id) {
        String sql = "SELECT * FROM cambios_identificador WHERE id_cambio = ?";
        CambioIdentificador cambioIdentificador = null;
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cambioIdentificador = new CambioIdentificador(
                            rs.getInt("id_cambio"),
                            rs.getInt("id_elemento"),
                            rs.getString("identificador_anterior"),
                            rs.getString("tipo_identificador_anterior"),
                            rs.getString("identificador_nuevo"),
                            rs.getString("tipo_identificador_nuevo"),
                            rs.getInt("usuario_modifica"),
                            rs.getTimestamp("fecha_modificacion"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cambioIdentificador;
    }

    // METODO PARA OBTENER TODOS LOS CAMBIOS DE IDENTIFICADOR
    public ResultSet obtenerTodosLosCambiosIdentificador() {
        String sql = "SELECT * FROM cambios_identificador";
        ResultSet rs = null;
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            rs = stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // METODO PARA ACTUALIZAR UN CAMBIO DE IDENTIFICADOR
    public boolean actualizarCambioIdentificador(CambioIdentificador cambioIdentificador) {
        String sql = "UPDATE cambios_identificador SET id_elemento = ?, identificador_anterior = ?, tipo_identificador_anterior = ?, "
                +
                "identificador_nuevo = ?, tipo_identificador_nuevo = ?, usuario_modifica = ? WHERE id_cambio = ?";
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, cambioIdentificador.getIdElemento());
            stmt.setString(2, cambioIdentificador.getIdentificadorAnterior());
            stmt.setString(3, cambioIdentificador.getTipoIdentificadorAnterior());
            stmt.setString(4, cambioIdentificador.getIdentificadorNuevo());
            stmt.setString(5, cambioIdentificador.getTipoIdentificadorNuevo());
            stmt.setInt(6, cambioIdentificador.getUsuarioModifica());
            stmt.setInt(7, cambioIdentificador.getIdCambio());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // METODO PARA ELIMINAR UN CAMBIO DE IDENTIFICADOR POR SU ID
    public boolean eliminarCambioIdentificador(int idCambio) {
        String sql = "DELETE FROM cambios_identificador WHERE id_cambio = ?";
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idCambio);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
