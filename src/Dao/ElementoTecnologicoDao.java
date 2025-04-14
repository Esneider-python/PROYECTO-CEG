package Dao;

import Modelo.ElementoTecnologico;
import util.Conexion;

import java.sql.*;

public class ElementoTecnologicoDao {

    // Insertar un nuevo elemento tecnológico
    public void insertar(ElementoTecnologico elementoTecnologico) {
        String sql = "INSERT INTO elementos_tecnologicos(elemento_id, marca, serie) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, elementoTecnologico.getIdElemento());
            stmt.setString(2, elementoTecnologico.getMarca());
            stmt.setString(3, elementoTecnologico.getSerie());
            stmt.executeUpdate();

            System.out.println("Elemento tecnológico insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar elemento tecnológico: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Actualizar datos del elemento tecnológico
    public boolean actualizar(ElementoTecnologico tecnologico) {
        String sqlUpdate = "UPDATE elementos_tecnologicos SET marca = ?, serie = ? WHERE elemento_id = ?";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sqlUpdate)) {

            stmt.setString(1, tecnologico.getMarca());
            stmt.setString(2, tecnologico.getSerie());
            stmt.setInt(3, tecnologico.getIdElemento());

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar elemento tecnológico: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Verificar si un elemento tecnológico existe
    public boolean existe(Connection conn, int idElemento) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM elementos_tecnologicos WHERE elemento_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
            stmt.setInt(1, idElemento);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    

    // Método para eliminar un elemento tecnológico
    public boolean eliminarElementoTecnologico(int idElemento) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean eliminado = false;

        try {
            conn = Conexion.getConexion();

            // Eliminar del registro en la tabla `elementos_tecnologicos`
            String eliminarElemento = "DELETE FROM elementos_tecnologicos WHERE elemento_id = ?";
            ps = conn.prepareStatement(eliminarElemento);
            ps.setInt(1, idElemento);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                eliminado = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return eliminado;
    }

}
