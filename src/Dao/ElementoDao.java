
package Dao;

import Modelo.Elemento;
import Modelo.ElementoTecnologico;
import Modelo.ElementosMobiliarios;
import Modelo.HistorialMovimiento;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Metodos creados

// Inserción de elementos generales, tecnológicos y mobiliarios.
// Consulta por tipo (tecnologico / mobiliario).
// Consulta por usuario.
// Consulta por ID, identificando tipo y atributos extra (marca, serie).
// Eliminar elemento
// Agregar Identificador elemento
// Modicar identificador elemento
// Actualizar elemento
// Mover elemento

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
                if (esTecnologico(idElemento, conn)) {
                    ElementoTecnologico t = new ElementoTecnologico();
                    t.setIdElemento(rs.getInt("id_elemento"));
                    t.setNombre(rs.getString("nombre"));
                    t.setEstado(rs.getString("estado"));
                    t.setUsuarioRegistra(rs.getInt("usuario_registra"));
                    t.setAulaId(rs.getInt("aula_id"));
                    t.setIdentificadorUnico(rs.getString("identificador_unico"));
                    t.setTipoIdentificador(rs.getString("tipo_identificador"));
                    t.setFechaCreacion(rs.getTimestamp("fecha_creacion"));

                    // Obtener marca y serie
                    String sqlTec = "SELECT marca, serie FROM elementos_tecnologicos WHERE elemento_id = ?";
                    try (PreparedStatement stmtTec = conn.prepareStatement(sqlTec)) {
                        stmtTec.setInt(1, idElemento);
                        ResultSet rsTec = stmtTec.executeQuery();
                        if (rsTec.next()) {
                            t.setMarca(rsTec.getString("marca"));
                            t.setSerie(rsTec.getString("serie"));
                        }
                    }

                    elemento = t;

                } else if (esMobiliario(idElemento, conn)) {
                    ElementosMobiliarios m = new ElementosMobiliarios();
                    m.setIdElemento(rs.getInt("id_elemento"));
                    m.setNombre(rs.getString("nombre"));
                    m.setEstado(rs.getString("estado"));
                    m.setUsuarioRegistra(rs.getInt("usuario_registra"));
                    m.setAulaId(rs.getInt("aula_id"));
                    m.setIdentificadorUnico(rs.getString("identificador_unico"));
                    m.setTipoIdentificador(rs.getString("tipo_identificacior"));
                    m.setFechaCreacion(rs.getTimestamp("fecha_creacion"));

                    elemento = m;

                } else {
                    // En caso de que no esté en ninguna tabla hija
                    Elemento e = new Elemento();
                    e.setIdElemento(rs.getInt("id_elemento"));
                    e.setNombre(rs.getString("nombre"));
                    e.setEstado(rs.getString("estado"));
                    e.setUsuarioRegistra(rs.getInt("usuario_registra"));
                    e.setAulaId(rs.getInt("aula_id"));
                    e.setIdentificadorUnico(rs.getString("identificador_unico"));
                    e.setTipoIdentificador(rs.getString("tipo_identificacior"));
                    e.setFechaCreacion(rs.getTimestamp("fecha_creacion"));

                    elemento = e;
                }
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

        if (tipo.equalsIgnoreCase("tecnologico")) {
            sql = "SELECT e.*, t.marca, t.serie " +
                    "FROM elementos e " +
                    "INNER JOIN elementos_tecnologicos t ON e.id_elemento = t.elemento_id";
        } else if (tipo.equalsIgnoreCase("mobiliario")) {
            sql = "SELECT e.* " +
                    "FROM elementos e " +
                    "INNER JOIN elementos_mobiliarios m ON e.id_elemento = m.elemento_id";
        } else {
            System.out.println("Tipo de elemento no válido.");
            return lista;
        }

        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                if (tipo.equalsIgnoreCase("tecnologico")) {
                    ElementoTecnologico e = new ElementoTecnologico();
                    e.setIdElemento(rs.getInt("id_elemento"));
                    e.setNombre(rs.getString("nombre"));
                    e.setEstado(rs.getString("estado"));
                    e.setUsuarioRegistra(rs.getInt("usuario_registra"));
                    e.setAulaId(rs.getInt("aula_id"));
                    e.setIdentificadorUnico(rs.getString("identificador_unico"));
                    e.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                    e.setMarca(rs.getString("marca"));
                    e.setSerie(rs.getString("serie"));
                    lista.add(e);
                } else {
                    ElementosMobiliarios e = new ElementosMobiliarios();
                    e.setIdElemento(rs.getInt("id_elemento"));
                    e.setNombre(rs.getString("nombre"));
                    e.setEstado(rs.getString("estado"));
                    e.setUsuarioRegistra(rs.getInt("usuario_registra"));
                    e.setAulaId(rs.getInt("aula_id"));
                    e.setIdentificadorUnico(rs.getString("identificador_unico"));
                    e.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
                    lista.add(e);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
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

                // Verificamos si es tecnológico
                if (esTecnologico(idElemento, conn)) {
                    ElementoTecnologico t = new ElementoTecnologico();
                    t.setIdElemento(idElemento);
                    t.setNombre(rs.getString("nombre"));
                    t.setEstado(rs.getString("estado"));
                    t.setUsuarioRegistra(rs.getInt("usuario_registra"));
                    t.setAulaId(rs.getInt("aula_id"));
                    t.setIdentificadorUnico(rs.getString("identificador_unico"));
                    t.setTipoIdentificador(rs.getString("tipo_identificador"));
                    t.setFechaCreacion(rs.getTimestamp("fecha_creacion"));

                    // Obtener marca y serie
                    String sqlTec = "SELECT marca, serie FROM elementos_tecnologicos WHERE elemento_id = ?";
                    try (PreparedStatement stmtTec = conn.prepareStatement(sqlTec)) {
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
                    m.setIdElemento(idElemento);
                    m.setNombre(rs.getString("nombre"));
                    m.setEstado(rs.getString("estado"));
                    m.setUsuarioRegistra(rs.getInt("usuario_registra"));
                    m.setAulaId(rs.getInt("aula_id"));
                    m.setIdentificadorUnico(rs.getString("identificador_unico"));
                    m.setTipoIdentificador(rs.getString("tipo_identificador"));
                    m.setFechaCreacion(rs.getTimestamp("fecha_creacion"));

                    lista.add(m);
                } else {
                    // En caso de que no esté ni en tecnológico ni mobiliario (no debería pasar)
                    Elemento e = new Elemento();
                    e.setIdElemento(idElemento);
                    e.setNombre(rs.getString("nombre"));
                    e.setEstado(rs.getString("estado"));
                    e.setUsuarioRegistra(rs.getInt("usuario_registra"));
                    e.setAulaId(rs.getInt("aula_id"));
                    e.setIdentificadorUnico(rs.getString("identificador_unico"));
                    e.setTipoIdentificador(rs.getString("tipo_identificador"));
                    e.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
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

    public boolean moverElemento(int idElemento, int nuevaAula, int usuarioMovio, String tipoElemento) {
        String sqlActualizarAula = "UPDATE elementos SET aula_id = ? WHERE id_elemento = ?";

        try (Connection conexion = Conexion.getConexion()) {
            conexion.setAutoCommit(false);
            try (
                    PreparedStatement stmtActualizar = conexion.prepareStatement(sqlActualizarAula)) {
                // Obtener el aula actual del elemento (consulta previa necesaria)
                int aulaOrigen = obtenerAulaActual(idElemento, conexion);
                if (aulaOrigen == -1) {
                    conexion.rollback();
                    return false;
                }

                // Actualizar aula del elemento
                stmtActualizar.setInt(1, nuevaAula);
                stmtActualizar.setInt(2, idElemento);
                stmtActualizar.executeUpdate();

                // Registrar en el historial de movimientos
                HistorialMovimiento historial = new HistorialMovimiento(tipoElemento, aulaOrigen, nuevaAula,
                        usuarioMovio);
                HistorialMovimientoDao historialDao = new HistorialMovimientoDao();
                boolean registro = historialDao.registrarMovimiento(historial);

                if (registro) {
                    conexion.commit();
                    return true;
                } else {
                    conexion.rollback();
                    return false;
                }

            } catch (SQLException e) {
                conexion.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método auxiliar para obtener el aula actual
    private int obtenerAulaActual(int idElemento, Connection conexion) {
        String sql = "SELECT aula_id FROM elementos WHERE id_elemento = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("aula_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Error
    }

    // ACTUALIZAR IDENTIFICADOR DE UN ELEMENTO SOLO SI ES "ADMINISTRADOR Y REGISTRAR
    // AUDITORIA"

    public boolean actualizarIdentificador(int idElemento, String nuevoIdentificador, String nuevoTipo, int idUsuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConexion();

            // 1. Validar si el usuario tiene rol de administrador
            String sqlRol = "SELECT r.nombre FROM usuarios u JOIN rol r ON u.rol = r.id_rol WHERE u.id_usuario = ?";
            stmt = conn.prepareStatement(sqlRol);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();
            if (!rs.next() || !rs.getString("nombre").equalsIgnoreCase("administrador")) {
                System.out.println("El usuario no tiene permisos para cambiar el identificador.");
                return false;
            }
            rs.close();
            stmt.close();

            // 2. Obtener identificador y tipo actuales del elemento
            String sqlSelect = "SELECT identificador_unico, tipo_identificador FROM elementos WHERE id_elemento = ?";
            stmt = conn.prepareStatement(sqlSelect);
            stmt.setInt(1, idElemento);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Elemento no encontrado.");
                return false;
            }

            String identificadorActual = rs.getString("identificador_unico");
            String tipoActual = rs.getString("tipo_identificador");
            rs.close();
            stmt.close();

            // 3. Actualizar identificador y tipo
            String sqlUpdate = "UPDATE elementos SET identificador_unico = ?, tipo_identificador = ? WHERE id_elemento = ?";
            stmt = conn.prepareStatement(sqlUpdate);
            stmt.setString(1, nuevoIdentificador);
            stmt.setString(2, nuevoTipo);
            stmt.setInt(3, idElemento);
            int filas = stmt.executeUpdate();
            stmt.close();

            if (filas == 0) {
                System.out.println("No se pudo actualizar el identificador.");
                return false;
            }

            // 4. Registrar auditoría
            String sqlAuditoria = "INSERT INTO cambios_identificador (id_elemento, identificador_anterior, tipo_identificador_anterior, identificador_nuevo, tipo_identificador_nuevo, usuario_modifica) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sqlAuditoria);
            stmt.setInt(1, idElemento);
            stmt.setString(2, identificadorActual);
            stmt.setString(3, tipoActual);
            stmt.setString(4, nuevoIdentificador);
            stmt.setString(5, nuevoTipo);
            stmt.setInt(6, idUsuario);
            stmt.executeUpdate();

            System.out.println("Cambio de identificador registrado con éxito.");
            return true;

        } catch (SQLException e) {
            System.out.println("Error en el cambio de identificador:");
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
