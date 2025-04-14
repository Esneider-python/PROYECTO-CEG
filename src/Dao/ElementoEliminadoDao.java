package Dao;

import Modelo.ElementoEliminado;
import util.Conexion;

import java.sql.*;

public class ElementoEliminadoDao {

    // Método para registrar la eliminación de un elemento
    public boolean registrarEliminacion(ElementoEliminado elementoEliminado) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean registrado = false;

        try {
            conn = Conexion.getConexion();

            String sql = "INSERT INTO elementos_eliminados (elemento_id, motivo_eliminacion, usuario_elimino) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, elementoEliminado.getElementoId());
            ps.setString(2, elementoEliminado.getMotivoEliminacion());
            ps.setInt(3, elementoEliminado.getUsuarioElimino());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
                registrado = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return registrado;
    }
}
