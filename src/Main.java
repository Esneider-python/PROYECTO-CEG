import Modelo.ElementoTecnologico;
import Modelo.ElementosMobiliarios;
import Dao.ElementoDao;

public class Main {

    public static void main(String[] args) {
      ElementoDao dao = new ElementoDao();

        // Insertar un ELEMENTO MOBILIARIO
        ElementosMobiliarios mobiliario = new ElementosMobiliarios();
        mobiliario.setNombre("Silla");
        mobiliario.setEstado("Nuevo");
        mobiliario.setUsuarioRegistra(1); // Debe existir
        mobiliario.setAulaId(2);          // Debe existir
        mobiliario.setIdentificadorUnico("MOB-001");
        dao.insertarElemento(mobiliario);

        // Insertar un ELEMENTO TECNOLÓGICO
        ElementoTecnologico tecnologico = new ElementoTecnologico();
        tecnologico.setNombre("Computador");
        tecnologico.setEstado("Bueno");
        tecnologico.setUsuarioRegistra(1);
        tecnologico.setAulaId(3);
        tecnologico.setIdentificadorUnico("TEC-001");
        tecnologico.setMarca("HP");
        tecnologico.setSerie("HPC-2023-XZ");
        dao.insertarElemento(tecnologico);
    }
}