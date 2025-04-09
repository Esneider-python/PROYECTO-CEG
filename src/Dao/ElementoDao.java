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

            System.out.println("✅ Elemento registrado correctamente.");

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
            System.out.println("🖥️ Tecnológico insertado.");
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
    // #1. Consultar elemento por ID
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

}
