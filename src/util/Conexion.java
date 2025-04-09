package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/databaselapaz";
    private static final String USUARIO = "NAN"; // Cambia si usas otro usuario
    private static final String CLAVE = "1004921685";       // Escribe tu contraseña si tienes una

    public static Connection getConexion() {
        try {
            return DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace();
            return null;
        }
    }
}
