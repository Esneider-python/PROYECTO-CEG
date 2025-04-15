package Dao;

import Modelo.Usuario;
import java.sql.*;

public class UsuarioDao {
    private final Connection conexion;

    public UsuarioDao(Connection conexion) {
        this.conexion = conexion;
    }

    // Insertar nuevo usuario
    public boolean insertarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombres, apellidos, telefono, correo, cedula, contrasena, rol) VALUES (?, ?,  ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getCedula());
            ps.setString(6, usuario.getContrasena());
            ps.setInt(7, usuario.getRolId());
            return ps.executeUpdate() > 0;
        }
    }

    // Obtener usuario por ID
    public Usuario obtenerUsuarioPorId(int id) throws SQLException {
        String sql = "SELECT u.*, r.nombre AS nombreRol FROM usuarios u JOIN rol r ON u.rol = r.id WHERE u.id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirUsuarioDesdeResultSet(rs);
                }
            }
        }
        return null;
    }

    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        boolean tieneContrasena = usuario.getContrasena() != null && !usuario.getContrasena().isEmpty();

        String sql = tieneContrasena
                ? "UPDATE usuarios SET nombres = ?, apellidos = ?, telefono = ?, correo = ?, cedula = ?, contrasena = ?, rol = ? WHERE id = ?"
                : "UPDATE usuarios SET nombres = ?, apellidos = ?, telefono = ?, correo = ?, cedula = ?, rol = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getNombres());
            ps.setString(2, usuario.getApellidos());
            ps.setString(3, usuario.getTelefono());
            ps.setString(4, usuario.getCorreo());
            ps.setString(5, usuario.getCedula());

            if (tieneContrasena) {
                ps.setString(6, usuario.getContrasena());
                ps.setInt(7, usuario.getRolId());
            } else {
                ps.setInt(6, usuario.getRolId());
            }

            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar usuario por ID
    public boolean eliminarUsuario(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Cambiar contraseña por ID
    public boolean cambiarContrasena(int idUsuario, String nuevaContrasena) throws SQLException {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevaContrasena);
            ps.setInt(2, idUsuario);
            return ps.executeUpdate() > 0;
        }
    }

    // Cambiar contraseña desde perfil (requiere validación previa)
    public boolean cambiarContrasenaUsuario(String correo, String contrasenaActual, String nuevaContrasena)
            throws SQLException {
        String sqlVerificar = "SELECT * FROM usuarios WHERE correo = ? AND contrasena = ?";
        try (PreparedStatement psVerificar = conexion.prepareStatement(sqlVerificar)) {
            psVerificar.setString(1, correo);
            psVerificar.setString(2, contrasenaActual);

            try (ResultSet rs = psVerificar.executeQuery()) {
                if (rs.next()) {
                    String sqlActualizar = "UPDATE usuarios SET contrasena = ? WHERE correo = ?";
                    try (PreparedStatement psActualizar = conexion.prepareStatement(sqlActualizar)) {
                        psActualizar.setString(1, nuevaContrasena);
                        psActualizar.setString(2, correo);
                        return psActualizar.executeUpdate() > 0;
                    }
                }
            }
        }
        return false;
    }

    // Método auxiliar para construir un objeto Usuario desde un ResultSet
    private Usuario construirUsuarioDesdeResultSet(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("cedula"),
                rs.getString("contrasena"),
                rs.getInt("rol"),
                rs.getString("nombreRol"));
    }
}
