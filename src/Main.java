import java.sql.Connection;

import Dao.AulaDao;
import Dao.BloqueDao;
import Dao.ColegioDao;
import Dao.ElementoDao;
import Dao.ElementoTecnologicoDao;
import Dao.PisoDao;
import Dao.RolDao;
import Dao.SedeDao;
import Dao.UsuarioDao;
import Modelo.Aula;
import Modelo.Bloque;
import Modelo.Colegio;
import Modelo.ElementoTecnologico;
import Modelo.Piso;
import Modelo.Rol;
import Modelo.Sede;
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
      nuevoUsuario.setCorreo("juan.loquilloz@example.com");
      nuevoUsuario.setCedula("1234678");
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

      // INSERTAR UN COLEGIO
      ColegioDao colegioDao = new ColegioDao(conn);
      Colegio nuevoColegio = new Colegio("Institución Educativa El Saber", nuevoUsuario);
      colegioDao.insertar(nuevoColegio);

      if (nuevoColegio.getId() != 0) {
        System.out.println("Colegio insertado con ID: " + nuevoColegio.getId());
      } else {
        System.out.println("Error al insertar el colegio.");
      }

      // INSERTAR SEDE EN UN COLEGIO EXISTENTE
      Sede nuevaSede = new Sede("Sede Norte", nuevoColegio, nuevoUsuario);
      SedeDao sedeDao = new SedeDao();
      sedeDao.insertar(nuevaSede);
      if (nuevaSede.getId() != 0) {
        System.out.println("Sede insertada con ID: " + nuevaSede.getId());
      } else {
        System.out.println("Error al insertar la sede.");
      }

      // INSERTAR BLOQUE EN LA SEDE 1

      Bloque nuevoBloque = new Bloque(nuevaSede, nuevoUsuario);
      BloqueDao bloqueDao = new BloqueDao();
      bloqueDao.insertar(nuevoBloque);
      if (nuevoBloque.getId() != 0) {
        System.out.println("Bloque insertado con ID: " + nuevoBloque.getId());
      } else {
        System.out.println("Error al insertar el bloque.");
      }

//    INSERTAR PISO
      Piso nuevoPiso = new Piso(3, nuevoBloque, nuevoUsuario);
      PisoDao pisoDao = new PisoDao();
      pisoDao.insertar(nuevoPiso);
      System.out.println("Piso insertado con ID: " + nuevoPiso.getId());

      // INSERTAR AULA
      AulaDao aulaDao = new AulaDao();
      Aula nuevaAula = new Aula(nuevoPiso, nuevoUsuario);
      aulaDao.insertar(nuevaAula);
      if (nuevaAula.getId() != 0) {
        System.out.println("Aula insertada con ID: " + nuevaAula.getId());
      } else {
        System.out.println("Error al insertar el aula.");
      }

      // INSERTAR ELEMENTO TECNOLÓGICO
      ElementoDao elementoDao = new ElementoDao();
      ElementoTecnologicoDao elementoTecnologicoDao = new ElementoTecnologicoDao();

      ElementoTecnologico nuevoElementoTec = new ElementoTecnologico();
      nuevoElementoTec.setNombre("Portátil HP Pavilion");
      nuevoElementoTec.setEstado("Disponible");
      nuevoElementoTec.setUsuarioRegistra(nuevoUsuario.getIdUsuario()); 
      nuevoElementoTec.setAulaId(nuevaAula.getId()); 
      nuevoElementoTec.setIdentificadorUnico("HP-2025-PAV001");
      nuevoElementoTec.setTipoIdentificador("Serial");
      nuevoElementoTec.setFechaCreacion(new java.sql.Timestamp(System.currentTimeMillis()));
      nuevoElementoTec.setMarca("HP");
      nuevoElementoTec.setSerie("PAV15-2025");

      elementoDao.insertarElemento(nuevoElementoTec);

      if (nuevoElementoTec.getIdElemento() != 0) {
        elementoTecnologicoDao.insertar(nuevoElementoTec);
        System.out.println("Elemento tecnológico insertado con ID: " + nuevoElementoTec.getIdElemento());
      } else {
        System.out.println("Error al insertar el elemento tecnológico.");
      }

      // -------

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
