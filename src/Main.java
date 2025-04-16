import java.sql.Connection;

import Dao.ColegioDao;
import Dao.RolDao;
import Dao.UsuarioDao;
import Modelo.Colegio;
import Modelo.Rol;
import Modelo.Usuario;
import util.Conexion;

public class Main {
  public static void main(String[] args) {

    try {
      Connection conn = Conexion.getConexion();

      // INSERTAMOS UN USUARIO CON EL ROL ADMINISTRADOR

      UsuarioDao usuarioDao = new UsuarioDao(conn);
      Usuario nuevoUsuario = new Usuario();
      nuevoUsuario.setNombres("Juan");
      nuevoUsuario.setApellidos("Pérez");
      nuevoUsuario.setTelefono("3124567890");
      nuevoUsuario.setCorreo("juan.perez@example.com");
      nuevoUsuario.setCedula("123456789");
      nuevoUsuario.setContrasena("1234");
      nuevoUsuario.setRolId(1);

      boolean usuarioInsertado = usuarioDao.insertarUsuario(nuevoUsuario);

      if (usuarioInsertado) {
        System.out.println("Usuario insertado correctamente.");
      } else {
        System.out.println("Error al insertar el usuario.");
      }

      // INSERTAR UN ROL
      RolDao rolDao = new RolDao(conn);
      Rol nuevoRol = new Rol();
      nuevoRol.setNombreRol("Inspector");
      int idRol = rolDao.insertarRol(nuevoRol);

      if (idRol != -1) {
        System.out.println("Rol insertado con ID: " + idRol);
      } else {
        System.out.println("Error al insertar el rol.");
      }

      // Inserción de Colegio
      ColegioDao colegioDao = new ColegioDao(conn);
      Colegio nuevoColegio = new Colegio("Institución Educativa El Saber", nuevoUsuario);
      colegioDao.insertar(nuevoColegio);

      if (nuevoColegio.getId() != 0) {
        System.out.println("Colegio insertado con ID: " + nuevoColegio.getId());
      } else {
        System.out.println("Error al insertar el colegio.");
      }
      // -------

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
