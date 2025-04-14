package Dao;

import Modelo.ElementosMobiliarios; // Asegúrate de importar esta clase
import util.Conexion;
import java.sql.*;

public class ElementoMobiliarioDao {

    public void insertarMobiliario(int idElemento) {
        String sql = "INSERT INTO elementos_mobiliarios(elemento_id) VALUES (?)";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idElemento);
            stmt.executeUpdate();
            System.out.println("🪑 Mobiliario insertado.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existeMobiliario(Connection conn, int idElemento) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM elementos_mobiliarios WHERE elemento_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sqlCheck)) {
            stmt.setInt(1, idElemento);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // En caso de que algún día quieras actualizar atributos específicos
    public boolean actualizarDatosMobiliario(Connection conn, ElementosMobiliarios mobiliario) throws SQLException {
        // Código aquí si hay campos en el futuro
        return true;
    }
}
