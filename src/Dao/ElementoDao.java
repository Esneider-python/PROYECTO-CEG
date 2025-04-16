
package Dao;

import Modelo.Elemento;
import Modelo.ElementoTecnologico;
import Modelo.ElementosMobiliarios;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Metodos creados

public class ElementoDao {
    private ElementoTecnologicoDao elementoTecnologicoDao = new ElementoTecnologicoDao();
    private ElementoMobiliarioDao elementoMobiliarioDao = new ElementoMobiliarioDao();

    // INSERTAR NUEVO ELEMENTO
    public int insertarElemento(Elemento elemento) {
        String sql = "INSERT INTO elementos(nombre, estado, usuario_registra, aula_id, identificador_unico, tipo_identificador) "
                +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, elemento.getNombre());
            stmt.setString(2, elemento.getEstado());
            stmt.setInt(3, elemento.getUsuarioRegistra());
            stmt.setInt(4, elemento.getAulaId());
            stmt.setString(5, elemento.getIdentificadorUnico());
            stmt.setString(6, elemento.getTipoIdentificador());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // Regresa el ID generado para el nuevo elemento
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Error al insertar el elemento
    }

    // #1. CONSULTAR ELEMENTO POR ID

    public Elemento obtenerPorId(int idElemento) {
        Elemento elemento = null;
        String sql = "SELECT * FROM elementos WHERE id_elemento = ?";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                elemento = new Elemento();
                elemento.setIdElemento(rs.getInt("id_elemento"));
                elemento.setNombre(rs.getString("nombre"));
                elemento.setEstado(rs.getString("estado"));
                elemento.setUsuarioRegistra(rs.getInt("usuario_registra"));
                elemento.setAulaId(rs.getInt("aula_id"));
                elemento.setIdentificadorUnico(rs.getString("identificador_unico"));
                elemento.setTipoIdentificador(rs.getString("tipo_identificador"));
                elemento.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elemento;
    }

    // CONSULTAR ELEMENTO POR TIPO
    public List<Elemento> obtenerPorTipo(String tipo) {
        List<Elemento> lista = new ArrayList<>();
        String sql;

        final String TIPO_TECNOLOGICO = "tecnologico";
        final String TIPO_MOBILIARIO = "mobiliario";

        if (tipo.equalsIgnoreCase(TIPO_TECNOLOGICO)) {
            sql = "SELECT e.*, t.marca, t.serie " +
                    "FROM elementos e " +
                    "INNER JOIN elementos_tecnologicos t ON e.id_elemento = t.elemento_id";
        } else if (tipo.equalsIgnoreCase(TIPO_MOBILIARIO)) {
            sql = "SELECT e.* " +
                    "FROM elementos e " +
                    "INNER JOIN elementos_mobiliarios m ON e.id_elemento = m.elemento_id";
        } else {
            System.out.println("Tipo de elemento no válido. Solo se permite 'tecnologico' o 'mobiliario'.");
            return lista;
        }

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                if (tipo.equalsIgnoreCase(TIPO_TECNOLOGICO)) {
                    ElementoTecnologico tec = new ElementoTecnologico();
                    llenarDatosComunes(rs, tec);
                    tec.setMarca(rs.getString("marca"));
                    tec.setSerie(rs.getString("serie"));
                    lista.add(tec);
                } else {
                    ElementosMobiliarios mob = new ElementosMobiliarios();
                    llenarDatosComunes(rs, mob);
                    lista.add(mob);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    private void llenarDatosComunes(ResultSet rs, Elemento e) throws SQLException {
        e.setIdElemento(rs.getInt("id_elemento"));
        e.setNombre(rs.getString("nombre"));
        e.setEstado(rs.getString("estado"));
        e.setUsuarioRegistra(rs.getInt("usuario_registra"));
        e.setAulaId(rs.getInt("aula_id"));
        e.setIdentificadorUnico(rs.getString("identificador_unico"));
        e.setTipoIdentificador(rs.getString("tipo_identificador")); // CUIDADO con el nombre
        e.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
    }

    // ...#3 CONSULTAR ELEMENTOS POR USUARIO
    public List<Elemento> obtenerPorUsuario(int idUsuario) {
        List<Elemento> lista = new ArrayList<>();
        String sql = "SELECT * FROM elementos WHERE usuario_registra = ?";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idElemento = rs.getInt("id_elemento");

                if (esTecnologico(idElemento, conn)) {
                    ElementoTecnologico t = new ElementoTecnologico();
                    llenarDatosComunes(rs, t);

                    // Consulta adicional para marca y serie
                    try (PreparedStatement stmtTec = conn.prepareStatement(
                            "SELECT marca, serie FROM elementos_tecnologicos WHERE elemento_id = ?")) {
                        stmtTec.setInt(1, idElemento);
                        ResultSet rsTec = stmtTec.executeQuery();
                        if (rsTec.next()) {
                            t.setMarca(rsTec.getString("marca"));
                            t.setSerie(rsTec.getString("serie"));
                        }
                    }

                    lista.add(t);
                } else if (esMobiliario(idElemento, conn)) {
                    ElementosMobiliarios m = new ElementosMobiliarios();
                    llenarDatosComunes(rs, m);
                    lista.add(m);
                } else {
                    Elemento e = new Elemento();
                    llenarDatosComunes(rs, e);
                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Metodo si es tecnologico

    private boolean esTecnologico(int idElemento, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM elementos_tecnologicos WHERE elemento_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    // Metodo si es mobiliario

    private boolean esMobiliario(int idElemento, Connection conn) throws SQLException {
        String sql = "SELECT 1 FROM elementos_mobiliarios WHERE elemento_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    // ACTUALIZAR UN ELEMENTO

    public boolean actualizarElemento(Elemento elemento) {
        String sqlUpdateElemento = "UPDATE elementos SET nombre = ?, usuario_registra = ? WHERE id_elemento = ?";

        try (Connection conn = Conexion.getConexion()) {

            boolean esTecnologico = elementoTecnologicoDao.existe(conn, elemento.getIdElemento());
            boolean esMobiliario = elementoMobiliarioDao.existeMobiliario(conn, elemento.getIdElemento());

            if (!esTecnologico && !esMobiliario) {
                System.out.println("El elemento no pertenece a ninguna categoría (ni tecnológico ni mobiliario).");
                return false;
            }

            // Actualizar la tabla principal 'elementos'
            try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateElemento)) {
                stmtUpdate.setString(1, elemento.getNombre());
                stmtUpdate.setInt(2, elemento.getUsuarioRegistra());
                stmtUpdate.setInt(3, elemento.getIdElemento());
                stmtUpdate.executeUpdate();
            }

            // Actualizar datos específicos si es tecnológico
            if (esTecnologico && elemento instanceof ElementoTecnologico) {
                ElementoTecnologico tecnologico = (ElementoTecnologico) elemento;
                boolean actualizado = elementoTecnologicoDao.actualizar(tecnologico);
                if (!actualizado) {
                    System.out.println("No se pudo actualizar el elemento tecnológico.");
                    return false;
                }
            }

            // Si es mobiliario, no se hace nada adicional por ahora
            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el elemento: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR ELEMENTO

    // Método para eliminar un elemento
    public boolean eliminarElemento(int idElemento) {
        Connection conn = null;
        PreparedStatement ps = null;
        boolean eliminado = false;

        try {
            conn = Conexion.getConexion();

            // Eliminar del registro principal de la tabla `elementos`
            String eliminarElemento = "DELETE FROM elementos WHERE id_elemento = ?";
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

    // MOVER ELEMENTO
    public boolean moverElemento(int idElemento, int nuevaAulaId) {
        String sql = "UPDATE elementos SET aula_id = ? WHERE id_elemento = ?";

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevaAulaId);
            stmt.setInt(2, idElemento);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al mover el elemento de aula: " + e.getMessage());
            return false;
        }
    }

    // ACTUALIZAR IDENTIFICADOR DE UN ELEMENTO
    public boolean actualizarIdentificador(int idElemento, String nuevoIdentificador, String nuevoTipo) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConexion();

            String sqlUpdate = "UPDATE elementos SET identificador_unico = ?, tipo_identificador = ? WHERE id_elemento = ?";
            stmt = conn.prepareStatement(sqlUpdate);
            stmt.setString(1, nuevoIdentificador);
            stmt.setString(2, nuevoTipo);
            stmt.setInt(3, idElemento);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Identificador actualizado correctamente.");
                return true;
            } else {
                System.out.println("No se pudo actualizar el identificador. Verifica si el ID del elemento existe.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar el identificador:");
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
