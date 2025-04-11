package Dao;

import Modelo.Elemento;
import Modelo.ElementoTecnologico;
import Modelo.ElementosMobiliarios;
import util.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// LISTA DE METODOS

// Inserción de elementos generales, tecnológicos y mobiliarios.
// Consulta por tipo (tecnologico / mobiliario).
// Consulta por usuario.
// Consulta por ID, identificando tipo y atributos extra (marca, serie).
// Diseño limpio respetando herencia y tu estructura de base de datos.

public class ElementoDao {
    // INSERTAR NUEVO ELEMENTO
    public void insertarElemento(Elemento elemento) {
        String sql = "INSERT INTO elementos(nombre, estado, usuario_registra, aula_id, identificador_unico) " +
                "VALUES (?, ?, ?, ?, ?)";

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
                int idElemento = rs.getInt(1);

                if (elemento instanceof ElementoTecnologico) {
                    insertarTecnologico(idElemento, (ElementoTecnologico) elemento);
                } else if (elemento instanceof ElementosMobiliarios) {
                    insertarMobiliario(idElemento);
                }
            }

            System.out.println("Elemento registrado correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insertar elemento tecnologico
    private void insertarTecnologico(int idElemento, ElementoTecnologico t) {
        String sql = "INSERT INTO elementos_tecnologicos(elemento_id, marca, serie) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idElemento);
            stmt.setString(2, t.getMarca());
            stmt.setString(3, t.getSerie());
            stmt.executeUpdate();
            System.out.println("Tecnológico insertado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insertar elemento mobiliario
    private void insertarMobiliario(int idElemento) {
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

    // -------------------CONSULTAS PARA ELEMENTOS-----------------------

    // consultar elemento por id
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

    // consultar elemento por tipo de elemento
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

    // ...#3 consultar elementos por usuarios
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

    // REPORTAR UN ELEMENTO

    public boolean reportarElemento(int idElemento, int idUsuario, String descripcion, String nuevoEstado) {
        String sqlInsertReporte = "INSERT INTO reporte (descripcion, elemento_reportado, usuario_reporta) VALUES (?, ?, ?)";
        String sqlActualizarEstado = "UPDATE elementos SET estado = ? WHERE id = ?";

        try (Connection conexion = Conexion.getConexion()) {
            conexion.setAutoCommit(false);

            try (
                    PreparedStatement insertarReporte = conexion.prepareStatement(sqlInsertReporte);
                    PreparedStatement actualizarEstado = conexion.prepareStatement(sqlActualizarEstado)) {
                // Insertar el reporte
                insertarReporte.setString(1, descripcion);
                insertarReporte.setInt(2, idElemento);
                insertarReporte.setInt(3, idUsuario);
                insertarReporte.executeUpdate();

                // Cambiar el estado del elemento
                actualizarEstado.setString(1, nuevoEstado);
                actualizarEstado.setInt(2, idElemento);
                actualizarEstado.executeUpdate();

                conexion.commit();
                return true;
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

    // ACTALIZAR UN ELEMENTO

    public boolean actualizarElemento(Elemento elemento) {
        String sqlUpdateElemento = "UPDATE elementos SET nombre = ?, estado = ?, aula_id = ?, identificador_unico = ? WHERE id_elemento = ?";
        String sqlCheckTecnologico = "SELECT COUNT(*) FROM elementos_tecnologicos WHERE elemento_id = ?";
        String sqlUpdateTecnologico = "UPDATE elementos_tecnologicos SET marca = ?, serie = ? WHERE elemento_id = ?";

        try (Connection conn = Conexion.getConexion()) {

            // 1. Actualizar tabla elementos
            try (PreparedStatement stmtElemento = conn.prepareStatement(sqlUpdateElemento)) {
                stmtElemento.setString(1, elemento.getNombre());
                stmtElemento.setString(2, elemento.getEstado());
                stmtElemento.setInt(3, elemento.getAulaId());
                stmtElemento.setString(4, elemento.getIdentificadorUnico());
                stmtElemento.setInt(5, elemento.getIdElemento());
                stmtElemento.executeUpdate();
            }

            // 2. Verificar si el elemento está en elementos_tecnologicos
            boolean esTecnologico = false;
            try (PreparedStatement stmtCheck = conn.prepareStatement(sqlCheckTecnologico)) {
                stmtCheck.setInt(1, elemento.getIdElemento());
                ResultSet rs = stmtCheck.executeQuery();
                if (rs.next()) {
                    esTecnologico = rs.getInt(1) > 0;
                }
            }

            // 3. Si es tecnológico, actualizar marca y serie
            if (esTecnologico && elemento instanceof ElementoTecnologico) {
                ElementoTecnologico tecnologico = (ElementoTecnologico) elemento;

                try (PreparedStatement stmtTec = conn.prepareStatement(sqlUpdateTecnologico)) {
                    stmtTec.setString(1, tecnologico.getMarca());
                    stmtTec.setString(2, tecnologico.getSerie());
                    stmtTec.setInt(3, tecnologico.getIdElemento());
                    stmtTec.executeUpdate();
                }
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error al actualizar el elemento: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR ELEMENTO

    public boolean eliminarElemento(int idElemento, int idUsuario, String motivoEliminacion) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean eliminado = false;

        try {
            conn = Conexion.getConexion();
            conn.setAutoCommit(false); // Iniciar transacción

            // Verificar si el elemento existe
            String verificar = "SELECT id_elemento FROM elementos WHERE id_elemento = ?";
            ps = conn.prepareStatement(verificar);
            ps.setInt(1, idElemento);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return false; // Elemento no encontrado
            }
            rs.close();
            ps.close();

            // Verificar si es tecnológico
            boolean esTecnologico = false;
            String verificarTecnologico = "SELECT id_tecnologico FROM elementos_tecnologicos WHERE elemento_id = ?";
            ps = conn.prepareStatement(verificarTecnologico);
            ps.setInt(1, idElemento);
            rs = ps.executeQuery();
            if (rs.next()) {
                esTecnologico = true;
            }
            rs.close();
            ps.close();

            // Registrar eliminación
            String registrarEliminacion = "INSERT INTO elementos_eliminados (elemento_id, motivo_eliminacion, usuario_elimino) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(registrarEliminacion);
            ps.setInt(1, idElemento);
            ps.setString(2, motivoEliminacion);
            ps.setInt(3, idUsuario);
            ps.executeUpdate();
            ps.close();

            // Eliminar del tipo específico
            if (esTecnologico) {
                String eliminarTec = "DELETE FROM elementos_tecnologicos WHERE elemento_id = ?";
                ps = conn.prepareStatement(eliminarTec);
                ps.setInt(1, idElemento);
                ps.executeUpdate();
                ps.close();
            } else {
                String eliminarMob = "DELETE FROM elementos_mobiliarios WHERE elemento_id = ?";
                ps = conn.prepareStatement(eliminarMob);
                ps.setInt(1, idElemento);
                ps.executeUpdate();
                ps.close();
            }

            // Eliminar del registro principal
            String eliminarElemento = "DELETE FROM elementos WHERE id_elemento = ?";
            ps = conn.prepareStatement(eliminarElemento);
            ps.setInt(1, idElemento);
            ps.executeUpdate();
            ps.close();

            conn.commit();
            eliminado = true;

        } catch (SQLException e) {
            try {
                if (conn != null)
                    conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.setAutoCommit(true);
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return eliminado;
    }

    // MOVER ELEMENTO Y REGISTRAR MOVIMIENTO
    public boolean moverElemento(int idElemento, String tipoElemento, int aulaOrigen, int aulaDestino, int idUsuario) {
        Connection conn = null;
        PreparedStatement actualizarElemento = null;
        PreparedStatement registrarHistorial = null;

        String actualizarElementoSQL = "UPDATE elementos SET aula = ? WHERE id_elemento = ?";
        String registrarHistorialSQL = "INSERT INTO historial_movimientos (tipo_elemento, aula_origen, aula_destino, usuario_movio) VALUES (?, ?, ?, ?)";

        try {
            conn = Conexion.getConexion();
            conn.setAutoCommit(false); // Empezamos transacción

            // 1. Actualizar el aula del elemento
            actualizarElemento = conn.prepareStatement(actualizarElementoSQL);
            actualizarElemento.setInt(1, aulaDestino);
            actualizarElemento.setInt(2, idElemento);
            actualizarElemento.executeUpdate();

            // 2. Registrar movimiento en el historial
            registrarHistorial = conn.prepareStatement(registrarHistorialSQL);
            registrarHistorial.setString(1, tipoElemento); // "mobiliario" o "tecnologico"
            registrarHistorial.setInt(2, aulaOrigen);
            registrarHistorial.setInt(3, aulaDestino);
            registrarHistorial.setInt(4, idUsuario);
            registrarHistorial.executeUpdate();

            conn.commit(); // Confirmar transacción
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir si hay error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (actualizarElemento != null)
                    actualizarElemento.close();
                if (registrarHistorial != null)
                    registrarHistorial.close();
                if (conn != null)
                    conn.setAutoCommit(true); // Restaurar estado
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // ACTUALIZAR IDENTIFICADOR DE UN ELEMENTO SOLO SI ES ADMINISTRADOR
    
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
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    












}
