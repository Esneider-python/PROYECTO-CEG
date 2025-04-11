package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/databaselapaz?serverTimezone=UTC";
    private static final String USUARIO = "root"; // Cambiar por tu usuario real
    private static final String CLAVE = "tu_contraseña"; // Cambiar por tu contraseña real

    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Opcional pero útil
            return DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
        return null;
    }
}
